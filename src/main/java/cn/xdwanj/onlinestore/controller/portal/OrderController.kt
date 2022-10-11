package cn.xdwanj.onlinestore.controller.portal;

import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.annotation.Slf4j.Companion.logger
import cn.xdwanj.onlinestore.common.*
import cn.xdwanj.onlinestore.entity.Cart
import cn.xdwanj.onlinestore.entity.Order
import cn.xdwanj.onlinestore.entity.OrderItem
import cn.xdwanj.onlinestore.entity.User
import cn.xdwanj.onlinestore.response.CommonResponse
import cn.xdwanj.onlinestore.service.CartService
import cn.xdwanj.onlinestore.service.OrderItemService
import cn.xdwanj.onlinestore.service.OrderService
import cn.xdwanj.onlinestore.vo.OrderItemVo
import cn.xdwanj.onlinestore.vo.OrderProductVo
import cn.xdwanj.onlinestore.vo.OrderVo
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XDwanj
 * @since 2022-07-16
 */
@Slf4j
@Tag(name = "订单模块")
@RestController
@RequestMapping("/order")
class OrderController(
  private val orderService: OrderService,
  private val cartService: CartService,
  private val orderItemService: OrderItemService,
) {
  @Operation(summary = "创建订单")
  @PostMapping("/create")
  fun create(
    @Parameter(hidden = true)
    @SessionAttribute(CURRENT_USER)
    user: User,
    shippingId: Int
  ): CommonResponse<OrderVo> {
    val errorResponse = CommonResponse.error<OrderVo>("订单生成失败")

    val cartList = cartService.ktQuery()
      .eq(Cart::userId, user.id)
      .eq(Cart::checked, CartConst.CHECKED)
      .list()

    val orderItemList = orderService.listOrderItemByCartList(user.id!!, cartList)
    if (orderItemList.isEmpty()) {
      return errorResponse
    }

    // 计算总价
    val totalPrice = orderService.getTotalPrice(orderItemList)

    // 获取订单
    val order = orderService.assembleOrder(user.id!!, shippingId, totalPrice)

    // 保存订单项列表
    if (orderItemService.saveBatch(orderItemList)) {
      return errorResponse
    }

    // 清理库存
    orderService.reduceProductStock(orderItemList)

    // 清理购物车
    cartService.removeBatchByIds(cartList)

    // 转换 OrderVo
    val orderVo = orderService.assembleOrderVo(order, orderItemList)

    // 保存订单
    if (orderService.save(order)) {
      return errorResponse
    }

    return CommonResponse.success(data = orderVo)
  }

  @Operation(summary = "取消订单，仅支持在未付款的情况下")
  @GetMapping("/cancel")
  fun cancel(
    @Parameter(hidden = true)
    @SessionAttribute(CURRENT_USER)
    user: User,
    orderNo: Long
  ): CommonResponse<Any> {
    val order = orderService.ktQuery()
      .eq(Order::orderNo, orderNo)
      .eq(Order::userId, user.id)
      .one()
      ?: return CommonResponse.error("订单不存在")

    if (order.status != OrderStatusEnum.NO_PAY.code) {
      return CommonResponse.error("订单无法取消")
    }

    val isSuccess = orderService.ktUpdate()
      .eq(Order::userId, user.id)
      .eq(Order::orderNo, orderNo)
      .set(Order::status, OrderStatusEnum.CANCELED)
      .update()

    return if (isSuccess) {
      CommonResponse.success("取消成功")
    } else {
      logger.error("订单取消失败")
      CommonResponse.error("取消失败")
    }
  }

  @Operation(summary = "获取未被持久化的订单")
  @GetMapping("/pre-create")
  fun getOrderCartProduct(
    @Parameter(hidden = true)
    @SessionAttribute(CURRENT_USER)
    user: User
  ): CommonResponse<OrderProductVo> {
    val cartList = cartService.ktQuery()
      .eq(Cart::checked, CartConst.CHECKED)
      .eq(Cart::userId, user.id)
      .list()

    if (cartList.isEmpty()) {
      return CommonResponse.error("吴选中的购物车项")
    }

    val orderItemList = orderService.listOrderItemByCartList(user.id!!, cartList)
    val totalPrice = orderService.getTotalPrice(orderItemList)
    val orderItemVoList = mutableListOf<OrderItemVo>()

    for (orderItem in orderItemList) {
      orderItemVoList += orderService.assembleOrderItemVo(orderItem)
    }

    val orderProductVo = OrderProductVo().apply {
      this.productTotalPrice = totalPrice
      this.orderItemVoList = orderItemVoList
      this.imageHost = FTP_HOST
    }

    return CommonResponse.success(data = orderProductVo)
  }

  @Operation(summary = "获取订单详情")
  @GetMapping("/{orderNo}")
  fun get(
    @Parameter(hidden = true)
    @SessionAttribute(CURRENT_USER)
    user: User,
    @PathVariable orderNo: Long
  ): CommonResponse<OrderVo> {
    val order = orderService.ktQuery()
      .eq(Order::userId, user.id)
      .eq(Order::orderNo, orderNo)
      .one()
      ?: return CommonResponse.error("找不到该订单")

    val orderItemList = orderItemService.ktQuery()
      .eq(OrderItem::userId, user.id)
      .eq(OrderItem::orderNo, orderNo)
      .list()

    if (orderItemList.isEmpty()) {
      return CommonResponse.error("该订单无订单项")
    }

    val orderVo = orderService.assembleOrderVo(order, orderItemList)
    return CommonResponse.success(data = orderVo)
  }

  @Operation(summary = "获取订单列表")
  @GetMapping("/list")
  fun list(
    @Parameter(hidden = true)
    @SessionAttribute(CURRENT_USER)
    user: User,
    @RequestParam(defaultValue = "1") pageNum: Long,
    @RequestParam(defaultValue = "10") pageSize: Long
  ): CommonResponse<IPage<OrderVo>> {
    val page = orderService.ktQuery()
      .eq(Order::userId, user.id)
      .page(Page(pageNum, pageSize))
      .convert {
        val orderItemList = orderItemService.ktQuery()
          .eq(OrderItem::orderNo, it.orderNo)
          .list()

        orderService.assembleOrderVo(it, orderItemList)
      }

    return CommonResponse.success(data = page)
  }
}
