package cn.xdwanj.onlinestore.advice

import cn.xdwanj.onlinestore.common.ServerResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.logging.log4j.message.ReusableMessage
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

/**
 * 统一包装实体切面
 *
 */
//@ControllerAdvice
//class ControllerResponseAdvice(
//  private val objectMapper: ObjectMapper
//) : ResponseBodyAdvice<Any> {
//  override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean {
//    return returnType
//      .parameterType
//      .isAssignableFrom(ServerResponse::class.java)
//      .not()
//  }
//
//  override fun beforeBodyWrite(body: Any?, returnType: MethodParameter, selectedContentType: MediaType, selectedConverterType: Class<out HttpMessageConverter<*>>, request: ServerHttpRequest, response: ServerHttpResponse): Any? {
//    if (returnType.genericParameterType == String::class.java) {
//      return objectMapper.writeValueAsString(ServerResponse.success(data = body))
//    }
//
//    return ServerResponse.success(data = body)
//  }
//}