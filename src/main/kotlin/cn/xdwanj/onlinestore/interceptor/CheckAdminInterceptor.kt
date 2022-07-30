package cn.xdwanj.onlinestore.interceptor

import cn.xdwanj.onlinestore.common.CURRENT_USER
import cn.xdwanj.onlinestore.entity.User
import cn.xdwanj.onlinestore.service.UserService
import cn.xdwanj.onlinestore.util.Slf4j
import cn.xdwanj.onlinestore.util.Slf4j.Companion.logger
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Slf4j
@Component
class CheckAdminInterceptor(
  private val userService: UserService
) : HandlerInterceptor {
  override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
    logger.info("检查用户权限是否为管理员")
    val user = request.session.getAttribute(CURRENT_USER) as User

    return userService.checkAdmin(user)
  }
}