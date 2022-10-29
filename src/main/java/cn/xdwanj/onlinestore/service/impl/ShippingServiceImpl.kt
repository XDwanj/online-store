package cn.xdwanj.onlinestore.service.impl;

import cn.xdwanj.onlinestore.entity.Shipping;
import cn.xdwanj.onlinestore.mapper.ShippingMapper;
import cn.xdwanj.onlinestore.service.ShippingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XDwanj
 * @since 2022-07-16
 */
@Service
class ShippingServiceImpl : ServiceImpl<ShippingMapper, Shipping>(), ShippingService
