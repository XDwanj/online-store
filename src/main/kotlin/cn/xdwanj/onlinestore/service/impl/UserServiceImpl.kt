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
  private val tokenCache: TokenCache,
  private val userMapper: UserMapper
) : ServiceImpl<UserMapper, User>(), UserService {
  override fun checkValid(str: String?, type: String?): ServerResponse<User> {
    if (type.isNullOrBlank()) {
      return ServerResponse.createByError("参数错误")
    }
    when (type) {
      EMAIL -> if (checkEmail(str)) {
        return ServerResponse.createByError("邮箱不存在")
      }

      USERNAME -> if (!checkUsername(str)) {
        return ServerResponse.createByError("用户名不存在")
      }

      else -> return ServerResponse.createByError("参数错误")
    }

    return ServerResponse.createBySuccessMsg("校验成功")
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
      return ServerResponse.createByError("用户名不存在")
    }

    val user = ktQuery()
      .eq(User::username, username)
      .eq(User::password, password.encodeByMD5())
      .one() ?: return ServerResponse.createByError("密码错误")

    user.password = ""
    return ServerResponse.createBySuccess("登录成功", user)
  }

  override fun register(user: User): ServerResponse<User> {
    if (checkUsername(user.username)) {
      return ServerResponse.createByError("用户名已存在")
    }
    if (checkEmail(user.email)) {
      return ServerResponse.createByError("邮箱已存在")
    }
    if (user.password.isNullOrEmpty()) {
      return ServerResponse.createByError("密码不可为空")
    }
    save(user.apply {
      role = Role.CUSTOMER.code
      createTime = LocalDateTime.now()
      updateTime = LocalDateTime.now()
      password = password?.encodeByMD5()
    }).let {
      if (!it) return ServerResponse.createByError("注册失败")
    }

    return ServerResponse.createBySuccessMsg("注册成功")
  }

  override fun getQuestion(username: String): ServerResponse<String> {
    if (!checkUsername(username))
      return ServerResponse.createByError("用户名不存在")

    val question = ktQuery()
      .eq(User::username, username)
      .select(User::question)
      .one()
      .question

    if (question.isNullOrEmpty())
      return ServerResponse.createByError("找回密码的问题是空的")

    return ServerResponse.createBySuccessData(question)
  }

  override fun checkAnswer(username: String, question: String, answer: String): ServerResponse<User> {
    val exists = ktQuery()
      .eq(User::username, username)
      .eq(User::question, question)
      .eq(User::answer, answer)
      .exists()

    return if (exists) {
      val forgetToken = UUID.randomUUID().toString()
      tokenCache["${TOKEN_PREFIX}username"] = forgetToken
      ServerResponse.createBySuccessMsg("答案正确")
    } else {
      ServerResponse.createByError("问题的答案错误")
    }
  }

  override fun forgetResetPassword(username: String, passwordNew: String, forgetToken: String): ServerResponse<String> {
    if (forgetToken.isBlank()) {
      return ServerResponse.createByError("forgetToken 需要传递")
    }
    if (!checkUsername(username)) {
      return ServerResponse.createByError("用户不存在")
    }
    if (passwordNew.isBlank()) {
      return ServerResponse.createByError("密码不可为空")
    }
    val token = tokenCache[TOKEN_PREFIX + username]
    if (token.isNullOrBlank()) {
      return ServerResponse.createByError("token过期或者无效")
    }
    return if (token == forgetToken) {
      val md5Pwd = passwordNew.encodeByMD5()
      ktUpdate()
        .eq(User::username, username)
        .set(User::password, md5Pwd)
        .set(User::updateTime, LocalDateTime.now())
        .update()
      ServerResponse.createBySuccessMsg("修改密码成功")
    } else {
      ServerResponse.createByError("token无效，请重新获取")
    }
  }

  override fun resetPassword(user: User, passwordOld: String, passwordNew: String): ServerResponse<String> {
    user.id?.let {
      if (!checkPassword(it, passwordOld.encodeByMD5())) {
        return ServerResponse.createByError("旧密码错误")
      }
      user.password = passwordNew.encodeByMD5()
      if (updateById(user.apply { updateTime = LocalDateTime.now() })) {
        return ServerResponse.createBySuccessMsg("密码更新成功")
      }
      return ServerResponse.createByError("密码更新失败")
    }
    return ServerResponse.createByError("用户不存在")
  }

  override fun updateInfo(user: User): ServerResponse<User> {
    if (checkUsername(user.email)) {
      return ServerResponse.createByError("email已存在，请更换email")
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
      ServerResponse.createBySuccessMsg("更新个人信息成功")
    } else {
      ServerResponse.createByError("更新个人信息失败")
    }
  }

  override fun getInfo(id: Int?): ServerResponse<User> {
    val user = ktQuery()
      .eq(User::id, id)
      .one() ?: return ServerResponse.createByError("找不到当前用户")

    user.password = ""
    return ServerResponse.createBySuccessData(user)
  }
}
