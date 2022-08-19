package cn.xdwanj.onlinestore.controller.portal;

import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.common.CURRENT_USER
import cn.xdwanj.onlinestore.common.CommonResponse
import cn.xdwanj.onlinestore.entity.User
import cn.xdwanj.onlinestore.service.AlipayService
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
  private val cartService: CartService,
  private val alipayService: AlipayService
) {
  @Operation(summary = "创建订单")
  @PostMapping("/create")
  fun create(
    @Parameter(hidden = true)
    @SessionAttribute(CURRENT_USER)
    user: User,
    shippingId: Int
  ): CommonResponse<OrderVo> {
    val orderVo = orderService.createOrder(user.id!!, shippingId)

    return if (orderVo != null) {
      CommonResponse.success(data = orderVo)
    } else {
      CommonResponse.error("订单生成失败")
    }
  }
}
