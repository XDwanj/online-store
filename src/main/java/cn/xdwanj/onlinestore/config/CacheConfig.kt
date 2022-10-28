package cn.xdwanj.onlinestore.config

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
class CacheConfig {
  @Bean
  fun caffeine(): Caffeine<Any, Any> = Caffeine.newBuilder()
    .expireAfterWrite(12, TimeUnit.HOURS)
    .initialCapacity(1000)
    .maximumSize(10000)

  @Bean
  fun cacheManager(
    caffeine: Caffeine<Any, Any>
  ) = CaffeineCacheManager().apply {
    setCaffeine(caffeine)
  }
}