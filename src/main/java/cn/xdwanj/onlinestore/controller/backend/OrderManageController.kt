package cn.xdwanj.onlinestore.controller.backend

import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.common.CommonResponse
import cn.xdwanj.onlinestore.common.OrderStatusEnum
import cn.xdwanj.onlinestore.entity.Order
import cn.xdwanj.onlinestore.entity.OrderItem
import cn.xdwanj.onlinestore.service.OrderItemService
import cn.xdwanj.onlinestore.service.OrderService
import cn.xdwanj.onlinestore.vo.OrderVo
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XDwanj
 * @since 2022-08-21
 */
@Slf4j
@Tag(name = "订单管理模块")
@RestController
@RequestMapping("/manage/order")
class OrderManageController(
  private val orderService: OrderService,
  private val orderItemService: OrderItemService
) {

  @Operation(summary = "订单列表")
  @GetMapping("/list")
  fun list(
    @RequestParam(defaultValue = "1") pageNum: Long,
    @RequestParam(defaultValue = "10") pageSize: Long
  ): CommonResponse<IPage<OrderVo>> {
    val page = orderService.ktQuery()
      .page(Page(pageNum, pageSize))
      .convert {
        val orderItemList = orderItemService.ktQuery()
          .eq(OrderItem::orderNo, it.orderNo)
          .list()
        orderService.assembleOrderVo(it, orderItemList)
      }

    return CommonResponse.success(data = page)
  }

  @Operation(summary = "订单详情")
  @GetMapping("/{orderNo}")
  fun get(@PathVariable orderNo: Long): CommonResponse<OrderVo> {
    val order = orderService.ktQuery()
      .eq(Order::orderNo, orderNo)
      .one()
      ?: return CommonResponse.error("订单详情获取失败")

    val orderItemList = orderItemService.ktQuery()
      .eq(OrderItem::orderNo, orderNo)
      .list()

    val orderVo = orderService.assembleOrderVo(order, orderItemList)

    return CommonResponse.success(data = orderVo)
  }

  /**
   * 教程中传入了 pageNum 和 pageSize，我个人暂时认为，没必要
   *
   * @param null
   */
  @Operation(summary = "通过 orderNo 查询 Order")
  @GetMapping("/search-single/{orderNo}")
  fun searchSingleOrder(@PathVariable orderNo: Long): CommonResponse<OrderVo> {
    val order = orderService.ktQuery()
      .eq(Order::orderNo, orderNo)
      .one()
      ?: return CommonResponse.error("订单不存在")

    val orderItemList = orderItemService.ktQuery()
      .eq(OrderItem::orderNo, orderNo)
      .list()

    val orderVo = orderService.assembleOrderVo(order, orderItemList)
    return CommonResponse.success(data = orderVo)
  }

  @Operation(summary = "发货")
  @GetMapping("/send-goods/{orderNo}")
  fun sendGoods(@PathVariable orderNo: Long): CommonResponse<Any> {
    val order = orderService.ktQuery()
      .eq(Order::orderNo, orderNo)
      .one()
      ?: return CommonResponse.error("订单不存在")

    if (order.status != OrderStatusEnum.PAID.code) {
      return CommonResponse.error("订单发货条件不符合")
    }

    val isSuccess = orderService.ktUpdate()
      .eq(Order::id, order.id)
      .set(Order::status, OrderStatusEnum.SHIPPED.code)
      .update()

    return if (isSuccess) {
      CommonResponse.success("发货成功")
    } else {
      CommonResponse.error("发货失败")
    }
  }

}