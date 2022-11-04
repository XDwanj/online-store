package cn.xdwanj.onlinestore.vo

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

data class ProductDetailVo(
  @Schema(description = "商品id")
  var id: Int? = null,
  @Schema(description = "分类id,对应mmall_category表的主键")
  var categoryId: Int? = null,
  @Schema(description = "商品名称")
  var name: String? = null,
  @Schema(description = "商品副标题")
  var subtitle: String? = null,
  @Schema(description = "产品主图,url相对地址")
  var mainImage: String? = null,
  @Schema(description = "图片地址,json格式,扩展用")
  var subImages: String? = null,
  @Schema(description = "商品详情")
  var detail: String? = null,
  @Schema(description = "价格,单位-元保留两位小数")
  var price: BigDecimal? = null,
  @Schema(description = "库存数量")
  var stock: Int? = null,
  @Schema(description = "商品状态.1-在售 2-下架 3-删除")
  var status: Int? = null,
  @Schema(description = "创建时间")
  var createTime: String? = null,
  @Schema(description = "更新时间")
  var updateTime: String? = null,
  @Schema(description = "图片服务器地址")
  var imageHost: String? = null,
  @Schema(description = "所在类别的父类别")
  var parentCategoryId: Int? = null
)
