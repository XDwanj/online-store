package cn.xdwanj.onlinestore.vo


data class ShippingVo(
  var receiverName: String? = null,
  var receiverPhone: String? = null,
  var receiverMobile: String? = null,
  var receiverProvince: String? = null,
  var receiverCity: String? = null,
  var receiverDistrict: String? = null,
  var receiverAddress: String? = null,
  var receiverZip: String? = null
)