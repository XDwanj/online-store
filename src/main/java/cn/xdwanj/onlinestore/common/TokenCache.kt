package cn.xdwanj.onlinestore.common

import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.annotation.Slf4j.Companion.logger
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.benmanes.caffeine.cache.Caffeine
import org.apache.el.parser.Token
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.TimeUnit

const val FORGET_TOKEN_PREFIX = "forgetToken_"
const val USER_TOKEN_PREFIX = "userToken_"

fun getToken(prefix: String): String {
  return prefix + UUID.randomUUID()
    .toString()
    .uppercase()
}

@Slf4j
@Component
class TokenCache(
  val objectMapper: ObjectMapper
) {

  /**
   * 这里有一个问题，key不可为空，value有没有可能是空的
   * 需不需要区分 空value 和 取不到值 两种情况
   * 答：我认为不需要，因为，这个 cache api 本身就不存在可空的行为
   */
  val cache = Caffeine.newBuilder()
    .initialCapacity(1000)
    .maximumSize(Long.MAX_VALUE)
    .expireAfterAccess(12, TimeUnit.HOURS)
    .build<String, String> { null }

  operator fun plusAssign(pair: Pair<String, String>) {
    cache.put(pair.first, pair.second)
  }

  final inline operator fun <reified T> get(key: String): T? {
    return try {
      val json = cache.getIfPresent(key)
        ?: return null
      objectMapper.readValue(json)
    } catch (e: Exception) {
      logger.error("get key=$key error")
      e.printStackTrace()
      null
    }
  }

  operator fun <T> set(k: String, v: T) {
    val json = objectMapper.writeValueAsString(v)
    cache.put(k, json)
  }

  fun remove(k: String) {
      cache.invalidate(k)
  }
}

