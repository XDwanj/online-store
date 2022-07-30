package cn.xdwanj.onlinestore.service;

import cn.xdwanj.onlinestore.common.ServerResponse
import cn.xdwanj.onlinestore.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XDwanj
 * @since 2022-07-16
 */
interface ProductService : IService<Product> {
  /**
   * 添加产品
   *
   * @param product
   * @return
   */
  fun saveProduct(product: Product): ServerResponse<String>

  /**
   * 更新产品
   *
   * @param product
   * @return
   */
  fun updateProduct(product: Product): ServerResponse<String>

  /**
   * 更新商品销售状态
   *
   * @param productId
   * @param status
   * @return
   */
  fun setSaleStatus(productId: Int, status: Int): ServerResponse<String>

  /**
   * 获取商品详情
   *
   * @param productId
   * @return
   */
  fun getDetail(productId: Int): ServerResponse<Product>
}
