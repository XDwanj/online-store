package cn.xdwanj.onlinestore.vo

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

data class CartProductVo(
  var id: Int? = null,
  @Schema(description = "用户id")
  var userId: Int? = null,
  @Schema(description = "商品id")
  var productId: Int? = null,
  @Schema(description = "购物车中的商品数目")
  var quantity: Int? = null, //购物车中此商品的数量
  @Schema(description = "商品名")
  var productName: String? = null,
  @Schema(description = "商品副标题")
  var productSubtitle: String? = null,
  @Schema(description = "产品主图,url相对地址")
  var productMainImage: String? = null,
  @Schema(description = "价格,单位-元保留两位小数")
  var productPrice: BigDecimal? = null,
  @Schema(description = "商品状态.1-在售 2-下架 3-删除")
  var productStatus: Int? = null,
  @Schema(description = "总价")
  var productTotalPrice: BigDecimal? = null,
  @Schema(description = "库存数量")
  var productStock: Int? = null,
  @Schema(description = "此商品是否勾选")
  var productChecked: Int? = null, //此商品是否勾选
  @Schema(description = "限制数量的一个返回结果")
  var limitQuantity: String? = null //限制数量的一个返回结果
)
