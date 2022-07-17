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
@TableName("mmall_product")
@ApiModel(value = "Product对象", description = "")
class Product {

        @ApiModelProperty("商品id")
    @TableId(value = "id", type = IdType.AUTO)
    var id: Int? = null

        @ApiModelProperty("分类id,对应mmall_category表的主键")
    var categoryId: Int? = null

        @ApiModelProperty("商品名称")
    var name: String? = null

        @ApiModelProperty("商品副标题")
    var subtitle: String? = null

        @ApiModelProperty("产品主图,url相对地址")
    var mainImage: String? = null

        @ApiModelProperty("图片地址,json格式,扩展用")
    var subImages: String? = null

        @ApiModelProperty("商品详情")
    var detail: String? = null

        @ApiModelProperty("价格,单位-元保留两位小数")
    var price: BigDecimal? = null

        @ApiModelProperty("库存数量")
    var stock: Int? = null

        @ApiModelProperty("商品状态.1-在售 2-下架 3-删除")
    var status: Int? = null

        @ApiModelProperty("创建时间")
    var createTime: LocalDateTime? = null

        @ApiModelProperty("更新时间")
    var updateTime: LocalDateTime? = null

    override fun toString(): String {
        return "Product{" +
        "id=" + id +
        ", categoryId=" + categoryId +
        ", name=" + name +
        ", subtitle=" + subtitle +
        ", mainImage=" + mainImage +
        ", subImages=" + subImages +
        ", detail=" + detail +
        ", price=" + price +
        ", stock=" + stock +
        ", status=" + status +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}"
    }
}
