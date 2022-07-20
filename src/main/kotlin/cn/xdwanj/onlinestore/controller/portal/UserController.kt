package cn.xdwanj.onlinestore.controller.portal

import cn.xdwanj.onlinestore.common.CURRENT_USER
import cn.xdwanj.onlinestore.common.ResponseCode
import cn.xdwanj.onlinestore.common.ServerResponse
import cn.xdwanj.onlinestore.entity.User
import cn.xdwanj.onlinestore.service.UserService
import cn.xdwanj.onlinestore.util.Slf4j
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore
import java.time.LocalDateTime
import javax.servlet.http.HttpSession

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XDwanj
 * @since 2022-07-16
 */
@Slf4j
@Api("用户模块")
@RestController
@RequestMapping("/user")
class UserController(
  private val userService: UserService
) {
  @ApiOperation("登录")
  @PostMapping("/login")
  fun login(
    username: String,
    password: String,
    @ApiIgnore session: HttpSession
  ): ServerResponse<User> {
    val response = userService.login(username, password)
    if (response.isSuccess()) {
      session.setAttribute(CURRENT_USER, response.data)
    }
    return response
  }

  @ApiOperation("注销")
  @GetMapping("/logout")
  fun logout(@ApiIgnore session: HttpSession): ServerResponse<User> {
    session.removeAttribute(CURRENT_USER)
    return ServerResponse.createBySuccess()
  }

  @ApiOperation("注册")
  @PostMapping("/register")
  fun register(user: User): ServerResponse<User> {
    return userService.register(user)
  }

  @ApiOperation("检查数据是否存在")
  @GetMapping("/checkValid/{value}")
  fun checkValid(
    @PathVariable value: String,
    type: String
  ): ServerResponse<User> {
    return userService.checkValid(value, type) // TODO: recode
  }

  @ApiOperation("返回用户数据")
  @GetMapping("/info/current")
  fun currentInfo(@ApiIgnore session: HttpSession): ServerResponse<User> {
    // 两种都可以
    session.getAttribute(CURRENT_USER)?.let {
      return ServerResponse.createBySuccessData((it as User))
    }
    return ServerResponse.createByError("用户未登录")

    // val user = session.getAttribute(CURRENT_USER) as User?
    //   ?: return ServerResponse.createByError("用户未登录")
    // return ServerResponse.createBySuccessData(user)
  }

  @ApiOperation("返回密码重置问题")
  @GetMapping("/question")
  fun question(
    username: String
  ): ServerResponse<String> {
    return userService.getQuestion(username)
  }

  @ApiOperation("回答密码重置问题")
  @PostMapping("/question")
  fun question(
    username: String,
    question: String,
    answer: String
  ): ServerResponse<User> {
    return userService.checkAnswer(username, question, answer)
  }

  @ApiOperation("重置密码")
  @PatchMapping("/password/forget")
  fun resetPassword(
    username: String,
    passwordNew: String,
    forgetToken: String
  ): ServerResponse<String> {
    return userService.forgetResetPassword(username, passwordNew, forgetToken)
  }

  @ApiOperation("登录状态下重置密码")
  @PatchMapping("/password/reset")
  fun resetPassword(
    @ApiIgnore session: HttpSession,
    passwordOld: String,
    passwordNew: String
  ): ServerResponse<String> {
    val user = session.getAttribute(CURRENT_USER) as User?
      ?: return ServerResponse.createByError("用户未登录")

    return userService.resetPassword(user, passwordOld, passwordNew)
  }

  @ApiOperation("更新用户信息")
  @PatchMapping("/info")
  fun updateInfo(
    @ApiIgnore session: HttpSession,
    userNew: User
  ): ServerResponse<User> {
    val currentUser = session.getAttribute(CURRENT_USER) as User?
      ?: return ServerResponse.createByError("当前用户未登录")
    userNew.id = currentUser.id
    userNew.username = currentUser.username
    return userService.updateInfo(userNew).also {
      if (it.isSuccess()) {
        it.data?.username = currentUser.username
        session.setAttribute(CURRENT_USER, it.data)
      }
    }
  }

  @ApiOperation("获取用户信息")
  @GetMapping("/info/db")
  fun info(@ApiIgnore session: HttpSession): ServerResponse<User> {
    val user = session.getAttribute(CURRENT_USER) as User?
      ?: return ServerResponse.createByError("用户未登录", ResponseCode.NEED_LOGIN.code)

    return userService.getInfo(user.id)
  }
}