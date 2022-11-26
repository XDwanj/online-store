package cn.xdwanj.onlinestore.interceptor

import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.annotation.Slf4j.Companion.logger
import cn.xdwanj.onlinestore.common.CacheMemory
import cn.xdwanj.onlinestore.constant.AUTHORIZATION_TOKEN
import cn.xdwanj.onlinestore.constant.USER_REQUEST
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
  private val cacheMemory: CacheMemory
) : HandlerInterceptor {

  override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
    logger.info("开始检查是否登录")
    val header = request.getHeader(AUTHORIZATION_TOKEN)
      ?: throw BusinessException("请携带token，token请求头为空", ResponseCode.NEED_LOGIN.code)

    val user = cacheMemory.get<User>(header)
      ?: throw BusinessException(ResponseCode.NEED_LOGIN.msg, ResponseCode.NEED_LOGIN.code)

    logger.info("用户已登录, 已将 $user 放入Request域中")
    request.setAttribute(USER_REQUEST, user)

    return true
  }

  // TODO：如已启用SaToken，请取消注释
  // override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
  //   val user = StpUtil.getSession()[USER_SESSION] as User
  //   request.setAttribute(USER_REQUEST, user)
  //   logger.info("用户已登录, 已将 $user 放入Request域中")
  //
  //   return true
  // }
}