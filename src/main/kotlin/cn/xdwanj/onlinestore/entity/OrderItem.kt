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
@TableName("mmall_order_item")
@ApiModel(value = "OrderItem对象", description = "")
class OrderItem {

        @ApiModelProperty("订单子表id")
    @TableId(value = "id", type = IdType.AUTO)
    var id: Int? = null

    var userId: Int? = null

    var orderNo: Long? = null

        @ApiModelProperty("商品id")
    var productId: Int? = null

        @ApiModelProperty("商品名称")
    var productName: String? = null

        @ApiModelProperty("商品图片地址")
    var productImage: String? = null

        @ApiModelProperty("生成订单时的商品单价，单位是元,保留两位小数")
    var currentUnitPrice: BigDecimal? = null

        @ApiModelProperty("商品数量")
    var quantity: Int? = null

        @ApiModelProperty("商品总价,单位是元,保留两位小数")
    var totalPrice: BigDecimal? = null

    var createTime: LocalDateTime? = null

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
