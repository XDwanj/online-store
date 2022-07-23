package cn.xdwanj.onlinestore.common

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.media.Schema

@Schema(hidden = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
class ServerResponse<T> private constructor(
  val status: Int? = null,
  val msg: String? = null,
  val data: T? = null
) {
  @JsonIgnore
  fun isSuccess() = run { this.status == ResponseCode.SUCCESS.code }
  override fun toString(): String {
    return "ServerResponse(status=$status, msg=$msg, data=$data)"
  }

  companion object { // 工厂方法
    fun <T> success(
      msg: String? = null,
      data: T? = null,
      code: Int = ResponseCode.SUCCESS.code
    ) = ServerResponse(code, msg, data)

    fun <T> error(
      msg: String? = null,
      code: Int = ResponseCode.ERROR.code
    ) = ServerResponse<T>(code, msg)

    fun <T> errorByException(
      e: BizException
    ) = ServerResponse<T>(e.errorCode, e.errorMsg)
  }
}

enum class ResponseCode(
  val code: Int,
  val desc: String
) {
  SUCCESS(0, "SUCCESS"),
  ERROR(1, "ERROR"),
  NEED_LOGIN(10, "NEED_LOGIN"),
  ILLEGAL_ARGUMENT(2, "ILLEGAL_ARGUMENT")
}
