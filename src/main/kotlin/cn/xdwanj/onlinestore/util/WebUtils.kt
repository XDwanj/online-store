package cn.xdwanj.onlinestore.util

import javax.servlet.http.HttpServletResponse

fun HttpServletResponse.returnJson(json: String) {
  characterEncoding = "UTF-8"
  contentType = "application/json"

  writer.use {
    it.print(json)
  }
}
