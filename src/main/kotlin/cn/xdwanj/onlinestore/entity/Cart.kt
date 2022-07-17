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
@TableName("mmall_cart")
@ApiModel(value = "Cart对象", description = "")
class Cart {

    @TableId(value = "id", type = IdType.AUTO)
    var id: Int? = null

    var userId: Int? = null

        @ApiModelProperty("商品id")
    var productId: Int? = null

        @ApiModelProperty("数量")
    var quantity: Int? = null

        @ApiModelProperty("是否选择,1=已勾选,0=未勾选")
    var checked: Int? = null

        @ApiModelProperty("创建时间")
    var createTime: LocalDateTime? = null

        @ApiModelProperty("更新时间")
    var updateTime: LocalDateTime? = null

    override fun toString(): String {
        return "Cart{" +
        "id=" + id +
        ", userId=" + userId +
        ", productId=" + productId +
        ", quantity=" + quantity +
        ", checked=" + checked +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}"
    }
}