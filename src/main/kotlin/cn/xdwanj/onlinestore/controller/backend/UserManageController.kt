package cn.xdwanj.onlinestore.controller.backend

import cn.xdwanj.onlinestore.common.CURRENT_USER
import cn.xdwanj.onlinestore.common.Role
import cn.xdwanj.onlinestore.common.ServerResponse
import cn.xdwanj.onlinestore.entity.User
import cn.xdwanj.onlinestore.service.UserService
import cn.xdwanj.onlinestore.util.Slf4j
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore
import javax.servlet.http.HttpSession

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XDwanj
 * @since 2022-07-21
 */
@Api(tags = ["后台管理员模块"])
@Slf4j
@RestController
@RequestMapping("/manage/user")
class UserManageController(
  private val userService: UserService
) {

  @ApiOperation("后台用户登录")
  @PostMapping("/login")
  fun login(
    username: String,
    password: String,
    @ApiIgnore session: HttpSession
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