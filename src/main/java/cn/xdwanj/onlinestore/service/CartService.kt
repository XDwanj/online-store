package cn.xdwanj.onlinestore.service;

import cn.xdwanj.onlinestore.common.ServerResponse
import cn.xdwanj.onlinestore.entity.Cart;
import cn.xdwanj.onlinestore.vo.CartVo
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XDwanj
 * @since 2022-07-16
 */
interface CartService : IService<Cart> {
  /**
   * 添加购物车
   *
   * @param userId
   * @param productId
   * @param count
   * @return
   */
  fun add(userId: Int, productId: Int, count: Int): ServerResponse<CartVo>

  /**
   * 更新购物车
   *
   * @param userId
   * @param productId
   * @param count
   * @return
   */
  fun updateCart(userId: Int, productId: Int, count: Int): ServerResponse<CartVo>
}
