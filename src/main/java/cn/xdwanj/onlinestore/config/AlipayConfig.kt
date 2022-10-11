package cn.xdwanj.onlinestore.config

import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.annotation.Slf4j.Companion.logger
import com.alipay.easysdk.factory.Factory
import com.alipay.easysdk.kernel.Config
import org.springframework.beans.BeanUtils
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Slf4j
@Configuration
class AlipayConfig(
  private val properties: AlipayConfigProperties
) {

  init {
    val config = Config().apply { BeanUtils.copyProperties(properties, this) }

    logger.info("支付宝属性构建中: {}", properties.apply {
      this.merchantPrivateKey = "略"
    })

    Factory.setOptions(config)
  }
}

@Slf4j
@Component
@ConfigurationProperties(prefix = "alipay.config")
data class AlipayConfigProperties(
  var protocol: String = "",
  var gatewayHost: String = "",
  var signType: String = "",
  var appId: String = "",
  var merchantPrivateKey: String = "",
  var alipayPublicKey: String = "",
  var notifyUrl: String = ""
)