package cn.xdwanj.onlinestore.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
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
@TableName("mmall_cart")
@Schema(name = "Cart", description = "$!{table.comment}")
class Cart {

    @TableId(value = "id", type = IdType.AUTO)
    var id: Int? = null

    var userId: Int? = null

    @Schema(description = "商品id")
    var productId: Int? = null

    @Schema(description = "数量")
    var quantity: Int? = null

    @Schema(description = "是否选择,1=已勾选,0=未勾选")
    var checked: Int? = null

    @Schema(description = "创建时间")
    var createTime: LocalDateTime? = null

    @Schema(description = "更新时间")
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
