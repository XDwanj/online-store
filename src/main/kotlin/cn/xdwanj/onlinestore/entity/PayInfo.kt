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
@TableName("mmall_pay_info")
@ApiModel(value = "PayInfo对象", description = "")
class PayInfo {

    @TableId(value = "id", type = IdType.AUTO)
    var id: Int? = null

        @ApiModelProperty("用户id")
    var userId: Int? = null

        @ApiModelProperty("订单号")
    var orderNo: Long? = null

        @ApiModelProperty("支付平台:1-支付宝,2-微信")
    var payPlatform: Int? = null

        @ApiModelProperty("支付宝支付流水号")
    var platformNumber: String? = null

        @ApiModelProperty("支付宝支付状态")
    var platformStatus: String? = null

        @ApiModelProperty("创建时间")
    var createTime: LocalDateTime? = null

        @ApiModelProperty("更新时间")
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
