package cn.xdwanj.onlinestore.util

import cn.xdwanj.onlinestore.exception.BusinessException
import jakarta.servlet.http.HttpServletResponse
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun HttpServletResponse.returnJson(json: String) {
  characterEncoding = "UTF-8"
  contentType = "application/json"

  writer.use {
    it.print(json)
  }
}

fun LocalDateTime?.formatString(): String {
  if (this == null) {
    throw BusinessException("时间格式化不可为空")
  }
  return this.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
}
