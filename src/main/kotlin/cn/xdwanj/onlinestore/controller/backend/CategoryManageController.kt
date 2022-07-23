package cn.xdwanj.onlinestore.controller.backend;

import cn.xdwanj.onlinestore.common.ServerResponse
import cn.xdwanj.onlinestore.entity.Category
import cn.xdwanj.onlinestore.service.CategoryService
import cn.xdwanj.onlinestore.util.Slf4j
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
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
@Api(tags = ["分类管理"])
@Slf4j
@RestController
@RequestMapping("/manage/category")
class CategoryManageController(
  private val categoryService: CategoryService
) {

  @ApiOperation("添加类别")
  @PostMapping
  fun addCategory(
    @NotBlank categoryName: String,
    @RequestParam(defaultValue = "0") parentId: Int,
  ): ServerResponse<String> {
    return categoryService.addCategory(categoryName, parentId)
  }

  @ApiOperation("更新类别")
  @PatchMapping
  fun updateCategory(
    categoryId: Int,
    @NotBlank categoryName: String
  ): ServerResponse<String> {
    return categoryService.updateCategory(categoryId, categoryName)
  }

  @ApiOperation("查询类别")
  @GetMapping("/{parentId}")
  fun getCategory(@PathVariable parentId: Int): ServerResponse<List<Category>> {
    return categoryService.getCategory(parentId)
  }

  @ApiOperation("递归查询类别")
  @GetMapping("/deep/{parentId}")
  fun deepCategory(@PathVariable parentId: Int): ServerResponse<List<Int>> {
    return categoryService.deepCategory(parentId)
  }
}
