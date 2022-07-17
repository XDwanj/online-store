package cn.xdwanj.onlinestore.service.impl;

import cn.xdwanj.onlinestore.entity.PayInfo;
import cn.xdwanj.onlinestore.mapper.PayInfoMapper;
import cn.xdwanj.onlinestore.service.PayInfoService;
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
open class PayInfoServiceImpl : ServiceImpl<PayInfoMapper, PayInfo>(), PayInfoService {

}
