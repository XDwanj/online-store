package cn.xdwanj.onlinestore.service;

import cn.xdwanj.onlinestore.entity.Product
import cn.xdwanj.onlinestore.vo.ProductDetailVo
import cn.xdwanj.onlinestore.vo.ProductListVo
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.IService

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
   * 管理员获取商品详情
   *
   * @param productId
   * @return
   */
  fun getDetailByManage(productId: Int): ProductDetailVo?

  /**
   * 用户获取商品详情
   *
   * @param productId
   * @return
   */
  fun getDetail(productId: Int): ProductDetailVo?

  /**
   * 获取商品分页列表，附带查询功能
   *
   * @param pageNum
   * @param pageSize
   * @return
   */
  fun listProductByManage(
    pageNum: Int,
    pageSize: Int,
    productName: String = "",
    categoryId: Int = -1
  ): IPage<ProductListVo>

  /**
   * 通过关键字和类别 排序查询商品详情列表
   *
   * @param pageNum
   * @param pageSize
   * @param keyword
   * @param categoryId
   * @param orderBy
   * @return
   */
  fun listProduct(
    pageNum: Int,
    pageSize: Int,
    keyword: String = "",
    categoryId: Int,
    orderBy: String
  ): IPage<ProductListVo>

}
