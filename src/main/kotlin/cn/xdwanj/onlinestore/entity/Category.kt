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
@TableName("mmall_category")
@Schema(name = "Category", description = "$!{table.comment}")
class Category {

    @Schema(description = "类别Id")
    @TableId(value = "id", type = IdType.AUTO)
    var id: Int? = null

    @Schema(description = "父类别id当id=0时说明是根节点,一级类别")
    var parentId: Int? = null

    @Schema(description = "类别名称")
    var name: String? = null

    @Schema(description = "类别状态1-正常,2-已废弃")
    var status: Boolean? = null

    @Schema(description = "排序编号,同类展示顺序,数值相等则自然排序")
    var sortOrder: Int? = null

    @Schema(description = "创建时间")
    var createTime: LocalDateTime? = null

    @Schema(description = "更新时间")
    var updateTime: LocalDateTime? = null

    override fun toString(): String {
        return "Category{" +
        "id=" + id +
        ", parentId=" + parentId +
        ", name=" + name +
        ", status=" + status +
        ", sortOrder=" + sortOrder +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}"
    }
}
