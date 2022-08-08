package cn.xdwanj.onlinestore.controller.portal

import cn.xdwanj.onlinestore.common.*
import cn.xdwanj.onlinestore.entity.User
import cn.xdwanj.onlinestore.exception.BusinessException
import cn.xdwanj.onlinestore.service.UserService
import cn.xdwanj.onlinestore.util.encodeByMD5
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*
import javax.servlet.http.HttpSession

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XDwanj
 * @since 2022-07-16
 */
@Tag(name = "用户模块")
@Validated
@RestController
@RequestMapping("/user")
class UserController(
  private val userService: UserService,
  private val tokenCache: TokenCache
) {
  @Operation(summary = "登录")
  @PostMapping("/login")
  fun login(
    username: String,
    password: String,
    @Parameter(hidden = true) session: HttpSession
  ): ServerResponse<User> {
    if (
      username.isBlank() ||
      password.isBlank()
    ) return ServerResponse.error("数据不可为空")

    val user = userService.login(username, password)?.apply {
      session.setAttribute(CURRENT_USER, this)
    }

    return if (user == null) {
      ServerResponse.error("用户登录失败")
    } else {
      ServerResponse.success(data = user)
    }
  }

  @Operation(summary = "注销")
  @GetMapping("/logout")
  fun logout(@Parameter(hidden = true) session: HttpSession): ServerResponse<String> {
    session.removeAttribute(CURRENT_USER)
    return ServerResponse.success("注销成功")
  }

  @Operation(summary = "注册")
  @PostMapping("/register")
  fun register(user: User): ServerResponse<User> {
    if (
      user.username.isNullOrBlank() ||
      user.password.isNullOrBlank() ||
      user.email.isNullOrBlank()
    ) return ServerResponse.error("参数不可为空")

    if (userService.checkUsername(user.username)) {
      return ServerResponse.error("用户名已存在")
    }

    if (userService.checkEmail(user.email)) {
      return ServerResponse.error("邮箱已存在")
    }

    user.apply {
      role = RoleEnum.CUSTOMER.code
      password = password?.encodeByMD5() ?: throw BusinessException("MD5编码失败")
    }
    userService.save(user).let {
      if (it) return ServerResponse.error("注册失败")
    }

    return ServerResponse.success("注册成功")
  }

  @Operation(summary = "检查数据是否存在")
  @PostMapping("/checkValid")
  fun checkValid(value: String, type: String): ServerResponse<User> {
    if (
      value.isBlank() ||
      type.isBlank()
    ) return ServerResponse.error("数据不可为空")
    return userService.checkValid(value, type) // TODO: recode
  }

  @Operation(summary = "返回密码重置问题")
  @GetMapping("/question")
  fun question(
    username: String
  ): ServerResponse<String> {
    if (username.isBlank()) {
      return ServerResponse.error("用户名不可为空")
    }

    if (!userService.checkUsername(username))
      return ServerResponse.error("用户名不存在")

    val question = userService.ktQuery()
      .eq(User::username, username)
      .select(User::question)
      .one()
      .question
      ?: return ServerResponse.error("找回密码的问题是空的")

    return ServerResponse.success(data = question)
  }

  @Operation(summary = "回答密码重置问题")
  @PostMapping("/question")
  fun question(
    username: String,
    question: String,
    answer: String
  ): ServerResponse<String> {
    if (
      username.isBlank() ||
      question.isBlank() ||
      answer.isBlank()
    ) return ServerResponse.error("参数不可为空")

    val exists = userService.ktQuery()
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

  @Operation(summary = "重置密码")
  @PutMapping("/password/forget")
  fun resetPassword(
    username: String,
    passwordNew: String,
    forgetToken: String
  ): ServerResponse<String> {
    if (
      username.isBlank() ||
      passwordNew.isBlank() ||
      forgetToken.isBlank()
    ) return ServerResponse.error("参数不可为空")

    if (!userService.checkUsername(username)) {
      return ServerResponse.error("用户不存在")
    }

    val token = tokenCache[TOKEN_PREFIX + username]
    if (token.isNullOrBlank()) {
      return ServerResponse.error("token过期或者无效")
    }

    return if (token == forgetToken) {
      val md5Pwd = passwordNew.encodeByMD5()
      userService.ktUpdate()
        .eq(User::username, username)
        .set(User::password, md5Pwd)
        .set(User::updateTime, LocalDateTime.now())
        .update()
      ServerResponse.success("修改密码成功")
    } else {
      ServerResponse.error("token无效，请重新获取")
    }
  }

  @Operation(summary = "登录状态下重置密码")
  @PutMapping("/password/reset")
  fun resetPassword(
    @Parameter(hidden = true) session: HttpSession,
    passwordOld: String,
    passwordNew: String
  ): ServerResponse<String> {
    if (
      passwordNew.isBlank() ||
      passwordOld.isBlank()
    ) return ServerResponse.error("参数不可为空")

    val user = session.getAttribute(CURRENT_USER) as User

    val userId = user.id ?: return ServerResponse.error("用户ID不可为空")
    if (!userService.checkPassword(userId, passwordOld.encodeByMD5())) {
      return ServerResponse.error("旧密码错误")
    }

    userService.ktUpdate()
      .eq(User::id, userId)
      .set(User::password, passwordNew.encodeByMD5())
      .update()
      .let {
        if (!it) return ServerResponse.error("密码更新失败")
      }

    return ServerResponse.success("密码更新成功")
  }

  @Operation(summary = "更新用户信息")
  @PutMapping("/info")
  fun info(
    @Parameter(hidden = true) session: HttpSession,
    userNew: User
  ): ServerResponse<User> {
    if (userService.checkUsername(userNew.email)) {
      return ServerResponse.error("email已存在，请更换email")
    }

    val currentUser = session.getAttribute(CURRENT_USER) as User
    userNew.id = currentUser.id
    userNew.username = currentUser.username

    userService.ktUpdate()
      .eq(User::id, userNew.id)
      .set(User::email, userNew.email)
      .set(User::phone, userNew.phone)
      .set(User::question, userNew.question)
      .set(User::answer, userNew.answer)
      .set(User::updateTime, LocalDateTime.now())
      .update()
      .let { if (!it) return ServerResponse.error("更新个人信息失败") }


    return ServerResponse.success("更新个人信息成功", userNew)

  }

  @Operation(summary = "从数据库中返回用户信息")
  @GetMapping("/info/db")
  fun info(@Parameter(hidden = true) session: HttpSession): ServerResponse<User> {
    val currentUser = session.getAttribute(CURRENT_USER) as User

    val user = userService.ktQuery()
      .eq(User::id, currentUser.id)
      .one()
      ?: let {
        session.removeAttribute(CURRENT_USER)
        return ServerResponse.error("找不到当前用户")
      }

    user.password = ""
    return ServerResponse.success(data = user)
  }

  @Operation(summary = "从Session中返回用户信息")
  @GetMapping("/info/current")
  fun currentInfo(@Parameter(hidden = true) session: HttpSession): ServerResponse<User> {
    val user = session.getAttribute(CURRENT_USER) as User
    return ServerResponse.success(data = user)
  }
}