package cn.xdwanj.onlinestore.service.impl

import cn.xdwanj.onlinestore.common.FTP_HOST
import cn.xdwanj.onlinestore.common.ProductStatusEnum
import cn.xdwanj.onlinestore.entity.Category
import cn.xdwanj.onlinestore.entity.Product
import cn.xdwanj.onlinestore.exception.BusinessException
import cn.xdwanj.onlinestore.mapper.CategoryMapper
import cn.xdwanj.onlinestore.mapper.ProductMapper
import cn.xdwanj.onlinestore.service.CategoryService
import cn.xdwanj.onlinestore.service.ProductService
import cn.xdwanj.onlinestore.util.formatString
import cn.xdwanj.onlinestore.vo.ProductDetailVo
import cn.xdwanj.onlinestore.vo.ProductListVo
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Service

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XDwanj
 * @since 2022-07-16
 */
@Service
class ProductServiceImpl(
  private val categoryMapper: CategoryMapper,
  private val categoryService: CategoryService
) : ServiceImpl<ProductMapper, Product>(), ProductService {

  override fun getDetailByManage(productId: Int): ProductDetailVo? {
    val product = getById(productId)
      ?: return null
    return assembleDetailVo(product)
  }

  override fun getDetail(productId: Int): ProductDetailVo? {
    val product = getById(productId) ?: throw BusinessException("产品不存在")

    if (product.status != ProductStatusEnum.ON_SALE.code) {
      return null
    }

    return assembleDetailVo(product)
  }


  fun assembleDetailVo(product: Product) = ProductDetailVo().apply {
    BeanUtils.copyProperties(product, this)
    createTime = product.createTime.formatString()
    updateTime = product.updateTime.formatString()
    val category: Category? = categoryMapper.selectById(product.categoryId)
    this.categoryId = if (category == null) 0 else category.id
  }

  override fun listProductByManage(
    pageNum: Int,
    pageSize: Int,
    productName: String,
    categoryId: Int
  ): IPage<ProductListVo> {
    return ktQuery()
      .like(productName.isNotBlank(), Product::name, productName)
      .eq(categoryId != -1, Product::categoryId, categoryId)
      .page(Page(pageNum.toLong(), pageSize.toLong()))
      .convert { assembleProductListVo(it) }
  }

  fun assembleProductListVo(product: Product) = ProductListVo().apply {
    BeanUtils.copyProperties(product, this)
    this.imageHost = FTP_HOST
  }

  override fun listProduct(
    pageNum: Int,
    pageSize: Int,
    keyword: String,
    categoryId: Int,
    orderBy: String
  ): IPage<ProductListVo> {

    categoryMapper.selectById(categoryId) ?: let {
      return Page.of(pageNum.toLong(), pageSize.toLong())
    }

    val categoryIdList = categoryService.deepCategory(categoryId)

    // TODO: review?
    var orderByRule = Triple(false, false, "")
    if (orderBy.isNotBlank()) {
      val split = orderBy.split("_")
      if (split.size != 2) throw BusinessException("排序规则错误")

      val isAsc = when (split[0]) {
        "asc" -> true
        "desc" -> false
        else -> throw BusinessException("排序规则错误")
      }

      orderByRule = Triple(true, isAsc, split[1])
    }

    val page = ktQuery()
      .`in`(Product::categoryId, categoryIdList)
      .orderByFromTripe(orderByRule)
      .page(Page(pageNum.toLong(), pageSize.toLong()))
      .convert {
        assembleProductListVo(it)
      }

    return page
  }

  /**
   * 排序规则转换，TODO：可能还有优化空间
   *
   * @param T 排序实体
   * @param orderByRule 排序规则：1.是否排序，2.是否是升序.排序字段
   * @return
   */
  private fun <T : Any> KtQueryChainWrapper<T>.orderByFromTripe(
    orderByRule: Triple<Boolean, Boolean, String>
  ): KtQueryChainWrapper<T> = this.apply {
    if (orderByRule.first)
      when (orderByRule.third) {
        "id" -> orderBy(true, orderByRule.second, Product::id)
        "categoryId" -> orderBy(true, orderByRule.second, Product::categoryId)
        "name" -> orderBy(true, orderByRule.second, Product::name)
        "price" -> orderBy(true, orderByRule.second, Product::price)
        "stock" -> orderBy(true, orderByRule.second, Product::stock)
        else -> throw BusinessException("排序规则错误")
      }
  }


}
