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
@TableName("mmall_shipping")
@Schema(name = "Shipping", description = "$!{table.comment}")
class Shipping {

  @TableId(value = "id", type = IdType.AUTO)
  var id: Int? = null

  @Schema(description = "用户id")
  var userId: Int? = null

  @Schema(description = "收货姓名")
  var receiverName: String? = null

  @Schema(description = "收货固定电话")
  var receiverPhone: String? = null

  @Schema(description = "收货移动电话")
  var receiverMobile: String? = null

  @Schema(description = "省份")
  var receiverProvince: String? = null

  @Schema(description = "城市")
  var receiverCity: String? = null

  @Schema(description = "区/县")
  var receiverDistrict: String? = null

  @Schema(description = "详细地址")
  var receiverAddress: String? = null

  @Schema(description = "邮编")
  var receiverZip: String? = null

  @Schema(description = "创建时间")
  @TableField(fill = FieldFill.INSERT)
  var createTime: LocalDateTime? = null

  @Schema(description = "更新时间")
  @TableField(fill = FieldFill.INSERT_UPDATE)
  var updateTime: LocalDateTime? = null

  override fun toString(): String {
    return "Shipping{" +
        "id=" + id +
        ", userId=" + userId +
        ", receiverName=" + receiverName +
        ", receiverPhone=" + receiverPhone +
        ", receiverMobile=" + receiverMobile +
        ", receiverProvince=" + receiverProvince +
        ", receiverCity=" + receiverCity +
        ", receiverDistrict=" + receiverDistrict +
        ", receiverAddress=" + receiverAddress +
        ", receiverZip=" + receiverZip +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}"
  }
}
