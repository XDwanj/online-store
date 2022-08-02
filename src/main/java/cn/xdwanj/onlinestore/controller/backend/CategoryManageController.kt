package cn.xdwanj.onlinestore.controller.backend

import cn.xdwanj.onlinestore.common.ServerResponse
import cn.xdwanj.onlinestore.entity.Category
import cn.xdwanj.onlinestore.service.CategoryService
import cn.xdwanj.onlinestore.util.Slf4j
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.NotBlank

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XDwanj
 * @since 2022-07-16
 */
@Tag(name = "分类管理")
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
    return categoryService.addCategory(categoryName, parentId)
  }

  @Operation(summary = "更新类别")
  @PutMapping
  fun updateCategory(
    categoryId: Int,
    categoryName: String
  ): ServerResponse<String> {
    return categoryService.updateCategory(categoryId, categoryName)
  }

  @Operation(summary = "查询类别")
  @GetMapping("/{parentId}")
  fun getCategory(@PathVariable parentId: Int): ServerResponse<List<Category>> {
    return categoryService.getCategory(parentId)
  }

  @Operation(summary = "递归查询类别")
  @GetMapping("/deep/{parentId}")
  fun deepCategory(@PathVariable parentId: Int): ServerResponse<List<Int>> {
    return categoryService.deepCategory(parentId)
  }
}
