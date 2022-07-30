package cn.xdwanj.onlinestore.util

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.DigestUtils
import java.util.*

@Component
class Security(
  @Value("\${password.salt}")
  private val _salt: String
) {
  // @Value注解无法注入静态属性，我们可以使用初始化函数进行值传递
  init {
    salt = this._salt
  }
}

lateinit var salt: String

fun String.encodeByMD5(): String = DigestUtils
  .md5DigestAsHex("${this}${salt}".toByteArray())
  .toString()
  .uppercase(Locale.getDefault())


