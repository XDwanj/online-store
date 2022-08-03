package cn.xdwanj.onlinestore.service.impl

import cn.xdwanj.onlinestore.common.BusinessException
import cn.xdwanj.onlinestore.common.FTP_HOST
import cn.xdwanj.onlinestore.common.ProductStatusEnum
import cn.xdwanj.onlinestore.common.ServerResponse
import cn.xdwanj.onlinestore.entity.Category
import cn.xdwanj.onlinestore.entity.Product
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
  override fun saveProduct(product: Product): ServerResponse<String> {
    if (save(product)) {
      return ServerResponse.success("保存成功")
    }

    return ServerResponse.error("保存失败")
  }

  override fun updateProduct(product: Product): ServerResponse<String> {
    ktUpdate()
      .eq(Product::id, product.id)
      .setEntity(product)
      .update().let {
        if (it) return ServerResponse.success("更新成功")
      }

    return ServerResponse.error("更新失败")
  }

  override fun setSaleStatus(productId: Int, status: Int): ServerResponse<String> {
    ktUpdate()
      .eq(Product::id, productId)
      .set(Product::status, status)
      .update().let {
        if (it) return ServerResponse.success("状态更新成功")
      }

    return ServerResponse.error("状态更新失败")
  }

  override fun getDetailByManage(productId: Int): ServerResponse<ProductDetailVo> {
    val product = getById(productId) ?: throw BusinessException("产品不存在")
    return ServerResponse.success(data = assembleDetailVo(product))
  }

  override fun getDetail(productId: Int): ServerResponse<ProductDetailVo> {
    val product = getById(productId) ?: throw BusinessException("产品不存在")

    if (product.status != ProductStatusEnum.ON_SALE.code) {
      return ServerResponse.error("商品已下架")
    }

    val productDetailVo = assembleDetailVo(product)
    return ServerResponse.success(data = productDetailVo)
  }


  private fun assembleDetailVo(product: Product) = ProductDetailVo(
    id = product.id,
    subtitle = product.subtitle,
    price = product.price,
    mainImage = product.mainImage,
    subImages = product.subImages,
    categoryId = product.categoryId,
    detail = product.detail,
    name = product.name,
    status = product.status,
    stock = product.stock,
    createTime = product.createTime?.formatString(),
    updateTime = product.updateTime?.formatString(),
    imageHost = FTP_HOST,
  ).apply {
    val category: Category? = categoryMapper.selectById(product.id)
    this.categoryId = if (category == null) 0 else category.id
  }

  override fun listProductByManage(pageNum: Int, pageSize: Int, productName: String, categoryId: Int): ServerResponse<IPage<ProductListVo>> {
    val page = ktQuery()
      .like(productName.isNotBlank(), Product::name, productName)
      .eq(categoryId != -1, Product::categoryId, categoryId)
      .page(Page(pageNum.toLong(), pageSize.toLong()))
      .convert { assembleProductListVo(it) }

    return ServerResponse.success(data = page)
  }

  fun assembleProductListVo(product: Product) = ProductListVo(
    id = product.id,
    categoryId = product.categoryId,
    name = product.name,
    subtitle = product.subtitle,
    mainImage = product.mainImage,
    price = product.price,
    status = product.status,
    imageHost = FTP_HOST
  )

  override fun listProduct(pageNum: Int, pageSize: Int, keyword: String, categoryId: Int, orderBy: String): ServerResponse<IPage<ProductListVo>> {
    if (categoryId < 0) return ServerResponse.error("类别非法")

    categoryMapper.selectById(categoryId)
      ?: let {
        val page = Page.of<ProductListVo>(pageNum.toLong(), pageSize.toLong())
        return ServerResponse.success(data = page)
      }

    val categoryIdList = categoryService.deepCategory(categoryId).data

    var orderByRule: Triple<Boolean, Boolean, String> = Triple(false, false, "")
    if (orderBy.isNotBlank()) {
      val split = orderBy.split("_")
      if (split.size != 2) return ServerResponse.error("排序规则错误")

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

    return ServerResponse.success(data = page)
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
