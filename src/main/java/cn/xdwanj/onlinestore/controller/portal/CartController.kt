package cn.xdwanj.onlinestore.controller.portal;

import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.annotation.Slf4j.Companion.logger
import cn.xdwanj.onlinestore.common.CartConst
import cn.xdwanj.onlinestore.common.USER_REQUEST
import cn.xdwanj.onlinestore.entity.Cart
import cn.xdwanj.onlinestore.entity.User
import cn.xdwanj.onlinestore.response.CommonResponse
import cn.xdwanj.onlinestore.response.ResponseCode
import cn.xdwanj.onlinestore.service.CartService
import cn.xdwanj.onlinestore.vo.CartVo
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
@Tag(name = "购物车模块")
@RestController
@RequestMapping("/cart")
class CartController(
  private val cartService: CartService
) {
  @Operation(summary = "添加购物车")
  @PostMapping
  fun add(
    @Parameter(hidden = true)
    @RequestAttribute(USER_REQUEST)
    user: User,
    productId: Int,
    count: Int
  ): CommonResponse<CartVo> {
    if (productId < 1 || count < 1) {
      return CommonResponse.error(ResponseCode.ILLEGAL_ARGUMENT.msg, ResponseCode.ILLEGAL_ARGUMENT.code)
    }

    val cartVo = cartService.add(user.id!!, productId, count)
      ?: return CommonResponse.error("添加失败")

    return CommonResponse.success(data = cartVo)
  }

  @Operation(summary = "更新购物车")
  @PutMapping
  fun update(
    @Parameter(hidden = true)
    @RequestAttribute(USER_REQUEST)
    user: User,
    productId: Int,
    count: Int
  ): CommonResponse<CartVo> {
    if (productId < 1 || count < 1) {
      return CommonResponse.error(ResponseCode.ILLEGAL_ARGUMENT.msg, ResponseCode.ILLEGAL_ARGUMENT.code)
    }

    val cart = cartService.ktQuery()
      .eq(Cart::productId, productId)
      .eq(Cart::userId, user.id)
      .one()
      ?: return CommonResponse.error("该购物车商品不存在")

    val isSuccess = cartService.ktUpdate()
      .eq(Cart::id, cart.id)
      .set(Cart::quantity, count)
      .update(cart)

    if (!isSuccess) {
      logger.info("{} 用户购物车更新失败", user.id!!)
    }

    val cartVo = cartService.getCartVoLimit(user.id!!)
    return CommonResponse.success(data = cartVo)
  }

  @Operation(summary = "删除购物车列表")
  // @DeleteMapping("/{productIds}")
  @DeleteMapping
  fun delete(
    @Parameter(hidden = true)
    @RequestAttribute(USER_REQUEST)
    user: User,
    @Parameter(description = "商品id数组，用','分割")
    @RequestParam(defaultValue = "")
    productIds: String
  ): CommonResponse<CartVo> {
    if (productIds.isBlank()) {
      return CommonResponse.error(ResponseCode.ILLEGAL_ARGUMENT.msg, ResponseCode.ILLEGAL_ARGUMENT.code)
    }

    productIds.split(",")
      .filter { it.isNotBlank() }
      .map { it.toInt() }
      .also {
        if (it.isEmpty()) {
          return CommonResponse.error(ResponseCode.ILLEGAL_ARGUMENT.msg, ResponseCode.ILLEGAL_ARGUMENT.code)
        }
      }.forEach { productId ->
        cartService.ktUpdate()
          .eq(Cart::userId, user.id)
          .eq(Cart::productId, productId)
          .remove()
      }

    val cartVo = cartService.getCartVoLimit(user.id!!)
    return CommonResponse.success(data = cartVo)
  }

  @Operation(summary = "获取购物车列表")
  @GetMapping("/list")
  fun list(
    @Parameter(hidden = true)
    @RequestAttribute(USER_REQUEST)
    user: User
  ): CommonResponse<CartVo> {
    val cartVo = cartService.getCartVoLimit(user.id!!)
    return CommonResponse.success(data = cartVo)
  }

  @Operation(summary = "购物车全选或者全反选")
  @PutMapping("/select-all/{checked}")
  fun selectAll(
    @Parameter(hidden = true)
    @RequestAttribute(USER_REQUEST)
    user: User,
    @Parameter(description = "${CartConst.CHECKED} 表示选中，${CartConst.UN_CHECKED} 表示未选中")
    @PathVariable
    checked: Int
  ): CommonResponse<CartVo> {
    val exists = listOf(CartConst.CHECKED, CartConst.UN_CHECKED).contains(checked)
    if (!exists) {
      return CommonResponse.error(ResponseCode.ILLEGAL_ARGUMENT.msg, ResponseCode.ILLEGAL_ARGUMENT.code)
    }

    val isSuccess = cartService.ktUpdate()
      .eq(Cart::userId, user.id)
      .set(Cart::checked, checked)
      .update()

    if (isSuccess) {
      logger.info("{} 用户购物车反选成功", user.id)
    }

    val cartVo = cartService.getCartVoLimit(user.id!!)
    return CommonResponse.success(data = cartVo)
  }

  @Operation(summary = "选中商品或者反选商品")
  @PutMapping("/select")
  fun select(
    @Parameter(hidden = true)
    @RequestAttribute(USER_REQUEST)
    user: User,
    productId: Int,
    checked: Int
  ): CommonResponse<CartVo> {
    val exists = listOf(CartConst.CHECKED, CartConst.UN_CHECKED).contains(checked)
    if (!exists) {
      return CommonResponse.error(ResponseCode.ILLEGAL_ARGUMENT.msg, ResponseCode.ILLEGAL_ARGUMENT.code)
    }

    val isSuccess = cartService.ktUpdate()
      .eq(Cart::userId, user.id)
      .eq(Cart::productId, productId)
      .set(Cart::checked, checked)
      .update()

    if (isSuccess) {
      logger.info("{} 用户的 {} 购物车商品选中调整成功", user.id, productId)
    }

    val cartVo = cartService.getCartVoLimit(user.id!!)
    return CommonResponse.success(data = cartVo)
  }

  @Operation(summary = "获得购物车项的数量")
  @GetMapping("/count")
  fun count(
    @Parameter(hidden = true)
    @RequestAttribute(USER_REQUEST)
    user: User
  ): CommonResponse<Int> {

    val count = cartService.ktQuery()
      .eq(Cart::userId, user.id)
      .count()

    return CommonResponse.success(data = count.toInt())
  }
}

