package cn.xdwanj.onlinestore.controller.portal

import cn.dev33.satoken.stp.StpUtil
import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.constant.USER_SESSION
import cn.xdwanj.onlinestore.entity.Shipping
import cn.xdwanj.onlinestore.entity.User
import cn.xdwanj.onlinestore.response.CommonResponse
import cn.xdwanj.onlinestore.response.ResponseCode
import cn.xdwanj.onlinestore.service.ShippingService
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XDwanj
 * @since 2022-07-16
 */
@Slf4j
@Tag(name = "物流模块")
@Transactional
@RestController
@RequestMapping("/shipping")
class ShippingController(
  private val shippingService: ShippingService
) {

  @Operation(summary = "添加地址")
  @PostMapping
  fun add(
    shipping: Shipping
  ): CommonResponse<Map<String, Int>> {
    val user = StpUtil.getSession()[USER_SESSION] as User
    shipping.id = null
    shipping.userId = user.id

    // 查询结束后，id 会映射到 entity 中
    val isSuccess = shippingService.save(shipping)

    return if (isSuccess) {
      CommonResponse.success("添加成功", data = mapOf("shippingId" to shipping.id!!))
    } else {
      CommonResponse.error("添加失败")
    }
  }

  @Operation(summary = "删除地址")
  @DeleteMapping("/{shippingId}")
  fun delete(
    @PathVariable shippingId: Int
  ): CommonResponse<Any> {
    val user = StpUtil.getSession()[USER_SESSION] as User
    if (shippingId < 1) {
      return CommonResponse.error(ResponseCode.ILLEGAL_ARGUMENT.msg, ResponseCode.ILLEGAL_ARGUMENT.code)
    }

    val isSuccess = shippingService.ktUpdate()
      .eq(Shipping::userId, user.id)
      .eq(Shipping::id, shippingId)
      .remove()

    return if (isSuccess) {
      CommonResponse.success("删除成功")
    } else {
      CommonResponse.error("删除失败")
    }
  }

  @Operation(summary = "更新地址")
  @PutMapping
  fun update(
    shipping: Shipping
  ): CommonResponse<Any> {
    val user = StpUtil.getSession()[USER_SESSION] as User
    val exists = shippingService.ktQuery()
      .eq(Shipping::id, shipping.id)
      .eq(Shipping::userId, user.id)
      .exists()

    if (exists) {
      return CommonResponse.error("该地址不存在")
    }

    val isSuccess = shippingService.updateById(shipping)

    return if (isSuccess) {
      CommonResponse.success("更新成功")
    } else {
      CommonResponse.error("更新失败")
    }
  }

  @Operation(summary = "查询地址")
  @GetMapping("/{shippingId}")
  fun get(
    @PathVariable shippingId: String
  ): CommonResponse<Shipping> {
    val user = StpUtil.getSession()[USER_SESSION] as User

    val shipping = shippingService.ktQuery()
      .eq(Shipping::userId, user.id)
      .eq(Shipping::id, shippingId)
      .one()
      ?: return CommonResponse.error("地址不存在")

    return CommonResponse.success(data = shipping)
  }

  @Operation(summary = "地址列表")
  @GetMapping("/list")
  fun list(
    @RequestParam(defaultValue = "1") pageNum: Int,
    @RequestParam(defaultValue = "10") pageSize: Int,
  ): CommonResponse<IPage<Shipping>> {
    val user = StpUtil.getSession()[USER_SESSION] as User
    if (pageNum < 1 || pageSize < 1) {
      return CommonResponse.error(ResponseCode.ILLEGAL_ARGUMENT.msg, ResponseCode.ILLEGAL_ARGUMENT.code)
    }

    val page = shippingService.ktQuery()
      .eq(Shipping::userId, user.id)
      .page(Page(pageNum.toLong(), pageSize.toLong()))

    return CommonResponse.success(data = page)
  }
}
