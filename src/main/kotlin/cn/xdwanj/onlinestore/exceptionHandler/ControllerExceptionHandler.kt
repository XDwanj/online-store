package cn.xdwanj.onlinestore.exceptionHandler

import cn.xdwanj.onlinestore.util.Slf4j
import cn.xdwanj.onlinestore.util.Slf4j.Companion.log
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@Slf4j
@RestControllerAdvice(basePackages = ["cn.xdwanj.onlinestore.controller.backend"])
class ControllerExceptionHandler {

  @ExceptionHandler(NullPointerException::class)
  fun npe(e: NullPointerException) {
    log.error("捕获到 npe 异常:", e)
  }
}