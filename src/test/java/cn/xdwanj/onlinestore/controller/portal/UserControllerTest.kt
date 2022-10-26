package cn.xdwanj.onlinestore.controller.portal

import cn.xdwanj.onlinestore.response.CommonResponse
import cn.xdwanj.onlinestore.response.ResponseCode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.Before
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

  @Autowired
  private lateinit var mockMvc: MockMvc

  @Autowired
  private lateinit var objectMapper: ObjectMapper

  @Before
  fun setUp() {
    println("----------start-------------")
    println("-----------end-------------")
  }

  @Test
  @Transactional
  @Rollback
  fun loginTest() {
    val url = "/user/login"

    val requestBuilder = MockMvcRequestBuilders.post(url)
      // 客户端发送的数据类型 必要
      .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
      // 客户端希望获取的数据类型 非必要
      .accept(MediaType.APPLICATION_JSON)
      // 设置请求头
      // .header(AUTHORIZATION_TOKEN, "")
      .param("username", "XDwanj")
      .param("password", "123456")

    mockMvc.perform(requestBuilder)
      // 执行完成后的断言，响应码并非 200，则测试不通过
      .andExpect(MockMvcResultMatchers.status().isOk)
      .andDo {
        val code = objectMapper.readValue<CommonResponse<String>>(it.response.contentAsString).code
        assert(code == ResponseCode.SUCCESS.code)
      }

  }

  // @Test
  // fun logout() {
  // }
}