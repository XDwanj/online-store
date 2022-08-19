package cn.xdwanj.onlinestore.vo

import java.math.BigDecimal

class OrderItemVo {
  var orderNo: Long? = null
  var productId: Int? = null
  var productName: String? = null
  var productImage: String? = null
  var currentUnitPrice: BigDecimal? = null
  var quantity: Int? = null
  var totalPrice: BigDecimal? = null
  var createTime: String? = null
}
