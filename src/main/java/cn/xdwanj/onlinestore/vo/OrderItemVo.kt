package cn.xdwanj.onlinestore.vo

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

data class OrderItemVo(
  @Schema(description = "订单号")
  var orderNo: Long? = null,
  @Schema(description = "商品id")
  var productId: Int? = null,
  @Schema(description = "商品名")
  var productName: String? = null,
  @Schema(description = "商品图片地址")
  var productImage: String? = null,
  @Schema(description = "生成订单时的商品单价，单位是元,保留两位小数")
  var currentUnitPrice: BigDecimal? = null,
  @Schema(description = "商品数量")
  var quantity: Int? = null,
  @Schema(description = "商品总价,单位是元,保留两位小数")
  var totalPrice: BigDecimal? = null,
  @Schema(description = "创建时间")
  var createTime: String? = null,
)
