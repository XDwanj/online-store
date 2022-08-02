package cn.xdwanj.onlinestore

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@MapperScan("cn.xdwanj.onlinestore.mapper")
class OnlineStoreApplication

fun main(args: Array<String>) {
  runApplication<OnlineStoreApplication>(*args)
}

