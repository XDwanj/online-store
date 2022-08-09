package cn.xdwanj.onlinestore.exception

import cn.xdwanj.onlinestore.common.ResponseCode

/**
 * 业务异常类
 *
 * @property errorMsg 错误信息
 * @property errorCode 错误code
 */
data class BusinessException(
  val errorMsg: String,
  val errorCode: Int = ResponseCode.ERROR.code,
  val logLevelEnum: LogLevelEnum = LogLevelEnum.INFO
) : RuntimeException()

/**
 * 日志等级枚举
 *
 */
enum class LogLevelEnum {
  INFO, TRANCE, DEBUG, WARN, ERROR
}

