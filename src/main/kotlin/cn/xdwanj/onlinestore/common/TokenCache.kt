package cn.xdwanj.onlinestore.common

import cn.xdwanj.onlinestore.util.Slf4j
import cn.xdwanj.onlinestore.util.Slf4j.Companion.log
import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

const val TOKEN_PREFIX = "_token"

@Slf4j
@Component
class TokenCache {

  /**
   * 这里有一个问题，key不可为空，value有没有可能是空的
   * 需不需要区分 空value 和 取不到值 两种情况
   * 答：我认为不需要，因为，这个 cache api 本身就不存在可空的行为
   */
  private val cache = Caffeine.newBuilder()
    .initialCapacity(1000)
    .maximumSize(10000)
    .expireAfterAccess(12, TimeUnit.HOURS)
    .build<String, String> { null }

  operator fun plusAssign(pair: Pair<String, String>) {
    cache.put(pair.first, pair.second)
  }

  operator fun get(key: String): String? {
    return try {
      cache.get(key)
    } catch (e: Exception) {
      log.error("get key=$key error")
      e.printStackTrace()
      null
    }
  }

  operator fun set(s: String, value: String) {
    cache.put(s, value)
  }
}


