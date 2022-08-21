package cn.xdwanj.onlinestore.controller.portal

import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.annotation.Slf4j.Companion.logger
import cn.xdwanj.onlinestore.common.CURRENT_USER
import cn.xdwanj.onlinestore.common.CommonResponse
import cn.xdwanj.onlinestore.common.OrderStatusEnum
import cn.xdwanj.onlinestore.entity.Order
import cn.xdwanj.onlinestore.entity.User
import cn.xdwanj.onlinestore.exception.BusinessException
import cn.xdwanj.onlinestore.service.AlipayService
import cn.xdwanj.onlinestore.service.OrderService
import com.alipay.easysdk.factory.Factory
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XDwanj
 * @since 2022-08-22
 */
@Slf4j
@Tag(name = "支付控制器")
@RestController
@RequestMapping("/pay")
class PayController(
  private val orderService: OrderService,
  private val alipayService: AlipayService
) {
  @Operation(summary = "alipay 付款，返回 alipay 网页内容")
  @GetMapping("/alipay/pay/{orderNo}")
  fun aliPay(
    @Parameter(hidden = true)
    @SessionAttribute(CURRENT_USER)
    user: User,
    @PathVariable orderNo: Long
  ): CommonResponse<String> {
    val order = orderService.ktQuery()
      .eq(Order::orderNo, orderNo)
      .one()
      ?: return CommonResponse.error("商品不存在")

    val form = alipayService.toPay(
      orderNo.toString(),
      "用户：${user.username}；订单号：${orderNo}",
      order.payment!!
    )

    return if (form.isNotBlank()) {
      CommonResponse.success(data = form)
    } else {
      CommonResponse.error("返回付款页面失败")
    }
  }

  @Operation(summary = "查询是否付款成功")
  @GetMapping("/alipay/query/{orderNo}")
  fun aliQuery(
    @Parameter(hidden = true)
    @SessionAttribute(CURRENT_USER)
    user: User,
    @PathVariable orderNo: Long
  ): CommonResponse<String> {
    val order = orderService.ktQuery()
      .eq(Order::orderNo, orderNo)
      .one()
      ?: throw BusinessException("订单不存在")

    val queryTradeStatus = alipayService.queryTradeStatus(orderNo.toString())

    if (queryTradeStatus.isNullOrBlank()) {
      return CommonResponse.error("订单付款未成功")
    }

    // 订单已经支付，验证数据库是否持久化
    if (order.status!! == OrderStatusEnum.NO_PAY.code) {
      orderService.ktUpdate()
        .eq(Order::userId, user.id)
        .eq(Order::orderNo, orderNo)
        .set(Order::status, OrderStatusEnum.PAID)
        .update()
    }

    return CommonResponse.success("订单已支付")
  }

  //TODO: 未完成
  @Operation(summary = "alipay 接受回调")
  @GetMapping("/alipay/callback")
  fun aliNotifyCallback(
    @Parameter(hidden = true) request: HttpServletRequest
  ): String {
    logger.info("进入回调函数")

    val params = mutableMapOf<String, String>()
    val requestParam = request.parameterMap

    for (key in requestParam.keys) {
      val values = requestParam[key]
      var valueStr = ""
      values!!.forEachIndexed { i, value ->
        valueStr += if (i == values.size - 1) {
          value
        } else {
          "$value,"
        }
      }
      params[key] = valueStr
    }

    val signVerified = Factory.Payment.Common().verifyNotify(params)

    return if (signVerified) {
      logger.info("验签成功")
      "success"
    } else {
      "failure"
    }
  }
}