package cn.xdwanj.onlinestore.controller.portal

import cn.xdwanj.onlinestore.common.*
import cn.xdwanj.onlinestore.constant.AUTHORIZATION_TOKEN
import cn.xdwanj.onlinestore.constant.RoleEnum
import cn.xdwanj.onlinestore.constant.USER_REQUEST
import cn.xdwanj.onlinestore.entity.User
import cn.xdwanj.onlinestore.exception.BusinessException
import cn.xdwanj.onlinestore.response.CommonResponse
import cn.xdwanj.onlinestore.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*

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
  private val cacheMemory: CacheMemory
) {
  @Operation(summary = "登录")
  @PostMapping("/login")
  fun login(
    username: String,
    password: String,
  ): CommonResponse<String> {
    if (
      username.isBlank() ||
      password.isBlank()
    ) return CommonResponse.error("数据不可为空")

    val user = userService.login(username, password)
    val authorizationToken = getTokenByPrefix(USER_TOKEN_PREFIX)
    cacheMemory[authorizationToken] = user

    return CommonResponse.success(data = authorizationToken)
  }

  @Operation(summary = "注销")
  @GetMapping("/logout")
  fun logout(
    @Parameter(hidden = true)
    @RequestHeader(AUTHORIZATION_TOKEN)
    token: String
  ): CommonResponse<String> {
    cacheMemory.remove(token)
    return CommonResponse.success("注销成功")
  }

  @Operation(summary = "注册")
  @PostMapping("/register")
  fun register(user: User): CommonResponse<User> {
    if (
      user.username.isNullOrBlank() ||
      user.password.isNullOrBlank() ||
      user.email.isNullOrBlank()
    ) return CommonResponse.error("参数不可为空")

    if (userService.checkUsername(user.username)) {
      return CommonResponse.error("用户名已存在")
    }

    if (userService.checkEmail(user.email)) {
      return CommonResponse.error("邮箱已存在")
    }

    user.apply {
      role = RoleEnum.CUSTOMER.code
      password = password?.encodeByMD5() ?: throw BusinessException("MD5编码失败")
    }
    val isSuccess = userService.save(user)

    if (!isSuccess) {
      return CommonResponse.error("注册失败")
    }
    return CommonResponse.success("注册成功")
  }

  @Operation(summary = "检查数据是否存在")
  @PostMapping("/checkValid")
  fun checkValid(value: String, type: String): CommonResponse<String> {
    if (
      value.isBlank() ||
      type.isBlank()
    ) return CommonResponse.error("数据不可为空")
    return userService.checkValid(value, type)
  }

  @Operation(summary = "返回密码重置问题")
  @GetMapping("/question")
  fun question(
    username: String
  ): CommonResponse<String> {
    if (username.isBlank()) {
      return CommonResponse.error("用户名不可为空")
    }

    if (!userService.checkUsername(username)) {
      return CommonResponse.error("用户名不存在")
    }

    val user = userService.ktQuery()
      .eq(User::username, username)
      .select(User::question)
      .one()
      ?: throw BusinessException("用户不存在")

    val question = user.question

    if (question.isNullOrBlank()) {
      return CommonResponse.error("找回密码的问题是空的")
    }
    return CommonResponse.success(data = question)
  }

  @Operation(summary = "回答密码重置问题")
  @PostMapping("/question")
  fun question(
    username: String,
    question: String,
    answer: String
  ): CommonResponse<Any> {
    if (
      username.isBlank() ||
      question.isBlank() ||
      answer.isBlank()
    ) return CommonResponse.error("参数不可为空")

    val exists = userService.ktQuery()
      .eq(User::username, username)
      .eq(User::question, question)
      .eq(User::answer, answer)
      .exists()

    return if (exists) {
      val forgetToken = UUID.randomUUID().toString()
      cacheMemory[FORGET_TOKEN_PREFIX + username] = forgetToken
      CommonResponse.success("答案正确", forgetToken)
    } else {
      CommonResponse.error("问题的答案错误")
    }
  }

  @Operation(summary = "重置密码")
  @PutMapping("/password/forget")
  fun resetPassword(
    username: String,
    passwordNew: String,
    forgetToken: String
  ): CommonResponse<Any> {
    if (
      username.isBlank() ||
      passwordNew.isBlank() ||
      forgetToken.isBlank()
    ) return CommonResponse.error("参数不可为空")

    if (!userService.checkUsername(username)) {
      return CommonResponse.error("用户不存在")
    }

    val token = cacheMemory.get<String>(FORGET_TOKEN_PREFIX + username)
    if (token.isNullOrBlank()) {
      return CommonResponse.error("token过期或者无效")
    }

    return if (token == forgetToken) {
      val md5Pwd = passwordNew.encodeByMD5()
      userService.ktUpdate()
        .eq(User::username, username)
        .set(User::password, md5Pwd)
        .set(User::updateTime, LocalDateTime.now())
        .update()
      CommonResponse.success("修改密码成功")
    } else {
      CommonResponse.error("token无效，请重新获取")
    }
  }

  @Operation(summary = "登录状态下重置密码")
  @PutMapping("/password/reset")
  fun resetPassword(
    @Parameter(hidden = true)
    @RequestAttribute(USER_REQUEST)
    user: User,
    passwordOld: String,
    passwordNew: String
  ): CommonResponse<Any> {
    if (
      passwordNew.isBlank() ||
      passwordOld.isBlank()
    ) return CommonResponse.error("参数不可为空")

    val userId = user.id
      ?: return CommonResponse.error("用户ID不可为空")

    if (!userService.checkPassword(userId, passwordOld)) {
      return CommonResponse.error("旧密码错误")
    }

    val isSuccess = userService.ktUpdate()
      .eq(User::id, userId)
      .set(User::password, passwordNew.encodeByMD5())
      .update()

    if (!isSuccess) {
      return CommonResponse.error("密码更新失败")
    }
    return CommonResponse.success("密码更新成功")
  }

  @Operation(summary = "更新用户信息")
  @PutMapping("/info")
  fun info(
    @Parameter(hidden = true)
    @RequestAttribute(USER_REQUEST)
    currentUser: User,
    userNew: User
  ): CommonResponse<User> {
    if (userService.checkUsername(userNew.email)) {
      return CommonResponse.error("email已存在，请更换email")
    }

    userNew.id = currentUser.id
    userNew.username = currentUser.username
    userNew.role = currentUser.role

    val isSuccess = userService.updateById(userNew)

    return if (isSuccess) {
      CommonResponse.success("更新个人信息成功", userNew)
    } else {
      CommonResponse.error("更新个人信息失败")
    }
  }

  @Operation(summary = "从数据库中返回用户信息")
  @GetMapping("/info/db")
  fun info(
    @Parameter(hidden = true)
    @RequestHeader(AUTHORIZATION_TOKEN)
    token: String
  ): CommonResponse<User> {
    val currentUser = cacheMemory.get<User>(token)

    val user = userService.ktQuery()
      .eq(User::id, currentUser!!.id)
      .one()
      ?: let {
        cacheMemory.remove(token)
        return CommonResponse.error("找不到当前用户")
      }

    user.password = ""
    return CommonResponse.success(data = user)
  }

  @Operation(summary = "从缓存中返回用户信息")
  @GetMapping("/info/current")
  fun currentInfo(
    @Parameter(hidden = true)
    @RequestAttribute(USER_REQUEST)
    user: User
  ): CommonResponse<User> {
    return CommonResponse.success(data = user)
  }
}