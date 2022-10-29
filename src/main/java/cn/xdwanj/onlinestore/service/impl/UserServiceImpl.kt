package cn.xdwanj.onlinestore.service.impl

import cn.xdwanj.onlinestore.common.CacheMemory
import cn.xdwanj.onlinestore.common.EMAIL
import cn.xdwanj.onlinestore.common.RoleEnum
import cn.xdwanj.onlinestore.common.USERNAME
import cn.xdwanj.onlinestore.entity.User
import cn.xdwanj.onlinestore.exception.BusinessException
import cn.xdwanj.onlinestore.mapper.UserMapper
import cn.xdwanj.onlinestore.response.CommonResponse
import cn.xdwanj.onlinestore.service.UserService
import cn.xdwanj.onlinestore.util.encodeByMD5
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XDwanj
 * @since 2022-07-16
 */
@Service
class UserServiceImpl(
  private val cacheMemory: CacheMemory
) : ServiceImpl<UserMapper, User>(), UserService {
  override fun checkValid(str: String?, type: String?): CommonResponse<String> {
    if (type.isNullOrBlank()) {
      return CommonResponse.error("参数错误")
    }
    when (type) {
      EMAIL -> if (checkEmail(str)) {
        return CommonResponse.error("邮箱不存在")
      }

      USERNAME -> if (!checkUsername(str)) {
        return CommonResponse.error("用户名不存在")
      }

      else -> return CommonResponse.error("参数错误")
    }

    return CommonResponse.success("校验成功")
  }

  override fun checkUsername(username: String?): Boolean {
    return ktQuery()
      .eq(User::username, username)
      .exists()
  }

  override fun checkEmail(email: String?): Boolean {
    return ktQuery()
      .eq(User::email, email)
      .exists()
  }

  override fun checkEmail(email: String?, userId: Int): Boolean {
    return ktQuery()
      .eq(User::id, userId)
      .eq(User::email, email)
      .exists()
  }

  override fun checkPassword(userId: Int, password: String): Boolean {
    return ktQuery()
      .eq(User::id, userId)
      .eq(User::password, password.encodeByMD5())
      .exists()
  }

  override fun login(username: String, password: String): User {
    if (!checkUsername(username)) {
      throw BusinessException("$username 该用户不存在")
    }

    val user = ktQuery()
      .eq(User::username, username)
      .eq(User::password, password.encodeByMD5())
      .one()
      ?: throw BusinessException("$username 密码错误")

    user.password = ""

    return user

  }

  override fun checkAdmin(user: User): Boolean {
    return user.role == RoleEnum.ADMIN.code
  }
}
