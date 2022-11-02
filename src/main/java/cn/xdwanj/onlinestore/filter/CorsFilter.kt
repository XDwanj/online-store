package cn.xdwanj.onlinestore.filter

import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.annotation.Slf4j.Companion.logger
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class CorsFilter : Filter {
  override fun init(filterConfig: FilterConfig?) {
    logger.info("启动 cors 过滤器")
  }

  override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
    val resp = response as HttpServletResponse
    val req = request as HttpServletRequest

    resp.apply {
      addHeader("Access-Control-Allow-Credentials", "true")
      addHeader("Access-Control-Allow-Origin", "*")
      addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH, OPTIONS, HEAD")
      addHeader("Access-Control-Allow-Headers", "*")
      addHeader("Access-Control-Max-Age", "86400")
    }

    if ("OPTIONS" == req.method.uppercase()) {
      resp.status = HttpServletResponse.SC_OK
    } else {
      chain?.doFilter(request, response)
    }

  }

  override fun destroy() {
    logger.info("cors 过滤器结束")
  }
}