package cn.xdwanj.onlinestore.controller.portal

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@AutoConfigureMockMvc
internal class CartControllerTest {
  //
  // @Autowired
  // private lateinit var userService: UserService
  //
  // @Autowired
  // private lateinit var mockMvc: MockMvc
  //
  // @Autowired
  // private lateinit var objectMapper: ObjectMapper
  //
  // private lateinit var authorizationToken: String
  //
  // @BeforeEach
  // fun setUp() {
  //   mockMvc.perform {
  //     MockMvcRequestBuilders.post("/user/login")
  //       .contentType(MediaType.APPLICATION_FORM_URLENCODED)
  //       .param("username", "xdwanj")
  //       .param("password", "123456")
  //       .buildRequest(it)
  //   }.andDo {
  //     val response = objectMapper.readValue<CommonResponse<String>>(it.response.contentAsString)
  //     authorizationToken = response.data!!
  //   }
  // }
  //
  // @Test
  // @Transactional
  // @Rollback
  // fun delete() {
  //   mockMvc.perform {
  //     MockMvcRequestBuilders.delete("/cart")
  //       .contentType(MediaType.APPLICATION_FORM_URLENCODED)
  //       .header(AUTHORIZATION_TOKEN, authorizationToken)
  //       .param("productIds", "31,,32,33,,")
  //       .buildRequest(it)
  //   }.andExpect(MockMvcResultMatchers.status().isOk)
  //     .andDo {
  //       val resp = it.response.contentAsString
  //       println("resp => $resp")
  //     }
  // }
}