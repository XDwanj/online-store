package cn.xdwanj.onlinestore.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import java.time.LocalDateTime
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author XDwanj
 * @since 2022-07-16
 */
@TableName("mmall_shipping")
@ApiModel(value = "Shipping对象", description = "")
class Shipping {

    @TableId(value = "id", type = IdType.AUTO)
    var id: Int? = null

        @ApiModelProperty("用户id")
    var userId: Int? = null

        @ApiModelProperty("收货姓名")
    var receiverName: String? = null

        @ApiModelProperty("收货固定电话")
    var receiverPhone: String? = null

        @ApiModelProperty("收货移动电话")
    var receiverMobile: String? = null

        @ApiModelProperty("省份")
    var receiverProvince: String? = null

        @ApiModelProperty("城市")
    var receiverCity: String? = null

        @ApiModelProperty("区/县")
    var receiverDistrict: String? = null

        @ApiModelProperty("详细地址")
    var receiverAddress: String? = null

        @ApiModelProperty("邮编")
    var receiverZip: String? = null

    var createTime: LocalDateTime? = null

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
