package cn.xdwanj.onlinestore.entity

import com.baomidou.mybatisplus.annotation.*
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
@TableName("mmall_pay_info")
@Schema(name = "PayInfo", description = "$!{table.comment}")
class PayInfo {

  @TableId(value = "id", type = IdType.AUTO)
  var id: Int? = null

  @Schema(description = "用户id")
  var userId: Int? = null

  @Schema(description = "订单号")
  var orderNo: Long? = null

  @Schema(description = "支付平台:1-支付宝,2-微信")
  var payPlatform: Int? = null

  @Schema(description = "支付宝支付流水号")
  var platformNumber: String? = null

  @Schema(description = "支付宝支付状态")
  var platformStatus: String? = null

  @Schema(description = "创建时间")
  @TableField(fill = FieldFill.INSERT)
  var createTime: LocalDateTime? = null

  @Schema(description = "更新时间")
  @TableField(fill = FieldFill.INSERT_UPDATE)
  var updateTime: LocalDateTime? = null

  override fun toString(): String {
    return "PayInfo{" +
        "id=" + id +
        ", userId=" + userId +
        ", orderNo=" + orderNo +
        ", payPlatform=" + payPlatform +
        ", platformNumber=" + platformNumber +
        ", platformStatus=" + platformStatus +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}"
  }
}
