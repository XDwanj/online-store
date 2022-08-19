package cn.xdwanj.onlinestore.config

import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.annotation.Slf4j.Companion.logger
import com.alipay.easysdk.factory.Factory
import com.alipay.easysdk.kernel.Config
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Slf4j
@Configuration
class AlipayConfig(
  private val properties: AlipayConfigProperties
) {

  init {
    val config = Config().apply {
      protocol = properties.protocol
      gatewayHost = properties.gatewayHost
      signType = properties.signType
      appId = properties.appId
      merchantPrivateKey = properties.merchantPrivateKey
      alipayPublicKey = properties.alipayPublicKey
      notifyUrl = properties.notifyUrl
    }

    logger.info("alipay config is: {}", properties)

    Factory.setOptions(config)
  }
}

@Slf4j
@Component
@ConfigurationProperties(prefix = "alipay.config")
data class AlipayConfigProperties(
  var protocol: String? = null,
  var gatewayHost: String? = null,
  var signType: String? = null,
  var appId: String? = null,
  var merchantPrivateKey: String? = null,
  var alipayPublicKey: String? = null,
  var notifyUrl: String? = null
)