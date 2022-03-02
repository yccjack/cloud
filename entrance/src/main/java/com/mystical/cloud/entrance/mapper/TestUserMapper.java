package com.mystical.cloud.entrance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mystical.cloud.entrance.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestUserMapper extends BaseMapper<User> {
}
