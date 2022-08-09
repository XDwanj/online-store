package cn.xdwanj.onlinestore.common

import cn.xdwanj.onlinestore.exception.BusinessException
import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.annotation.Slf4j.Companion.logger
import cn.xdwanj.onlinestore.exception.LogLevelEnum
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.validation.ConstraintViolationException

@Slf4j
@RestControllerAdvice
class GlobalExceptionHandler {

  @ExceptionHandler(NullPointerException::class)
  fun nullPointer(e: NullPointerException): ServerResponse<String> {
    logger.error("出现 NPE 异常：", e)
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
    logger.error("传入参数校验错误：", e)
    val bindingResult = e.bindingResult

    val errorMsg = bindingResult.fieldErrors.map {
      "错误字段 -> ${it.field} -> 错误值 -> ${it.rejectedValue} -> 原因 -> ${it.defaultMessage}"
    }.joinToString(separator = "\n") { it }

    return ServerResponse.error(errorMsg)
  }

  /**
   * 简单形参校验
   *
   * @param e
   * @return
   */
  @ExceptionHandler(ConstraintViolationException::class)
  fun constrainViolation(e: ConstraintViolationException): ServerResponse<String> {
    logger.error("传入参数非法：", e)
    val errorMsg = e.constraintViolations.map {
      "${it.propertyPath}: ${it.message}"
    }.joinToString(separator = "\n") { it }
    return ServerResponse.error(errorMsg)
  }

  @ExceptionHandler(BusinessException::class)
  fun business(e: BusinessException): ServerResponse<String> {
    when (e.logLevelEnum) {
      LogLevelEnum.INFO -> logger.info("出现业务异常，原因是：{}", e.errorMsg)
      LogLevelEnum.TRANCE -> logger.trace("出现业务异常，原因是：{}", e.errorMsg)
      LogLevelEnum.DEBUG -> logger.debug("出现业务异常，原因是：{}", e.errorMsg)
      LogLevelEnum.WARN -> logger.warn("出现业务异常，原因是：{}", e.errorMsg)
      LogLevelEnum.ERROR -> logger.error("出现业务异常，原因是：{}", e.errorMsg)
    }
    return ServerResponse.error(e.errorMsg, e.errorCode)
  }

  @ExceptionHandler(Exception::class)
  fun all(e: Exception): ServerResponse<String> {
    logger.error("未知异常，原因：", e)
    return ServerResponse.error(e.toString())
  }
}