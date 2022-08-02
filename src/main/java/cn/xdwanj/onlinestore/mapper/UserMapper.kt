package cn.xdwanj.onlinestore.mapper;

import cn.xdwanj.onlinestore.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XDwanj
 * @since 2022-07-16
 */
@Mapper
interface UserMapper : BaseMapper<User>
