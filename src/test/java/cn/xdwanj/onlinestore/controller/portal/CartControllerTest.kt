package cn.xdwanj.onlinestore.controller.portal

import cn.xdwanj.onlinestore.common.AUTHORIZATION_TOKEN
import cn.xdwanj.onlinestore.response.CommonResponse
import cn.xdwanj.onlinestore.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.Before
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.Rollback
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
internal class CartControllerTest {

  @Autowired
  private lateinit var userService: UserService

  @Autowired
  private lateinit var mockMvc: MockMvc

  @Autowired
  private lateinit var objectMapper: ObjectMapper

  private lateinit var authorizationToken: String

  @BeforeEach
  fun setUp() {
    mockMvc.perform {
      MockMvcRequestBuilders.post("/user/login")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("username", "xdwanj")
        .param("password", "123456")
        .buildRequest(it)
    }.andDo {
      val response = objectMapper.readValue<CommonResponse<String>>(it.response.contentAsString)
      authorizationToken = response.data!!
    }
  }

  @Test
  @Transactional
  @Rollback
  fun delete() {
    mockMvc.perform {
      MockMvcRequestBuilders.delete("/cart")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .header(AUTHORIZATION_TOKEN, authorizationToken)
        .param("productIds", "31,,32,33,,")
        .buildRequest(it)
    }.andExpect(MockMvcResultMatchers.status().isOk)
      .andDo {
        val resp = it.response.contentAsString
        println("resp => $resp")
      }
  }
}