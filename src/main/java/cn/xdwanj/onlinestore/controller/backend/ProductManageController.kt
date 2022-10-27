package cn.xdwanj.onlinestore.controller.backend

import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.common.FTP_HOST
import cn.xdwanj.onlinestore.common.UPLOAD_PATH
import cn.xdwanj.onlinestore.entity.Product
import cn.xdwanj.onlinestore.response.CommonResponse
import cn.xdwanj.onlinestore.response.ResponseCode
import cn.xdwanj.onlinestore.response.WangEditor
import cn.xdwanj.onlinestore.service.FileService
import cn.xdwanj.onlinestore.service.ProductService
import cn.xdwanj.onlinestore.vo.ProductDetailVo
import cn.xdwanj.onlinestore.vo.ProductListVo
import com.baomidou.mybatisplus.core.metadata.IPage
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XDwanj
 * @since 2022-07-16
 */
@Tag(name = "后台商品管理")
@Slf4j
@RestController
@RequestMapping("/manage/product")
class ProductManageController(
  private val productService: ProductService,
  private val fileService: FileService
) {

  @Operation(summary = "商品保存")
  @PostMapping
  fun save(product: Product): CommonResponse<String> {
    val isSuccess = productService.save(product)

    return if (isSuccess) {
      CommonResponse.success("保存成功")
    } else {
      CommonResponse.error("保存失败")
    }
  }

  @Operation(summary = "商品更新")
  @PutMapping
  fun update(product: Product): CommonResponse<String> {
    val isSuccess = productService.updateById(product)

    if (isSuccess) {
      return CommonResponse.success("更新成功")
    }
    return CommonResponse.error("更新失败")
  }

  @Operation(summary = "设置商品销售状态")
  @PutMapping("/sale-status")
  fun setSaleStatus(productId: Int, status: Int): CommonResponse<String> {
    val isSuccess = productService.ktUpdate()
      .eq(Product::id, productId)
      .set(Product::status, status)
      .update()

    if (isSuccess) {
      return CommonResponse.success("更新成功")
    }

    return CommonResponse.error("更新失败")
  }

  @Operation(summary = "获取商品详情")
  @GetMapping("/{productId}")
  fun getProduct(@PathVariable productId: Int): CommonResponse<ProductDetailVo> {
    val productDetailVo = productService.getDetailByManage(productId)
      ?: return CommonResponse.error("商品不存在")
    return CommonResponse.success(data = productDetailVo)
  }

  @Operation(summary = "获取商品列表，带搜索功能")
  @GetMapping("/list", "/search")
  fun listProduct(
    @RequestParam(defaultValue = "") keyword: String,
    @RequestParam(defaultValue = "1") pageNum: Int,
    @RequestParam(defaultValue = "5") pageSize: Int
  ): CommonResponse<IPage<ProductListVo>> {
    if (pageNum < 1 || pageSize < 1) {
      return CommonResponse.error(ResponseCode.ILLEGAL_ARGUMENT.msg, ResponseCode.ILLEGAL_ARGUMENT.code)
    }

    val page = productService.listProductByManage(pageNum, pageSize, keyword)
    return CommonResponse.success(data = page)
  }

  @Operation(summary = "文件上传")
  @PostMapping("/upload/file")
  fun upload(@RequestPart file: MultipartFile): CommonResponse<Map<String, String>> {
    val uri = fileService.upload(file, UPLOAD_PATH)
    val url = "$FTP_HOST/$uri"
    val resultMap = mapOf(
      "uri" to uri,
      "url" to url
    )
    return CommonResponse.success(data = resultMap)
  }

  @Operation(summary = "富文本上传")
  @PostMapping("/upload/rich-text")
  fun uploadRichText(
    @RequestParam(value = "upload_file", required = false)
    file: MultipartFile,
    @Parameter(hidden = true)
    request: HttpServletRequest
  ): WangEditor {
    val path = request.servletContext.getRealPath(UPLOAD_PATH)
    val targetFileName = fileService.upload(file, path)
    if (targetFileName.isBlank()) {
      return WangEditor.error()
    }

    val url = "$FTP_HOST/$targetFileName"
    return WangEditor.success(listOf(url))
  }
}
