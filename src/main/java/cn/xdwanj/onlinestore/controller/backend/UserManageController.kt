package cn.xdwanj.onlinestore.controller.backend

import cn.xdwanj.onlinestore.common.CURRENT_USER
import cn.xdwanj.onlinestore.common.Role
import cn.xdwanj.onlinestore.common.ServerResponse
import cn.xdwanj.onlinestore.entity.User
import cn.xdwanj.onlinestore.service.UserService
import cn.xdwanj.onlinestore.util.Slf4j
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
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
  private val userService: UserService
) {

  @Operation(summary = "后台用户登录")
  @PostMapping("/login")
  fun login(
    username: String,
    password: String,
    @Parameter(hidden = true) session: HttpSession
  ): ServerResponse<User> {
    val response = userService.login(username, password)

    if (!response.isSuccess()) {
      return response
    }

    if (response.data?.role != Role.ADMIN.code) {
      return ServerResponse.error("登录的用户并非管理员")
    }

    session.setAttribute(CURRENT_USER, response.data)
    return response

  }

}