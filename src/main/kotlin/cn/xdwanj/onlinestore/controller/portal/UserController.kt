package cn.xdwanj.onlinestore.controller.portal

import cn.xdwanj.onlinestore.common.CURRENT_USER
import cn.xdwanj.onlinestore.common.ServerResponse
import cn.xdwanj.onlinestore.entity.User
import cn.xdwanj.onlinestore.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession
import javax.validation.constraints.NotBlank

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
  private val userService: UserService
) {
  @Operation(summary = "登录")
  @PostMapping("/login")
  fun login(
    @NotBlank username: String,
    @NotBlank password: String,
    @Parameter(hidden = true) session: HttpSession
  ): ServerResponse<User> {
    val response = userService.login(username, password)
    if (response.isSuccess()) {
      session.setAttribute(CURRENT_USER, response.data)
    }
    return response
  }

  @Operation(summary = "注销")
  @GetMapping("/logout")
  fun logout(@Parameter(hidden = true) session: HttpSession): ServerResponse<User> {
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

    return userService.register(user)
  }

  @Operation(summary = "检查数据是否存在")
  @GetMapping("/checkValid/{value}")
  fun checkValid(
    @PathVariable value: String,
    @NotBlank type: String
  ): ServerResponse<User> {
    return userService.checkValid(value, type) // TODO: recode
  }

  @Operation(summary = "返回密码重置问题")
  @GetMapping("/question")
  fun question(
    @NotBlank username: String
  ): ServerResponse<String> {
    // if (username.isBlank()) {
    //   return ServerResponse.error("用户名不可为空")
    // }
    return userService.getQuestion(username)
  }

  @Operation(summary = "回答密码重置问题")
  @PostMapping("/question")
  fun question(
    @NotBlank username: String,
    @NotBlank question: String,
    @NotBlank answer: String
  ): ServerResponse<String> {
    if (
      username.isBlank() ||
      question.isBlank() ||
      answer.isBlank()
    ) return ServerResponse.error("参数不可为空")


    return userService.checkAnswer(username, question, answer)
  }

  @Operation(summary = "重置密码")
  @PatchMapping("/password/forget")
  fun resetPassword(
    @NotBlank username: String,
    @NotBlank passwordNew: String,
    @NotBlank forgetToken: String
  ): ServerResponse<String> {
    if (
      username.isBlank() ||
      passwordNew.isBlank() ||
      forgetToken.isBlank()
    ) return ServerResponse.error("参数不可为空")

    return userService.forgetResetPassword(username, passwordNew, forgetToken)
  }

  @Operation(summary = "登录状态下重置密码")
  @PatchMapping("/password/reset")
  fun resetPassword(
    @Parameter(hidden = true) session: HttpSession,
    @NotBlank passwordOld: String,
    @NotBlank passwordNew: String
  ): ServerResponse<String> {
    if (
      passwordNew.isBlank() ||
      passwordOld.isBlank()
    ) return ServerResponse.error("参数不可为空")

    val user = session.getAttribute(CURRENT_USER) as User
    return userService.resetPassword(user, passwordOld, passwordNew)
  }

  @Operation(summary = "更新用户信息")
  @PatchMapping("/info")
  fun info(
    @Parameter(hidden = true) session: HttpSession,
    userNew: User
  ): ServerResponse<User> {
    val currentUser = session.getAttribute(CURRENT_USER) as User
    userNew.id = currentUser.id
    userNew.username = currentUser.username
    return userService.updateInfo(userNew).apply {
      if (isSuccess()) { // 如果更新成功，将新的用户信息传入 session
        data?.username = currentUser.username
        session.setAttribute(CURRENT_USER, data)
      }
    }
  }

  @Operation(summary = "从数据库中返回用户信息")
  @GetMapping("/info/db")
  fun info(@Parameter(hidden = true) session: HttpSession): ServerResponse<User> {
    val user = session.getAttribute(CURRENT_USER) as User
    return userService.getInfo(user.id)
  }

  @Operation(summary = "从Session中返回用户信息")
  @GetMapping("/info/current")
  fun currentInfo(@Parameter(hidden = true) session: HttpSession): ServerResponse<User> {
    val user = session.getAttribute(CURRENT_USER) as User
    return ServerResponse.success(data = user)
  }
}