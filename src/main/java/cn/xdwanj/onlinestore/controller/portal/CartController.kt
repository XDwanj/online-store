package cn.xdwanj.onlinestore.controller.portal;

import cn.xdwanj.onlinestore.exception.BusinessException
import cn.xdwanj.onlinestore.common.CURRENT_USER
import cn.xdwanj.onlinestore.common.ResponseCode
import cn.xdwanj.onlinestore.common.ServerResponse
import cn.xdwanj.onlinestore.entity.User
import cn.xdwanj.onlinestore.service.CartService
import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.vo.CartVo
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpSession

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XDwanj
 * @since 2022-07-16
 */
@Slf4j
@Tag(name = "购物车模块")
@RestController
@RequestMapping("/cart")
class CartController(
  private val cartService: CartService
) {
  @Operation(summary = "添加购物车")
  @PostMapping("/add")
  fun add(
    @Parameter(hidden = true) session: HttpSession,
    productId: Int,
    count: Int
  ): ServerResponse<CartVo> {
    if (productId < 1 || count < 1)
      return ServerResponse.error(ResponseCode.ILLEGAL_ARGUMENT.desc, ResponseCode.ILLEGAL_ARGUMENT.code)

    val user = session.getAttribute(CURRENT_USER) as User
    val userId = user.id ?: throw BusinessException("用户ID不可为空")

    val cartVo = cartService.add(userId, productId, count)
      ?: return ServerResponse.error("添加失败")

    return ServerResponse.success(data = cartVo)
  }

  @Operation(summary = "更新购物车")
  @PutMapping
  fun update(
    @Parameter(hidden = true) session: HttpSession,
    productId: Int,
    count: Int
  ): ServerResponse<CartVo> {
    if (productId < 1 || count < 1)
      return ServerResponse.error(ResponseCode.ILLEGAL_ARGUMENT.desc, ResponseCode.ILLEGAL_ARGUMENT.code)

    val user = session.getAttribute(CURRENT_USER) as User
    val userId = user.id
      ?: throw BusinessException("用户ID不可为空")

    val cartVo = cartService.updateCart(userId, productId, count)
    return ServerResponse.success(data = cartVo)
  }
}
