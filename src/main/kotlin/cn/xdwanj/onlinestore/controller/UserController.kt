package cn.xdwanj.onlinestore.controller

import cn.xdwanj.onlinestore.common.CURRENT_USER
import cn.xdwanj.onlinestore.common.ServerResponse
import cn.xdwanj.onlinestore.entity.User
import cn.xdwanj.onlinestore.service.UserService
import cn.xdwanj.onlinestore.util.Slf4j
import org.springframework.web.bind.annotation.*
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
@RestController
@RequestMapping("/user")
class UserController(
  private val userService: UserService
) {

  @PostMapping("/login")
  fun login(
    username: String,
    password: String,
    session: HttpSession
  ): ServerResponse<User> {
    val response = userService.login(username, password)
    if (response.isSuccess()) {
      session.setAttribute(CURRENT_USER, response.data)
    }
    return response
  }

  @GetMapping("/logout")
  fun logout(session: HttpSession): ServerResponse<User> {
    session.removeAttribute(CURRENT_USER)
    return ServerResponse.createBySuccess()
  }

  @PostMapping("/register")
  fun register(user: User): ServerResponse<User> {
    return userService.register(user)
  }

  @GetMapping("/checkValid/{value}")
  fun checkValid(
    @PathVariable value: String,
    type: String
  ): ServerResponse<User> {
    return userService.checkValid(value, type) // TODO: recode
  }

  @GetMapping("/info")
  fun info(session: HttpSession): ServerResponse<User> {
    // 两种都可以
    // session.getAttribute(CURRENT_USER)?.let {
    //   return ServerResponse.createBySuccessData(it as User)
    // }
    // return ServerResponse.createByError("用户未登录")

    val user = session.getAttribute(CURRENT_USER) as User?
      ?: return ServerResponse.createByError("用户未登录")
    return ServerResponse.createBySuccessData(user)
  }

  @GetMapping("/question/{username}")
  fun question(
    @PathVariable username: String
  ): ServerResponse<String> {
    return userService.getQuestion(username)
  }

  @PostMapping("/question/{answer}")
  fun question(
    username: String,
    question: String,
    @PathVariable answer: String
  ): ServerResponse<User> {
    return userService.checkAnswer(username, question, answer)
  }
}
