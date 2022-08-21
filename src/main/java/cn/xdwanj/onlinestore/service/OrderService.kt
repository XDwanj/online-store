package cn.xdwanj.onlinestore.service;

import cn.xdwanj.onlinestore.entity.Cart
import cn.xdwanj.onlinestore.entity.Order
import cn.xdwanj.onlinestore.entity.OrderItem
import cn.xdwanj.onlinestore.entity.Shipping
import cn.xdwanj.onlinestore.vo.OrderItemVo
import cn.xdwanj.onlinestore.vo.OrderVo
import cn.xdwanj.onlinestore.vo.ShippingVo
import com.baomidou.mybatisplus.extension.service.IService
import java.math.BigDecimal

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XDwanj
 * @since 2022-07-16
 */
interface OrderService : IService<Order> {

  fun assembleOrderVo(order: Order, orderItemList: List<OrderItem>): OrderVo
  fun assembleOrderItemVo(orderItem: OrderItem): OrderItemVo
  fun assembleShippingVo(shipping: Shipping): ShippingVo
  fun assembleOrder(userId: Int, shippingId: Int, payment: BigDecimal): Order

  /**
   * 订单付款
   *
   * @param orderNo
   * @param userId
   */
  fun pay(orderNo: Long, userId: Int): String

  /**
   * 清理库存
   *
   * @param orderItemList
   */
  fun reduceProductStock(orderItemList: List<OrderItem>)

  /**
   * 生成OrderNo
   *
   * @return
   */
  fun generateOrderNo(): Long

  /**
   * 购物车转订单项
   *
   * @param userId
   * @param cartList
   * @return
   */
  fun getOrderItemByCart(userId: Int, cartList: List<Cart>): List<OrderItem>

  /**
   * 获取订单总价
   *
   * @param orderItemList
   * @return
   */
  fun getOrderTotalPrice(orderItemList: List<OrderItem>): BigDecimal
}
