package cn.xdwanj.onlinestore.service.impl;

import cn.xdwanj.onlinestore.common.ServerResponse
import cn.xdwanj.onlinestore.entity.Category;
import cn.xdwanj.onlinestore.mapper.CategoryMapper;
import cn.xdwanj.onlinestore.service.CategoryService;
import cn.xdwanj.onlinestore.util.Slf4j
import cn.xdwanj.onlinestore.util.Slf4j.Companion.logger
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime

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
  override fun addCategory(categoryName: String, parentId: Int): ServerResponse<String> {
    save(Category().also {
      it.name = categoryName
      it.parentId = parentId
      it.status = true
      it.createTime = LocalDateTime.now()
      it.updateTime = LocalDateTime.now()
    }).let {
      if (!it) return ServerResponse.error("添加品类失败")
    }

    return ServerResponse.success("添加成功")
  }

  override fun updateCategory(categoryId: Int, categoryName: String): ServerResponse<String> {
    ktUpdate()
      .eq(Category::id, categoryId)
      .set(Category::name, categoryName)
      .set(Category::updateTime, LocalDateTime.now())
      .update().let {
        if (!it) return ServerResponse.error("更新失败")
      }
    return ServerResponse.success("更新品类名称成功")
  }

  override fun getCategory(parentId: Int): ServerResponse<List<Category>> {
    val categories = ktQuery()
      .eq(Category::parentId, parentId)
      .list()

    if (categories.isEmpty()) {
      logger.info("未找到当前分类的子分类")
    }

    return ServerResponse.success(data = categories)
  }

  override fun deepCategory(categoryId: Int): ServerResponse<List<Int>> {
    val categorySet = mutableSetOf<Category>()
    findChildCategory(categorySet, categoryId)
    val categoryIdList = mutableListOf<Int>()
    categorySet.forEach { category ->
      category.id?.let { categoryIdList += it }
    }

    return ServerResponse.success(data = categoryIdList)
  }

  private fun findChildCategory(categorySet: MutableSet<Category>, categoryId: Int): MutableSet<Category> {
    ktQuery()
      .eq(Category::id, categoryId)
      .one()?.let {
        categorySet += it
      }

    getCategory(categoryId).data?.forEach { category ->
      category.id?.let { findChildCategory(categorySet, it) }
    }

    return categorySet
  }
}
