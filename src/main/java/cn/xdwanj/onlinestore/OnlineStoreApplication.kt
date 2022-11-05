package cn.xdwanj.onlinestore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OnlineStoreApplication

// dev api is http://localhost:15000/api/v1/swagger-ui/index.html
fun main(args: Array<String>) {
  runApplication<OnlineStoreApplication>(*args)
}