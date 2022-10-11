package cn.xdwanj.onlinestore.interceptor

import cn.xdwanj.onlinestore.exception.BusinessException
import cn.xdwanj.onlinestore.common.CURRENT_USER
import cn.xdwanj.onlinestore.response.ResponseCode
import cn.xdwanj.onlinestore.entity.User
import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.annotation.Slf4j.Companion.logger
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Slf4j
@Component
class LoginInterceptor : HandlerInterceptor {

  override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
    logger.info("开始检查是否登录")
    request.session.getAttribute(CURRENT_USER) as User?
      ?: throw BusinessException("用户未登录，请先登录", ResponseCode.NEED_LOGIN.code)

    logger.info("用户已登录")
    return true
  }
}