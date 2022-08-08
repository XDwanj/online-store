package cn.xdwanj.onlinestore.controller.backend

import cn.xdwanj.onlinestore.common.ServerResponse
import cn.xdwanj.onlinestore.entity.Category
import cn.xdwanj.onlinestore.service.CategoryService
import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.annotation.Slf4j.Companion.logger
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XDwanj
 * @since 2022-07-16
 */
@Tag(name = "后台分类管理")
@Slf4j
@RestController
@RequestMapping("/manage/category")
class CategoryManageController(
  private val categoryService: CategoryService
) {

  @Operation(summary = "添加类别")
  @PostMapping
  fun addCategory(
    categoryName: String,
    @RequestParam(defaultValue = "0") parentId: Int,
  ): ServerResponse<String> {
    categoryService.save(Category().also {
      it.name = categoryName
      it.parentId = parentId
      it.status = true
    }).let {
      if (it) return ServerResponse.success("添加成功")
    }

    return ServerResponse.error("添加品类失败")
  }

  @Operation(summary = "更新类别")
  @PutMapping
  fun updateCategory(
    categoryId: Int,
    categoryName: String
  ): ServerResponse<String> {
    categoryService.ktUpdate()
      .eq(Category::id, categoryId)
      .set(Category::name, categoryName)
      .update()
      .let {
        if (it) return ServerResponse.success("更新品类名称成功")
      }

    return ServerResponse.error("更新失败")
  }

  @Operation(summary = "查询类别")
  @GetMapping("/{parentId}")
  fun getCategory(@PathVariable parentId: Int): ServerResponse<List<Category>> {
    val categories = categoryService.ktQuery()
      .eq(Category::parentId, parentId)
      .list()

    if (categories.isEmpty()) {
      logger.info("未找到当前分类的子分类")
    }

    return ServerResponse.success(data = categories)
  }

  @Operation(summary = "递归查询类别")
  @GetMapping("/deep/{parentId}")
  fun deepCategory(@PathVariable parentId: Int): ServerResponse<List<Int>> {
    val categoryList = categoryService.deepCategory(parentId)
    return ServerResponse.success(data = categoryList)
  }
}
