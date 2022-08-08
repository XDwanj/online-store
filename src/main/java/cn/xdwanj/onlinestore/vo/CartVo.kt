package cn.xdwanj.onlinestore.vo

import java.math.BigDecimal

data class CartVo(
  var cartProductVoList: MutableList<CartProductVo>? = null,
  var cartTotalPrice: BigDecimal? = null,
  var allChecked: Boolean? = null,
  var imageHost: String? = null
)