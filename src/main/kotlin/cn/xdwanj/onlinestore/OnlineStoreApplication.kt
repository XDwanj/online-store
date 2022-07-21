package cn.xdwanj.onlinestore

import cn.xdwanj.onlinestore.util.MPGenerator
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import springfox.documentation.oas.annotations.EnableOpenApi

@SpringBootApplication
@EnableOpenApi
@MapperScan("cn.xdwanj.onlinestore.mapper")
open class OnlineStoreApplication

fun main(args: Array<String>) {
  // MPGenerator().generator()
  runApplication<OnlineStoreApplication>(*args)
}

