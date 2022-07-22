package cn.xdwanj.onlinestore.interceptor

import cn.xdwanj.onlinestore.common.BizException
import cn.xdwanj.onlinestore.common.CURRENT_USER
import cn.xdwanj.onlinestore.common.ResponseCode
import cn.xdwanj.onlinestore.entity.User
import cn.xdwanj.onlinestore.util.Slf4j
import cn.xdwanj.onlinestore.util.Slf4j.Companion.log
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Slf4j
@Component
class LoginInterceptor : HandlerInterceptor {

  override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
    log.info("开始检查是否登录")
    request.session.getAttribute(CURRENT_USER) as User?
      ?: let {
        log.info("用户未登录")
        throw BizException("用户未登录，请先登录", ResponseCode.ILLEGAL_ARGUMENT.code)
      }

    log.info("用户已登录")
    return true
  }
}