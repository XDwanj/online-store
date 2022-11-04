package cn.xdwanj.onlinestore.vo

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

data class OrderVo(
  @Schema(description = "订单号")
  var orderNo: Long? = null,
  @Schema(description = "实际付款金额,单位是元,保留两位小数")
  var payment: BigDecimal? = null,
  @Schema(description = "支付类型,1-在线支付")
  var paymentType: Int? = null,
  @Schema(description = "支付状态解释")
  var paymentTypeDesc: String? = null,
  @Schema(description = "运费,单位是元")
  var postage: Int? = null,
  @Schema(description = "订单状态:0-已取消-10-未付款，20-已付款，40-已发货，50-交易成功，60-交易关闭")
  var status: Int? = null,
  @Schema(description = "订单状态解释")
  var statusDesc: String? = null,
  @Schema(description = "支付时间")
  var paymentTime: String? = null,
  @Schema(description = "发货时间")
  var sendTime: String? = null,
  @Schema(description = "交易完成时间")
  var endTime: String? = null,
  @Schema(description = "交易关闭时间")
  var closeTime: String? = null,
  @Schema(description = "创建时间")
  var createTime: String? = null,
  @Schema(description = "订单项集合")
  var orderItemVoList: List<OrderItemVo>? = null,
  @Schema(description = "图片服务器地址")
  var imageHost: String? = null,
  @Schema(description = "地址id")
  var shippingId: Int? = null,
  @Schema(description = "接收人姓名")
  var receiverName: String? = null,
  @Schema(description = "地址对象")
  var shippingVo: ShippingVo? = null
)
