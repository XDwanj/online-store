package cn.xdwanj.onlinestore.service;

import cn.xdwanj.onlinestore.vo.ProductDetailVo
import cn.xdwanj.onlinestore.common.ServerResponse
import cn.xdwanj.onlinestore.entity.Product;
import cn.xdwanj.onlinestore.vo.ProductListVo
import com.baomidou.mybatisplus.core.metadata.IPage
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
   * 管理员获取商品详情
   *
   * @param productId
   * @return
   */
  fun getDetailByManage(productId: Int): ServerResponse<ProductDetailVo>

  /**
   * 用户获取商品详情
   *
   * @param productId
   * @return
   */
  fun getDetail(productId: Int): ServerResponse<ProductDetailVo>

  /**
   * 获取商品分页列表，附带查询功能
   *
   * @param pageNum
   * @param pageSize
   * @return
   */
  fun listProductByManage(pageNum: Int, pageSize: Int, productName: String = "", categoryId: Int = -1): ServerResponse<IPage<ProductListVo>>

  /**
   * 通过关键字和类别查询商品详情列表
   *
   * @param keyword
   * @param categoryId
   * @param pageNum
   * @param pageSize
   */
  fun listProduct(pageNum: Int, pageSize: Int, keyword: String = "", categoryId: Int = -1)

}
