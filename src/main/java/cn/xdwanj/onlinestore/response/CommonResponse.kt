package cn.xdwanj.onlinestore.response

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "通用返回对象")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
class CommonResponse<T> private constructor(
  @Schema(description = "状态码")
  val code: Int? = null,
  @Schema(description = "状态消息")
  val msg: String? = null,
  @Schema(description = "数据")
  val data: T? = null
) {
  @JsonIgnore
  fun isSuccess() = run { this.code == ResponseCode.SUCCESS.code }

  override fun toString(): String {
    return "CommonResponse(code=$code, msg=$msg, data=$data)"
  }

  companion object { // 工厂方法
    fun <T> success(
      msg: String? = ResponseCode.SUCCESS.msg,
      data: T? = null,
      code: Int = ResponseCode.SUCCESS.code
    ) = CommonResponse(code, msg, data)

    fun <T> error(
      msg: String? = null,
      code: Int = ResponseCode.ERROR.code,
      data: T? = null
    ) = CommonResponse(code, msg, data)

  }
}

enum class ResponseCode(
  val code: Int,
  val msg: String
) {
  SUCCESS(0, "SUCCESS"),
  ERROR(1, "ERROR"),
  NEED_LOGIN(10, "NEED_LOGIN"),
  ILLEGAL_ARGUMENT(2, "ILLEGAL_ARGUMENT")
}
