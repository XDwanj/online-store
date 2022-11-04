package cn.xdwanj.onlinestore.controller.backend

import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.common.*
import cn.xdwanj.onlinestore.constant.AUTHORIZATION_TOKEN
import cn.xdwanj.onlinestore.constant.RoleEnum
import cn.xdwanj.onlinestore.response.CommonResponse
import cn.xdwanj.onlinestore.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import javax.servlet.http.HttpSession
import org.springframework.web.bind.annotation.*

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
  private val cacheMemory: CacheMemory
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

    if (user.role != RoleEnum.ADMIN.code) {
      return CommonResponse.error("登录的用户并非管理员")
    }

    val userToken = getTokenByPrefix(USER_TOKEN_PREFIX)
    cacheMemory[userToken] = user
    return CommonResponse.success(data = userToken)
  }

  @Operation(summary = "后台用户注销")
  @GetMapping("/logout")
  fun logout(
    @Parameter(hidden = true)
    @RequestHeader(AUTHORIZATION_TOKEN)
    authorizationToken: String
  ): CommonResponse<String> {
    cacheMemory.remove(authorizationToken)
    return CommonResponse.success("注销成功")
  }

}