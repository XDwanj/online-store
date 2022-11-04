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
@TableName("mmall_order")
@Schema(name = "Order", description = "$!{table.comment}")
class Order {

  @Schema(description = "订单id")
  @TableId(value = "id", type = IdType.AUTO)
  var id: Int? = null

  @Schema(description = "订单号")
  var orderNo: Long? = null

  @Schema(description = "用户id")
  var userId: Int? = null

  var shippingId: Int? = null

  @Schema(description = "实际付款金额,单位是元,保留两位小数")
  var payment: BigDecimal? = null

  @Schema(description = "支付类型,1-在线支付")
  var paymentType: Int? = null

  @Schema(description = "运费,单位是元")
  var postage: Int? = null

  @Schema(description = "订单状态:0-已取消-10-未付款，20-已付款，40-已发货，50-交易成功，60-交易关闭")
  var status: Int? = null

  @Schema(description = "支付时间")
  var paymentTime: LocalDateTime? = null

  @Schema(description = "发货时间")
  var sendTime: LocalDateTime? = null

  @Schema(description = "交易完成时间")
  var endTime: LocalDateTime? = null

  @Schema(description = "交易关闭时间")
  var closeTime: LocalDateTime? = null

  @Schema(description = "创建时间")
  @TableField(fill = FieldFill.INSERT)
  var createTime: LocalDateTime? = null

  @Schema(description = "更新时间")
  @TableField(fill = FieldFill.INSERT_UPDATE)
  var updateTime: LocalDateTime? = null

  override fun toString(): String {
    return "Order{" +
        "id=" + id +
        ", orderNo=" + orderNo +
        ", userId=" + userId +
        ", shippingId=" + shippingId +
        ", payment=" + payment +
        ", paymentType=" + paymentType +
        ", postage=" + postage +
        ", status=" + status +
        ", paymentTime=" + paymentTime +
        ", sendTime=" + sendTime +
        ", endTime=" + endTime +
        ", closeTime=" + closeTime +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}"
  }
}
