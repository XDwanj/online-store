package cn.xdwanj.onlinestore.service.impl;

import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.entity.Category
import cn.xdwanj.onlinestore.exception.BusinessException
import cn.xdwanj.onlinestore.mapper.CategoryMapper
import cn.xdwanj.onlinestore.service.CategoryService
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
@Slf4j
@Service
class CategoryServiceImpl : ServiceImpl<CategoryMapper, Category>(), CategoryService {

  override fun deepCategory(categoryId: Int): List<Int> {
    val categorySet = mutableSetOf<Category>()

    findChildCategory(categorySet, categoryId)
    val categoryList = categorySet.toMutableList().map {
      it.id ?: throw BusinessException("类别id不可为空")
    }

    return categoryList.sorted()
  }

  private fun findChildCategory(categorySet: MutableSet<Category>, categoryId: Int): MutableSet<Category> {
    ktQuery()
      .eq(Category::id, categoryId)
      .one()?.let {
        categorySet += it
      }

    ktQuery()
      .eq(Category::parentId, categoryId)
      .list()
      .forEach { category ->
        category.id?.let {
          findChildCategory(categorySet, it)
        }
      }

    return categorySet
  }
}
