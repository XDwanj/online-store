package cn.xdwanj.onlinestore.exceptionHandler

import cn.xdwanj.onlinestore.common.BizException
import cn.xdwanj.onlinestore.common.ServerResponse
import cn.xdwanj.onlinestore.util.Slf4j
import cn.xdwanj.onlinestore.util.Slf4j.Companion.log
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@Slf4j
@RestControllerAdvice
class ControllerExceptionHandler {

  @ExceptionHandler(NullPointerException::class)
  fun npe(e: NullPointerException): ServerResponse<String> {
    log.error("出现 NPE 异常：", e)
    return ServerResponse.error("数据格式错误")
  }

  @ExceptionHandler(BizException::class)
  fun be(e: BizException): ServerResponse<String> {
    log.error("出现业务异常，原因是：{}", e.errorMsg)
    return ServerResponse.errorByException(e)
  }

  @ExceptionHandler(Exception::class)
  fun exception(e: Exception): ServerResponse<String> {
    log.error("未知异常，原因：", e)
    return ServerResponse.error(e.toString())
  }
}