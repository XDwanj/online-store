package cn.xdwanj.onlinestore.entity

import com.baomidou.mybatisplus.annotation.*
import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * <p>
 *
 * </p>
 *
 * @author XDwanj
 * @since 2022-07-19
 */
@TableName("mmall_product")
@Schema(name = "Product", description = "$!{table.comment}")
class Product {

  @Schema(description = "商品id")
  @TableId(value = "id", type = IdType.AUTO)
  var id: Int? = null

  @Schema(description = "分类id,对应mmall_category表的主键")
  var categoryId: Int? = null

  @Schema(description = "商品名称")
  var name: String? = null

  @Schema(description = "商品副标题")
  var subtitle: String? = null

  @Schema(description = "产品主图,url相对地址")
  var mainImage: String? = null

  @Schema(description = "图片地址,json格式,扩展用")
  var subImages: String? = null

  @Schema(description = "商品详情")
  var detail: String? = null

  @Schema(description = "价格,单位-元保留两位小数")
  var price: BigDecimal? = null

  @Schema(description = "库存数量")
  var stock: Int? = null

  @Schema(description = "商品状态.1-在售 2-下架 3-删除")
  var status: Int? = null

  @Schema(description = "创建时间")
  @TableField(fill = FieldFill.INSERT)
  var createTime: LocalDateTime? = null

  @Schema(description = "更新时间")
  @TableField(fill = FieldFill.INSERT_UPDATE)
  var updateTime: LocalDateTime? = null

  override fun toString(): String {
    return "Product{" +
        "id=" + id +
        ", categoryId=" + categoryId +
        ", name=" + name +
        ", subtitle=" + subtitle +
        ", mainImage=" + mainImage +
        ", subImages=" + subImages +
        ", detail=" + detail +
        ", price=" + price +
        ", stock=" + stock +
        ", status=" + status +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}"
  }
}
