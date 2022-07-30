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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Category

        if (id != other.id) return false
        if (parentId != other.parentId) return false
        if (name != other.name) return false
        if (status != other.status) return false
        if (sortOrder != other.sortOrder) return false
        if (createTime != other.createTime) return false
        if (updateTime != other.updateTime) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (parentId ?: 0)
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (status?.hashCode() ?: 0)
        result = 31 * result + (sortOrder ?: 0)
        result = 31 * result + (createTime?.hashCode() ?: 0)
        result = 31 * result + (updateTime?.hashCode() ?: 0)
        return result
    }
}
