package cn.xdwanj.onlinestore.controller.portal

import cn.xdwanj.onlinestore.service.impl.TestServiceImpl
import cn.xdwanj.onlinestore.util.Slf4j
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
@RequestMapping("/test")
class TestController(
  private val testServiceImpl: TestServiceImpl
) {

  @ApiOperation("测试npe异常")
  @GetMapping("/npe")
  fun npe(): Int {
    return testServiceImpl.npe()
  }
}