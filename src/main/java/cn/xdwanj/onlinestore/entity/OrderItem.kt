package cn.xdwanj.onlinestore.entity

import com.baomidou.mybatisplus.annotation.*
import java.math.BigDecimal
import java.time.LocalDateTime
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>
 *
 * </p>
 *
 * @author XDwanj
 * @since 2022-07-19
 */
@TableName("mmall_order_item")
@Schema(name = "OrderItem", description = "$!{table.comment}")
class OrderItem {

  @Schema(description = "订单子表id")
  @TableId(value = "id", type = IdType.AUTO)
  var id: Int? = null

  var userId: Int? = null

  var orderNo: Long? = null

  @Schema(description = "商品id")
  var productId: Int? = null

  @Schema(description = "商品名称")
  var productName: String? = null

  @Schema(description = "商品图片地址")
  var productImage: String? = null

  @Schema(description = "生成订单时的商品单价，单位是元,保留两位小数")
  var currentUnitPrice: BigDecimal? = null

  @Schema(description = "商品数量")
  var quantity: Int? = null

  @Schema(description = "商品总价,单位是元,保留两位小数")
  var totalPrice: BigDecimal? = null

  @Schema(description = "创建时间")
  @TableField(fill = FieldFill.INSERT)
  var createTime: LocalDateTime? = null

  @Schema(description = "更新时间")
  @TableField(fill = FieldFill.INSERT_UPDATE)
  var updateTime: LocalDateTime? = null

  override fun toString(): String {
    return "OrderItem{" +
        "id=" + id +
        ", userId=" + userId +
        ", orderNo=" + orderNo +
        ", productId=" + productId +
        ", productName=" + productName +
        ", productImage=" + productImage +
        ", currentUnitPrice=" + currentUnitPrice +
        ", quantity=" + quantity +
        ", totalPrice=" + totalPrice +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}"
  }
}
