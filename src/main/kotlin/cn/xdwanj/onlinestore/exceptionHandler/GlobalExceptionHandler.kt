package cn.xdwanj.onlinestore.exceptionHandler

import cn.xdwanj.onlinestore.common.BizException
import cn.xdwanj.onlinestore.common.ServerResponse
import cn.xdwanj.onlinestore.util.Slf4j
import cn.xdwanj.onlinestore.util.Slf4j.Companion.log
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.validation.ConstraintViolationException

@Slf4j
@RestControllerAdvice
class GlobalExceptionHandler {

  @ExceptionHandler(NullPointerException::class)
  fun nullPointer(e: NullPointerException): ServerResponse<String> {
    log.error("出现 NPE 异常：", e)
    return ServerResponse.error("数据格式错误")
  }

  /**
   * 实体类数据校验
   *
   * @param e
   * @return
   */
  @ExceptionHandler(MethodArgumentNotValidException::class)
  fun methodArgumentNotValid(e: MethodArgumentNotValidException): ServerResponse<String> {
    log.error("传入参数校验错误：", e)
    return ServerResponse.error("传入参数格式非法")
  }

  /**
   * 简单形参校验
   *
   * @param e
   * @return
   */
  @ExceptionHandler(ConstraintViolationException::class)
  fun constrainViolation(e: ConstraintViolationException): ServerResponse<String> {
    log.error("传入参数非法：", e)
    return ServerResponse.error(e.toString())
  }

  @ExceptionHandler(BizException::class)
  fun biz(e: BizException): ServerResponse<String> {
    log.error("出现业务异常，原因是：{}", e.errorMsg)
    return ServerResponse.errorByException(e)
  }

  @ExceptionHandler(Exception::class)
  fun all(e: Exception): ServerResponse<String> {
    log.error("未知异常，原因：", e)
    return ServerResponse.error(e.toString())
  }
}