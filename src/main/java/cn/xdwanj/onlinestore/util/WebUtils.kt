package cn.xdwanj.onlinestore.util

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

/**
 * yyyy-MM-dd HH:mm:ss 格式转换 LocalDateTime，如果 null，则返回空字符串
 *
 * @return
 */
fun LocalDateTime?.formatString(): String {
  if (this == null) {
    return ""
  }
  return this.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
}
