package cn.xdwanj.onlinestore.vo

import java.math.BigDecimal

data class CartProductVo(
  var id: Int? = null,
  var userId: Int? = null,
  var productId: Int? = null,
  var quantity: Int? = null, //购物车中此商品的数量
  var productName: String? = null,
  var productSubtitle: String? = null,
  var productMainImage: String? = null,
  var productPrice: BigDecimal? = null,
  var productStatus: Int? = null,
  var productTotalPrice: BigDecimal? = null,
  var productStock: Int? = null,
  var productChecked: Int? = null, //此商品是否勾选
  var limitQuantity: String? = null //限制数量的一个返回结果
)
