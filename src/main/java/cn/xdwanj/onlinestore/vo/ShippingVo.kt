package cn.xdwanj.onlinestore.vo

import io.swagger.v3.oas.annotations.media.Schema

data class ShippingVo(
  @Schema(description = "收货姓名")
  var receiverName: String? = null,
  @Schema(description = "收货固定电话")
  var receiverPhone: String? = null,
  @Schema(description = "收货移动电话")
  var receiverMobile: String? = null,
  @Schema(description = "省份")
  var receiverProvince: String? = null,
  @Schema(description = "城市")
  var receiverCity: String? = null,
  @Schema(description = "区/县")
  var receiverDistrict: String? = null,
  @Schema(description = "详细地址")
  var receiverAddress: String? = null,
  @Schema(description = "邮编")
  var receiverZip: String? = null
)