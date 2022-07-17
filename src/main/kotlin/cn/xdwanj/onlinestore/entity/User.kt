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
@TableName("mmall_user")
@ApiModel(value = "User对象", description = "")
class User {

        @ApiModelProperty("用户表id")
    @TableId(value = "id", type = IdType.AUTO)
    var id: Int? = null

        @ApiModelProperty("用户名")
    var username: String? = null

        @ApiModelProperty("用户密码，MD5加密")
    var password: String? = null

    var email: String? = null

    var phone: String? = null

        @ApiModelProperty("找回密码问题")
    var question: String? = null

        @ApiModelProperty("找回密码答案")
    var answer: String? = null

        @ApiModelProperty("角色0-管理员,1-普通用户")
    var role: Int? = null

        @ApiModelProperty("创建时间")
    var createTime: LocalDateTime? = null

        @ApiModelProperty("最后一次更新时间")
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
