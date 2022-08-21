package cn.xdwanj.onlinestore.advice

//@Slf4j
//@ControllerAdvice
//class ControllerResponseAdvice(
//  private val objectMapper: ObjectMapper
//) : ResponseBodyAdvice<Any> {
//  override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean {
//    return true
//  }
//
//  override fun beforeBodyWrite(body: Any?, returnType: MethodParameter, selectedContentType: MediaType, selectedConverterType: Class<out HttpMessageConverter<*>>, request: ServerHttpRequest, response: ServerHttpResponse): Any? {
//    if (body is ServerResponse<*>) {
//      return body
//    }
//
//    return if (body is String) {
//      objectMapper.writeValueAsString(ServerResponse.success(data = body))
//    } else {
//      ServerResponse.success(data = body)
//    }
//  }
//}