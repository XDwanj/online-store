package cn.xdwanj.onlinestore.vo

import java.math.BigDecimal

data class OrderProductVo(
  var orderItemVoList: List<OrderItemVo>? = null,
  var productTotalPrice: BigDecimal? = null,
  var imageHost: String? = null,
)