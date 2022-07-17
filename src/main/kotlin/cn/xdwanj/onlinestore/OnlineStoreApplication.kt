package cn.xdwanj.onlinestore

import cn.xdwanj.onlinestore.util.MPGenerator
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class OnlineStoreApplication

fun main(args: Array<String>) {
  MPGenerator().generator()
  // runApplication<OnlineStoreApplication>(*args)
}

