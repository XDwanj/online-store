package cn.xdwanj.onlinestore.controller.portal

import cn.xdwanj.onlinestore.annotation.Slf4j
import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner::class)
@WebMvcTest(UserController::class)
class UserControllerTest {
  //
  // @Autowired
  // private lateinit var mockMvc: MockMvc
  //
  // @Autowired
  // private lateinit var objectMapper: ObjectMapper
  //
  // @Autowired
  // private lateinit var userService: UserService
  //
  // companion object {
  //   private lateinit var authorizationToken: String
  // }
  //
  // @Before
  // fun setUp() {
  //   logger.info("setUp")
  //
  //   val un = "__test__"
  //   val pw = "__123456__"
  //   val user = User().apply {
  //     username = un
  //     password = pw.encodeByMD5()
  //     email = "____@qq.com"
  //     phone = "123____1234"
  //     question = "question"
  //     answer = "answer"
  //     role = RoleEnum.CUSTOMER.code
  //   }
  //   val loginIsSuccess = userService.save(user)
  //
  //   assert(loginIsSuccess)
  //
  //   val requestBuilder = MockMvcRequestBuilders.post("/user/login")
  //     .assembleRequest()
  //     .param("username", un)
  //     .param("password", pw)
  //
  //   val data = mockMvc.perform(requestBuilder)
  //     .checkIsSuccessToCommonResponse<String>(objectMapper).data!!
  //   logger.info(data)
  //   authorizationToken = data
  // }
  //
  // private fun MockHttpServletRequestBuilder.assembleRequest(): MockHttpServletRequestBuilder {
  //   return this.apply {
  //     contentType(MediaType.APPLICATION_FORM_URLENCODED)
  //     accept(MediaType.APPLICATION_JSON)
  //   }
  // }
  //
  // private fun <T> ResultActions.checkIsSuccessToCommonResponse(om: ObjectMapper): CommonResponse<T> {
  //   var response: CommonResponse<T> = CommonResponse.error()
  //   andExpect(MockMvcResultMatchers.status().isOk)
  //   andDo {
  //     response = om.readValue(it.response.contentAsString)
  //   }
  //   assert(response.isSuccess())
  //   return response
  // }
  //
  // @Test
  // fun checkValidTest() {
  //   mockMvc.perform {
  //     MockMvcRequestBuilders.post("/user/checkValid")
  //       .assembleRequest()
  //       .header(AUTHORIZATION_TOKEN, authorizationToken)
  //       .param("type", "email")
  //       .param("value", "____@qq.com")
  //       .buildRequest(it)
  //   }.checkIsSuccessToCommonResponse<String>(objectMapper)
  // }
  //
  // @After
  // fun logout() {
  //   userService.ktUpdate()
  //     .eq(User::username, "__test__")
  //     .remove()
  // }
}