package cn.xdwanj.onlinestore

import cn.xdwanj.onlinestore.util.MPGenerator
import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@MapperScan("cn.xdwanj.onlinestore.mapper")
open class OnlineStoreApplication

fun main(args: Array<String>) {
  // MPGenerator().generator()
  runApplication<OnlineStoreApplication>(*args)
}

