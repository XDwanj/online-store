package cn.xdwanj.onlinestore.controller.backend;

import cn.xdwanj.onlinestore.common.ServerResponse
import cn.xdwanj.onlinestore.service.CategoryService
import cn.xdwanj.onlinestore.util.Slf4j
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

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
@RequestMapping("manage/category")
class CategoryController(
  private val categoryService: CategoryService
) {

  @PostMapping
  fun addCategory(
    categoryName: String?,
    @RequestParam(defaultValue = "0") parentId: Int,
  ): ServerResponse<String> {
    if (categoryName.isNullOrBlank()) {
      return ServerResponse.createByError("分类名不可为空")
    }
    return categoryService.addCategory(categoryName, parentId)
  }

}
