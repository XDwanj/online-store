package cn.xdwanj.onlinestore.interceptor

import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.annotation.Slf4j.Companion.logger
import cn.xdwanj.onlinestore.constant.USER_REQUEST
import cn.xdwanj.onlinestore.entity.User
import cn.xdwanj.onlinestore.exception.BusinessException
import cn.xdwanj.onlinestore.response.ResponseCode
import cn.xdwanj.onlinestore.service.UserService
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
    val user = request.getAttribute(USER_REQUEST) as User?
      ?: throw BusinessException("用户登录已失效", ResponseCode.NEED_LOGIN.code)

    val isAdmin = userService.checkAdmin(user)

    return isAdmin.apply {
      if (this) {
        logger.info("用户权限为管理员")
      } else {
        throw BusinessException("${user.username} 用户并非管理员")
      }
    }
  }
}