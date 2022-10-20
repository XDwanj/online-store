package cn.xdwanj.onlinestore.interceptor

import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.annotation.Slf4j.Companion.logger
import cn.xdwanj.onlinestore.common.AUTHORIZATION_TOKEN
import cn.xdwanj.onlinestore.common.TokenCache
import cn.xdwanj.onlinestore.common.USER_REQUEST
import cn.xdwanj.onlinestore.entity.User
import cn.xdwanj.onlinestore.exception.BusinessException
import cn.xdwanj.onlinestore.response.ResponseCode
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Slf4j
@Component
class LoginInterceptor(
  private val tokenCache: TokenCache
) : HandlerInterceptor {

  override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
    logger.info("开始检查是否登录")
    val header = request.getHeader(AUTHORIZATION_TOKEN)
      ?: throw BusinessException("请携带token，token请求头为空", ResponseCode.NEED_LOGIN.code)

    val user = tokenCache.get<User>(header)
      ?: throw BusinessException(ResponseCode.NEED_LOGIN.msg, ResponseCode.NEED_LOGIN.code)

    logger.info("用户已登录, 已将 ${user.id} 放入Request域中")
    request.setAttribute(USER_REQUEST, user)

    return true
  }
}