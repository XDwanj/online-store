package cn.xdwanj.onlinestore.controller.portal

import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.response.CommonResponse
import cn.xdwanj.onlinestore.response.ResponseCode
import cn.xdwanj.onlinestore.service.ProductService
import cn.xdwanj.onlinestore.vo.ProductDetailVo
import cn.xdwanj.onlinestore.vo.ProductListVo
import com.baomidou.mybatisplus.core.metadata.IPage
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XDwanj
 * @since 2022-08-03
 */
@Slf4j
@Tag(name = "商品管理")
@RestController
@RequestMapping("/product")
class ProductController(
  private val productService: ProductService
) {

  @Operation(summary = "获取商品详情")
  @GetMapping("/detail/{productId}")
  fun getDetail(@PathVariable productId: Int): CommonResponse<ProductDetailVo> {
    val productVo = productService.getDetail(productId)
      ?: return CommonResponse.error("商品已下架")

    return CommonResponse.success(data = productVo)
  }

  @Operation(summary = "获取商品集合")
  @GetMapping("/list", "/search")
  fun listDetail(
    pageNum: Int,
    pageSize: Int,
    @RequestParam(defaultValue = "")
    keyword: String,
    categoryId: Int,
    @Parameter(description = "格式：'排序规则_排序字段'，默认不排序")
    @RequestParam(defaultValue = "")
    orderBy: String
  ): CommonResponse<IPage<ProductListVo>> {
    if (pageNum < 1 || pageSize < 1 || categoryId < 0) {
      return CommonResponse.error(ResponseCode.ILLEGAL_ARGUMENT.msg, ResponseCode.ILLEGAL_ARGUMENT.code)
    }

    val page = productService.listProduct(pageNum, pageSize, keyword, categoryId, orderBy)
    return CommonResponse.success(data = page)
  }

}