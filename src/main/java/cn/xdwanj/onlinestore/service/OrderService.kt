package cn.xdwanj.onlinestore.service;

import cn.xdwanj.onlinestore.entity.Order
import cn.xdwanj.onlinestore.vo.OrderVo
import com.baomidou.mybatisplus.extension.service.IService

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XDwanj
 * @since 2022-07-16
 */
interface OrderService : IService<Order> {
  /**
   * 订单付款
   *
   * @param orderNo
   * @param userId
   */
  fun pay(orderNo: Long, userId: Int): String
  fun createOrder(userId: Int, shippingId: Int): OrderVo?
}
