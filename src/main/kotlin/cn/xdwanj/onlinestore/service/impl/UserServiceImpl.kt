package cn.xdwanj.onlinestore.service.impl

import cn.xdwanj.onlinestore.common.EMAIL
import cn.xdwanj.onlinestore.common.Role
import cn.xdwanj.onlinestore.common.ServerResponse
import cn.xdwanj.onlinestore.common.USERNAME
import cn.xdwanj.onlinestore.entity.User
import cn.xdwanj.onlinestore.mapper.UserMapper
import cn.xdwanj.onlinestore.service.UserService
import cn.xdwanj.onlinestore.util.encodeByMD5
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import java.util.UUID

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XDwanj
 * @since 2022-07-16
 */
@Service
open class UserServiceImpl : ServiceImpl<UserMapper, User>(), UserService {
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

  override fun checkAnswer(username: String, question: String, answer: String): ServerResponse<String> {
    ktQuery()
      .eq(User::username, username)
      .eq(User::question, question)
      .eq(User::answer, answer)
      .exists().let {
        if (it) {
          val forgetToken = UUID.randomUUID().toString()
          TODO("tokenCache.setKey(TokenCache.TOKEN_PREFIX + username, forgetToken)")
          // return ServerResponse.createBySuccessData(forgetToken)
        }
      }
    TODO()
  }
}
