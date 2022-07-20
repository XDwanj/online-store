package cn.xdwanj.onlinestore.config

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
open class CacheConfig {
  @Bean
  open fun caffeine() = Caffeine.newBuilder()
    .expireAfterWrite(12, TimeUnit.HOURS)
    .initialCapacity(1000)
    .maximumSize(10000)

  @Bean
  open fun cacheManager(
    caffeine: Caffeine<Any, Any>
  ) = CaffeineCacheManager().apply {
    setCaffeine(caffeine)
  }
}