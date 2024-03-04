package cn.xdwanj.onlinestore

import cn.dev33.satoken.SaManager
import cn.hutool.log.LogFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext

@SpringBootApplication
class OnlineStoreApplication

val application by lazy { _application }
private lateinit var _application: ApplicationContext

// swagger doc is http://localhost:8080/api/v1/swagger-ui/index.html
fun main(args: Array<String>) {
  _application = runApplication<OnlineStoreApplication>(*args)
  val logger = LogFactory.get(OnlineStoreApplication::class.java)
  logger.info("SaTokenConfig => {}", SaManager.config)
}