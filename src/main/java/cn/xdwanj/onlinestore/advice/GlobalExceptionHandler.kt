package cn.xdwanj.onlinestore.advice

import cn.dev33.satoken.exception.NotLoginException
import cn.dev33.satoken.exception.NotPermissionException
import cn.dev33.satoken.exception.NotRoleException
import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.annotation.Slf4j.Companion.logger
import cn.xdwanj.onlinestore.exception.BusinessException
import cn.xdwanj.onlinestore.exception.LogLevelEnum
import cn.xdwanj.onlinestore.response.CommonResponse
import cn.xdwanj.onlinestore.response.ResponseCode
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.validation.ConstraintViolationException

@Slf4j
@RestControllerAdvice
class GlobalExceptionHandler {

  @ExceptionHandler(NullPointerException::class)
  fun nullPointer(e: NullPointerException): CommonResponse<String> {
    logger.error("出现 NPE 异常：", e)
    return CommonResponse.error(e.toString())
  }

  /**
   * 实体类数据校验
   *
   * @param e
   * @return
   */
  @ExceptionHandler(MethodArgumentNotValidException::class)
  fun methodArgumentNotValid(e: MethodArgumentNotValidException): CommonResponse<String> {
    logger.info("传入参数校验错误：", e)
    val bindingResult = e.bindingResult

    val errorMsg = bindingResult.fieldErrors.map {
      "错误字段 -> ${it.field} -> 错误值 -> ${it.rejectedValue} -> 原因 -> ${it.defaultMessage}"
    }.joinToString(separator = "\n") { it }

    return CommonResponse.error(errorMsg)
  }

  /**
   * 简单形参校验
   *
   * @param e
   * @return
   */
  @ExceptionHandler(ConstraintViolationException::class)
  fun constrainViolation(e: ConstraintViolationException): CommonResponse<String> {
    logger.error("传入参数非法：", e)
    val errorMsg = e.constraintViolations.map {
      "${it.propertyPath}: ${it.message}"
    }.joinToString(separator = "\n") { it }
    return CommonResponse.error(errorMsg)
  }

  @ExceptionHandler(BusinessException::class)
  fun business(e: BusinessException): CommonResponse<String> {
    when (e.logLevel) { // 优先级由低到高
      LogLevelEnum.NOT_LOG -> {} // 不打日志
      LogLevelEnum.TRANCE -> logger.trace("出现业务异常，原因是：{}", e.errorMsg)
      LogLevelEnum.DEBUG -> logger.debug("出现业务异常，原因是：{}", e.errorMsg)
      LogLevelEnum.INFO -> logger.info("出现业务异常，原因是：{}", e.errorMsg)
      LogLevelEnum.WARN -> logger.warn("出现业务异常，原因是：{}", e.errorMsg)
      LogLevelEnum.ERROR -> logger.error("出现业务异常，原因是：{}", e.errorMsg)
    }
    return CommonResponse.error(e.errorMsg, e.errorCode)
  }

  @ExceptionHandler(NotLoginException::class)
  fun notLogin(e: NotLoginException): CommonResponse<String> {
    logger.warn("用户未登录", e)
    return CommonResponse.error(e.message, ResponseCode.NEED_LOGIN.code)
  }

  @ExceptionHandler(NotPermissionException::class)
  fun notPermission(e: NotPermissionException): CommonResponse<String> {
    logger.warn("缺少权限", e)
    return CommonResponse.error("缺少权限: ${e.permission}")
  }

  @ExceptionHandler(NotRoleException::class)
  fun notRole(e :NotRoleException): CommonResponse<String> {
    logger.warn("缺少角色", e)
    return CommonResponse.error("缺少角色 ${e.role}")
  }

  @ExceptionHandler(Exception::class)
  fun all(e: Exception): CommonResponse<String> {
    logger.error("未知异常，原因：", e)
    return CommonResponse.error(e.toString())
  }
}