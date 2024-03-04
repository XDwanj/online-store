package cn.xdwanj.onlinestore.vo

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

data class OrderProductVo(
  @Schema(description = "订单项集合")
  var orderItemVoList: List<OrderItemVo>? = null,
  @Schema(description = "订单总价")
  var productTotalPrice: BigDecimal? = null,
  @Schema(description = "图片服务器地址")
  var imageHost: String? = null,
)