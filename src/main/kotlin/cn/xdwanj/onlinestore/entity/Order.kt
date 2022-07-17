package cn.xdwanj.onlinestore.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import java.math.BigDecimal
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
@TableName("mmall_order")
@ApiModel(value = "Order对象", description = "")
class Order {

        @ApiModelProperty("订单id")
    @TableId(value = "id", type = IdType.AUTO)
    var id: Int? = null

        @ApiModelProperty("订单号")
    var orderNo: Long? = null

        @ApiModelProperty("用户id")
    var userId: Int? = null

    var shippingId: Int? = null

        @ApiModelProperty("实际付款金额,单位是元,保留两位小数")
    var payment: BigDecimal? = null

        @ApiModelProperty("支付类型,1-在线支付")
    var paymentType: Int? = null

        @ApiModelProperty("运费,单位是元")
    var postage: Int? = null

        @ApiModelProperty("订单状态:0-已取消-10-未付款，20-已付款，40-已发货，50-交易成功，60-交易关闭")
    var status: Int? = null

        @ApiModelProperty("支付时间")
    var paymentTime: LocalDateTime? = null

        @ApiModelProperty("发货时间")
    var sendTime: LocalDateTime? = null

        @ApiModelProperty("交易完成时间")
    var endTime: LocalDateTime? = null

        @ApiModelProperty("交易关闭时间")
    var closeTime: LocalDateTime? = null

        @ApiModelProperty("创建时间")
    var createTime: LocalDateTime? = null

        @ApiModelProperty("更新时间")
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
