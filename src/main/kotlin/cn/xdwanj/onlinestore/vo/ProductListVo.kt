package cn.xdwanj.onlinestore.vo

import java.math.BigDecimal

data class ProductListVo(
  var id: Int? = null,
  var categoryId: Int? = null,
  var name: String? = null,
  var subtitle: String? = null,
  var mainImage: String? = null,
  var price: BigDecimal? = null,
  var status: Int? = null,
  var imageHost: String? = null,
)
