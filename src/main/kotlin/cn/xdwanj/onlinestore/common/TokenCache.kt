package cn.xdwanj.onlinestore.common

import cn.xdwanj.onlinestore.util.Slf4j
import cn.xdwanj.onlinestore.util.Slf4j.Companion.log
import com.github.benmanes.caffeine.cache.CacheLoader
import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import org.apache.el.parser.Token
import org.springframework.stereotype.Component

const val TOKEN_PREFIX = "_token"

@Slf4j
@Component
class TokenCache(
  caffeine: Caffeine<Any, Any>
) {

  /**
   * 这里有一个问题，key不可为空，value有没有可能是空的
   * 需不需要区分 空value 和 取不到值 两种情况
   * 答：我认为不需要，因为，这个 cache api 本身就不存在可空的行为
   */
  private val cache = caffeine.build<String, String> { null }

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


