package cn.xdwanj.onlinestore.vo

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

data class CartVo(
  @Schema(description = "商品列表")
  var cartProductVoList: MutableList<CartProductVo>? = null,
  @Schema(description = "购物车总价")
  var cartTotalPrice: BigDecimal? = null,
  @Schema(description = "是否全部勾选")
  var allChecked: Boolean? = null,
  @Schema(description = "图片服务器地址")
  var imageHost: String? = null
)