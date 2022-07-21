package cn.xdwanj.onlinestore

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import springfox.documentation.oas.annotations.EnableOpenApi

@SpringBootApplication
@EnableOpenApi
@MapperScan("cn.xdwanj.onlinestore.mapper")
open class OnlineStoreApplication

fun main(args: Array<String>) {
  // MPGenerator().generator()
  runApplication<OnlineStoreApplication>(*args)
}

