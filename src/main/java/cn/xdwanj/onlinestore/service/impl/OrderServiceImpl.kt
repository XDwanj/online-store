package cn.xdwanj.onlinestore.service.impl

import cn.hutool.core.lang.Snowflake
import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.annotation.Slf4j.Companion.logger
import cn.xdwanj.onlinestore.constant.FTP_HOST
import cn.xdwanj.onlinestore.constant.OrderStatusEnum
import cn.xdwanj.onlinestore.constant.PaymentTypeEnum
import cn.xdwanj.onlinestore.constant.ProductStatusEnum
import cn.xdwanj.onlinestore.entity.*
import cn.xdwanj.onlinestore.exception.BusinessException
import cn.xdwanj.onlinestore.exception.LogLevelEnum
import cn.xdwanj.onlinestore.mapper.OrderMapper
import cn.xdwanj.onlinestore.service.OrderService
import cn.xdwanj.onlinestore.service.ProductService
import cn.xdwanj.onlinestore.service.ShippingService
import cn.xdwanj.onlinestore.util.formatString
import cn.xdwanj.onlinestore.vo.OrderItemVo
import cn.xdwanj.onlinestore.vo.OrderVo
import cn.xdwanj.onlinestore.vo.ShippingVo
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Service
import java.math.BigDecimal

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
  private val productService: ProductService,
  private val shippingService: ShippingService,
  private val snowflake: Snowflake
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
      BeanUtils.copyProperties(orderItem, this)
      this.createTime = orderItem.createTime.formatString()
    }
  }

  override fun assembleShippingVo(shipping: Shipping): ShippingVo {
    return ShippingVo().apply {
      BeanUtils.copyProperties(shipping, this)
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
    return Order().also {
      it.userId = userId
      it.orderNo = generateOrderNo()
      it.status = OrderStatusEnum.NO_PAY.code
      it.postage = 0 // TODO：暂时不考虑运费
      it.paymentType = PaymentTypeEnum.ONLINE_PAY.code
      it.payment = payment
      it.shippingId = shippingId
    }
  }

  override fun generateOrderNo() = snowflake.nextId()

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

      val orderItem = OrderItem().also {
        it.userId = userId
        it.productId = product.id
        it.productName = product.name
        it.productImage = product.mainImage
        it.currentUnitPrice = product.price
        it.quantity = cartItem.quantity
        it.totalPrice = product.price!! * BigDecimal.valueOf(it.quantity!!.toLong())
      }

      orderItemList += orderItem
    }

    return orderItemList
  }
}
