package cn.xdwanj.onlinestore.controller.backend

import cn.dev33.satoken.stp.StpUtil
import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.constant.USER_SESSION
import cn.xdwanj.onlinestore.response.CommonResponse
import cn.xdwanj.onlinestore.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpSession

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XDwanj
 * @since 2022-07-21
 */
@Tag(name = "后台管理员模块")
@Slf4j
@RestController
@RequestMapping("/manage")
class UserManageController(
  private val userService: UserService,
) {

  @Operation(summary = "后台用户登录")
  @PostMapping("/login")
  fun login(
    username: String,
    password: String,
    @Parameter(hidden = true)
    session: HttpSession
  ): CommonResponse<String> {
    val user = userService.login(username, password)

    StpUtil.login(user.id)
    StpUtil.getSession()[USER_SESSION] = user
    val token = StpUtil.getTokenValue()

    return CommonResponse.success(data = token)
  }

  @Operation(summary = "后台用户注销")
  @GetMapping("/logout")
  fun logout(
  ): CommonResponse<String> {
    StpUtil.logout()
    return CommonResponse.success("注销成功")
  }

}