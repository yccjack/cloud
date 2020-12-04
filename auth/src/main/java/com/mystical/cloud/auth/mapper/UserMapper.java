package com.mystical.cloud.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mystical.cloud.auth.bean.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description:
 * @author: MysticalYcc
 * @Date: 2020/12/4
 */
@Mapper
public interface UserMapper extends BaseMapper<UserInfo> {
}
