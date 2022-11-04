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
@TableName("mmall_user")
@Schema(name = "User", description = "$!{table.comment}")
class User {

  @Schema(description = "用户表id")
  @TableId(value = "id", type = IdType.AUTO)
  var id: Int? = null

  @Schema(description = "用户名")
  var username: String? = null

  @Schema(description = "用户密码，MD5加密")
  var password: String? = null

  var email: String? = null

  var phone: String? = null

  @Schema(description = "找回密码问题")
  var question: String? = null

  @Schema(description = "找回密码答案")
  var answer: String? = null

  @Schema(description = "角色0-管理员,1-普通用户")
  var role: Int? = null

  @Schema(description = "创建时间")
  @TableField(fill = FieldFill.INSERT)
  var createTime: LocalDateTime? = null

  @Schema(description = "更新时间")
  @TableField(fill = FieldFill.INSERT_UPDATE)
  var updateTime: LocalDateTime? = null

  override fun toString(): String {
    return "User{" +
        "id=" + id +
        ", username=" + username +
        ", password=" + password +
        ", email=" + email +
        ", phone=" + phone +
        ", question=" + question +
        ", answer=" + answer +
        ", role=" + role +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}"
  }
}
