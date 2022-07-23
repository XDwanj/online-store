package cn.xdwanj.onlinestore.controller.backend;

import cn.xdwanj.onlinestore.common.ServerResponse
import cn.xdwanj.onlinestore.service.CategoryService
import cn.xdwanj.onlinestore.util.Slf4j
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
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
}
