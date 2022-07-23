package cn.xdwanj.onlinestore.service.impl

import cn.xdwanj.onlinestore.common.*
import cn.xdwanj.onlinestore.entity.User
import cn.xdwanj.onlinestore.mapper.UserMapper
import cn.xdwanj.onlinestore.service.UserService
import cn.xdwanj.onlinestore.util.encodeByMD5
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XDwanj
 * @since 2022-07-16
 */
@Service
open class UserServiceImpl(
  private val tokenCache: TokenCache
) : ServiceImpl<UserMapper, User>(), UserService {
  override fun checkValid(str: String?, type: String?): ServerResponse<User> {
    if (type.isNullOrBlank()) {
      return ServerResponse.error("参数错误")
    }
    when (type) {
      EMAIL -> if (checkEmail(str)) {
        return ServerResponse.error("邮箱不存在")
      }

      USERNAME -> if (!checkUsername(str)) {
        return ServerResponse.error("用户名不存在")
      }

      else -> return ServerResponse.error("参数错误")
    }

    return ServerResponse.success("校验成功")
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

  /**
   * 并没有进行MD5编码
   */
  override fun checkPassword(userId: Int, password: String): Boolean {
    return ktQuery()
      .eq(User::id, userId)
      .eq(User::password, password)
      .exists()
  }

  override fun login(username: String, password: String): ServerResponse<User> {
    if (!checkUsername(username)) {
      return ServerResponse.error("用户名不存在")
    }

    val user = ktQuery()
      .eq(User::username, username)
      .eq(User::password, password.encodeByMD5())
      .one() ?: return ServerResponse.error("密码错误")

    user.password = ""
    return ServerResponse.success("登录成功", user)
  }

  override fun register(user: User): ServerResponse<User> {
    if (checkUsername(user.username)) {
      return ServerResponse.error("用户名已存在")
    }
    if (checkEmail(user.email)) {
      return ServerResponse.error("邮箱已存在")
    }
    save(user.apply {
      role = Role.CUSTOMER.code
      password = password?.encodeByMD5()
      createTime = LocalDateTime.now()
      updateTime = LocalDateTime.now()
    }).let {
      if (!it) return ServerResponse.error("注册失败")
    }

    return ServerResponse.success("注册成功")
  }

  override fun getQuestion(username: String): ServerResponse<String> {
    if (!checkUsername(username))
      return ServerResponse.error("用户名不存在")

    val question = ktQuery()
      .eq(User::username, username)
      .select(User::question)
      .one()
      .question

    if (question.isNullOrEmpty())
      return ServerResponse.error("找回密码的问题是空的")

    return ServerResponse.success(data = question)
  }

  override fun checkAnswer(username: String, question: String, answer: String): ServerResponse<String> {
    val exists = ktQuery()
      .eq(User::username, username)
      .eq(User::question, question)
      .eq(User::answer, answer)
      .exists()

    return if (exists) {
      val forgetToken = UUID.randomUUID().toString()
      tokenCache[TOKEN_PREFIX + username] = forgetToken
      ServerResponse.success("答案正确", forgetToken)
    } else {
      ServerResponse.error("问题的答案错误")
    }
  }

  override fun checkAdmin(user: User): Boolean {
    return user.role == Role.ADMIN.code
  }

  override fun forgetResetPassword(username: String, passwordNew: String, forgetToken: String): ServerResponse<String> {
    if (forgetToken.isBlank()) {
      return ServerResponse.error("forgetToken 需要传递")
    }
    if (!checkUsername(username)) {
      return ServerResponse.error("用户不存在")
    }
    if (passwordNew.isBlank()) {
      return ServerResponse.error("密码不可为空")
    }
    val token = tokenCache[TOKEN_PREFIX + username]
    if (token.isNullOrBlank()) {
      return ServerResponse.error("token过期或者无效")
    }
    return if (token == forgetToken) {
      val md5Pwd = passwordNew.encodeByMD5()
      ktUpdate()
        .eq(User::username, username)
        .set(User::password, md5Pwd)
        .set(User::updateTime, LocalDateTime.now())
        .update()
      ServerResponse.success("修改密码成功")
    } else {
      ServerResponse.error("token无效，请重新获取")
    }
  }

  override fun resetPassword(user: User, passwordOld: String, passwordNew: String): ServerResponse<String> {
    user.id?.let {
      if (!checkPassword(it, passwordOld.encodeByMD5())) {
        return ServerResponse.error("旧密码错误")
      }
      user.password = passwordNew.encodeByMD5()
      if (updateById(user.apply { updateTime = LocalDateTime.now() })) {
        return ServerResponse.success("密码更新成功")
      }
      return ServerResponse.error("密码更新失败")
    }
    return ServerResponse.error("用户不存在")
  }

  override fun updateInfo(user: User): ServerResponse<User> {
    if (checkUsername(user.email)) {
      return ServerResponse.error("email已存在，请更换email")
    }
    val isSuccess = ktUpdate()
      .eq(User::id, user.id)
      .set(User::email, user.email)
      .set(User::phone, user.phone)
      .set(User::question, user.question)
      .set(User::answer, user.answer)
      .set(User::updateTime, LocalDateTime.now())
      .update()

    return if (isSuccess) {
      ServerResponse.success("更新个人信息成功")
    } else {
      ServerResponse.error("更新个人信息失败")
    }
  }

  override fun getInfo(id: Int?): ServerResponse<User> {
    val user = ktQuery()
      .eq(User::id, id)
      .one() ?: return ServerResponse.error("找不到当前用户")

    user.password = ""
    return ServerResponse.success(data = user)
  }
}
