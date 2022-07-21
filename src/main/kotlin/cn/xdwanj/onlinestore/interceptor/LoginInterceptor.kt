package cn.xdwanj.onlinestore.interceptor

import cn.xdwanj.onlinestore.common.CURRENT_USER
import cn.xdwanj.onlinestore.common.ResponseCode
import cn.xdwanj.onlinestore.common.ServerResponse
import cn.xdwanj.onlinestore.entity.User
import cn.xdwanj.onlinestore.util.Slf4j
import cn.xdwanj.onlinestore.util.Slf4j.Companion.log
import cn.xdwanj.onlinestore.util.returnJson
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Slf4j
@Component
class LoginInterceptor(
  private val objectMapper: ObjectMapper
) : HandlerInterceptor {

  override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
    log.info("开始检查是否登录")
    request.session.getAttribute(CURRENT_USER) as User?
      ?: let {
        log.info("用户未登录")
        val serverResponse = ServerResponse.createByError<String>("用户未登录，请登录", ResponseCode.NEED_LOGIN.code)
        val json = objectMapper.writeValueAsString(serverResponse)
        response.returnJson(json)
        return false
      }

    log.info("用户已登录")
    return true
  }
}