package cn.xdwanj.onlinestore.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.servlet.http.HttpServletResponse

fun HttpServletResponse.returnJson(json: String) {
  characterEncoding = "UTF-8"
  contentType = "application/json"

  writer.use {
    it.print(json)
  }
}

fun LocalDateTime.formatString(): String {
  return this.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
}
