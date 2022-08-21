package cn.xdwanj.onlinestore.service.impl

import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.annotation.Slf4j.Companion.logger
import cn.xdwanj.onlinestore.common.*
import cn.xdwanj.onlinestore.entity.*
import cn.xdwanj.onlinestore.exception.BusinessException
import cn.xdwanj.onlinestore.exception.LogLevelEnum
import cn.xdwanj.onlinestore.mapper.OrderMapper
import cn.xdwanj.onlinestore.service.*
import cn.xdwanj.onlinestore.util.formatString
import cn.xdwanj.onlinestore.vo.OrderItemVo
import cn.xdwanj.onlinestore.vo.OrderVo
import cn.xdwanj.onlinestore.vo.ShippingVo
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal
import kotlin.random.Random

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XDwanj
 * @since 2022-07-16
 */
@Slf4j
@Service
class OrderServiceImpl(
  private val objectMapper: ObjectMapper,
  private val productService: ProductService,
  private val shippingService: ShippingService
) : ServiceImpl<OrderMapper, Order>(), OrderService {

  override fun assembleOrderVo(order: Order, orderItemList: List<OrderItem>): OrderVo {

    val orderVo = OrderVo().apply {
      orderNo = order.orderNo
      payment = order.payment
      paymentType = order.paymentType
      paymentTypeDesc = PaymentTypeEnum.codeOf(order.paymentType!!).desc
      postage = order.postage
      status = order.status
      statusDesc = OrderStatusEnum.codeOf(order.status!!).desc
      shippingId = order.shippingId

      shippingService.getById(shippingId)?.let {
        this.receiverName = it.receiverName
        this.shippingVo = assembleShippingVo(it)
      }

      paymentTime = order.paymentTime.formatString()
      sendTime = order.sendTime.formatString()
      endTime = order.endTime.formatString()
      createTime = order.createTime.formatString()
      closeTime = order.closeTime.formatString()
      imageHost = FTP_HOST

      val orderItemVoList = mutableListOf<OrderItemVo>()
      for (orderItem in orderItemList) {
        orderItemVoList += assembleOrderItemVo(orderItem)
      }

      this.orderItemVoList = orderItemVoList
    }

    return orderVo
  }

  override fun assembleOrderItemVo(orderItem: OrderItem): OrderItemVo {
    return OrderItemVo().apply {
      orderNo = orderItem.orderNo
      productId = orderItem.productId
      productName = orderItem.productName
      productImage = orderItem.productImage
      currentUnitPrice = orderItem.currentUnitPrice
      quantity = orderItem.quantity
      totalPrice = orderItem.totalPrice
      createTime = orderItem.createTime.formatString()
    }
  }

  override fun assembleShippingVo(shipping: Shipping): ShippingVo {
    return ShippingVo().apply {
      receiverName = shipping.receiverName
      receiverAddress = shipping.receiverAddress
      receiverProvince = shipping.receiverProvince
      receiverCity = shipping.receiverCity
      receiverDistrict = shipping.receiverDistrict
      receiverMobile = shipping.receiverMobile
      receiverZip = shipping.receiverZip
      receiverPhone = shipping.receiverPhone
    }
  }

  override fun reduceProductStock(orderItemList: List<OrderItem>) {
    for (orderItem in orderItemList) {
      val product = productService.ktQuery()
        .eq(Product::id, orderItem.productId)
        .select(Product::stock)
        .one()

      product.stock = if (product.stock!! >= orderItem.quantity!!) {
        product.stock!! - orderItem.quantity!!
      } else {
        throw BusinessException("库存不足无法生成订单", logLevel = LogLevelEnum.INFO)
      }

      productService.ktUpdate()
        .eq(Product::id, orderItem.productId)
        .set(Product::stock, product.stock)
        .update()
    }
  }

  override fun assembleOrder(userId: Int, shippingId: Int, payment: BigDecimal): Order {
    return Order().apply {
      orderNo = generateOrderNo()
      status = OrderStatusEnum.NO_PAY.code
      postage = 0 // TODO：暂时不考虑运费
      paymentType = PaymentTypeEnum.ONLINE_PAY.code
      this.payment = payment
      this.shippingId = shippingId
    }
  }

  // TODO: 待优化
  override fun generateOrderNo(): Long {
    val currentTime = System.currentTimeMillis()
    return currentTime + currentTime % Random.nextInt(100)
  }

  override fun getTotalPrice(orderItemList: List<OrderItem>): BigDecimal {
    var payment = BigDecimal.ZERO
    for (orderItem in orderItemList) {
      payment += orderItem.totalPrice!!
    }

    return payment
  }

  override fun listOrderItemByCartList(userId: Int, cartList: List<Cart>): List<OrderItem> {
    val orderItemList = mutableListOf<OrderItem>()

    if (cartList.isEmpty()) {
      throw BusinessException("购物车为空")
    }

    for (cartItem in cartList) {
      val product = productService.getById(cartItem.productId)
      if (product.status != ProductStatusEnum.ON_SALE.code) {
        logger.error("购物车中存在已下架或删除的商品")
        return emptyList()
      }

      val orderItem = OrderItem().apply {
        this.userId = userId
        productId = product.id
        productName = product.name
        productImage = product.mainImage
        currentUnitPrice = product.price
        quantity = cartItem.quantity
        totalPrice = product.price!! * BigDecimal.valueOf(quantity!!.toLong())
      }

      orderItemList += orderItem
    }

    return orderItemList
  }
}
