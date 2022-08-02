package cn.xdwanj.onlinestore.vo

import java.math.BigDecimal

data class ProductDetailVo(
  var id: Int? = null,
  var categoryId: Int? = null,
  var name: String? = null,
  var subtitle: String? = null,
  var mainImage: String? = null,
  var subImages: String? = null,
  var detail: String? = null,
  var price: BigDecimal? = null,
  var stock: Int? = null,
  var status: Int? = null,
  var createTime: String? = null,
  var updateTime: String? = null,
  var imageHost: String? = null,
  var parentCategoryId: Int? = null
)
