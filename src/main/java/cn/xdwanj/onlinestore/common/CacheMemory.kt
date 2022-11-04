package cn.xdwanj.onlinestore.common

import cn.hutool.core.util.IdUtil
import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.annotation.Slf4j.Companion.logger
import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

const val FORGET_TOKEN_PREFIX = "forgetToken_"
const val USER_TOKEN_PREFIX = "userToken_"

fun getTokenByPrefix(prefix: String): String {
  return prefix + IdUtil.fastSimpleUUID()
    .toString()
    .uppercase()
}

@Slf4j
@Component
class CacheMemory {

  /**
   * 这里有一个问题，key不可为空，value有没有可能是空的
   * 需不需要区分 空value 和 取不到值 两种情况
   * 答：我认为不需要，因为，这个 cache api 本身就不存在可空的行为
   */
  val cache = Caffeine.newBuilder()
    .initialCapacity(1000)
    .maximumSize(Long.MAX_VALUE)
    .expireAfterAccess(12, TimeUnit.HOURS)
    .build<String, Any> { null }

  operator fun plusAssign(pair: Pair<String, Any>) {
    cache.put(pair.first, pair.second)
  }

  final inline operator fun <reified T> get(key: String): T? {
    return try {
      cache.getIfPresent(key) as T
    } catch (e: Exception) {
      logger.error("get key=$key error")
      e.printStackTrace()
      null
    }
  }

  operator fun set(k: String, v: Any) {
    cache.put(k, v)
  }

  fun remove(k: String) {
    cache.invalidate(k)
  }
}
