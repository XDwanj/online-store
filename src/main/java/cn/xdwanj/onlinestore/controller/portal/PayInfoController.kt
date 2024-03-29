package cn.xdwanj.onlinestore.controller.portal;

import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XDwanj
 * @since 2022-07-16
 */
@RestController
@Transactional
@RequestMapping("/payInfo")
class PayInfoController
