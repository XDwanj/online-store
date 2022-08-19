package cn.xdwanj.onlinestore.vo

import java.math.BigDecimal

data class OrderVo(
  var orderNo: Long? = null,
  var payment: BigDecimal? = null,
  var paymentType: Int? = null,
  var paymentTypeDesc: String? = null,
  var postage: Int? = null,
  var status: Int? = null,
  var statusDesc: String? = null,
  var paymentTime: String? = null,
  var sendTime: String? = null,
  var endTime: String? = null,
  var closeTime: String? = null,
  var createTime: String? = null,
  var orderItemVoList: List<OrderItemVo>? = null,
  var imageHost: String? = null,
  var shippingId: Int? = null,
  var receiverName: String? = null,
  var shippingVo: ShippingVo? = null
)
