package cn.xdwanj.onlinestore.common

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude

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
    // fun <T> createBySuccess() = ServerResponse<T>(ResponseCode.SUCCESS.code)
    fun <T> createBySuccess(msg: String? = null, data: T? = null, code: Int = ResponseCode.SUCCESS.code) =
      ServerResponse(code, msg, data)

    // fun <T> createBySuccessData(data: T): ServerResponse<T> = ServerResponse(ResponseCode.SUCCESS.code, data = data)
    // fun <T> createBySuccessMsg(msg: String) = ServerResponse<T>(ResponseCode.SUCCESS.code, msg = msg)
    // fun <T> createByError() = ServerResponse<T>(ResponseCode.ERROR.code, msg = ResponseCode.ERROR.desc)
    // fun <T> createByError(msg: String) = ServerResponse<T>(ResponseCode.ERROR.code, msg)
    fun <T> createByError(msg: String? = null, code: Int =
      ResponseCode.ERROR.code) = ServerResponse<T>(code, msg)

    // fun <T> buildSuccess(msg: String? = null, data: T? = null, code: Int = ResponseCode.SUCCESS.code) =
    //   ServerResponse<T>(status = code, msg = msg, data = data)
    //
    // fun <T> buildError(msg: String? = null, code: Int = ResponseCode.ERROR.code) =
    //   ServerResponse<T>(status = code, msg = msg)
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
