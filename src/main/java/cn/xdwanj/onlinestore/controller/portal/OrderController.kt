package cn.xdwanj.onlinestore.controller.portal;

import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.common.CURRENT_USER
import cn.xdwanj.onlinestore.common.CartConst
import cn.xdwanj.onlinestore.common.CommonResponse
import cn.xdwanj.onlinestore.entity.Cart
import cn.xdwanj.onlinestore.entity.User
import cn.xdwanj.onlinestore.service.CartService
import cn.xdwanj.onlinestore.service.OrderService
import cn.xdwanj.onlinestore.vo.OrderVo
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.SessionAttribute

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
  private val cartService: CartService
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

    val orderItemList = orderService.getOrderItemByCart(user.id!!, cartList)
    if (orderItemList.isEmpty()) {
      return errorResponse
    }

    // 计算总价
    val totalPrice = orderService.getOrderTotalPrice(orderItemList)

    // 获取订单
    val order = orderService.assembleOrder(user.id!!, shippingId, totalPrice)

    // 保存订单
    if (orderService.save(order)) {
      return errorResponse
    }

    // 清理库存
    orderService.reduceProductStock(orderItemList)

    // 清理购物车
    cartService.removeBatchByIds(cartList)

    // 转换 OrderVo
    val orderVo = orderService.assembleOrderVo(order, orderItemList)

    return CommonResponse.success(data = orderVo)
  }
}
