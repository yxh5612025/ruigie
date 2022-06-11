package com.example.ruiji.service.impl;

import com.example.ruiji.entity.User;
import com.example.ruiji.mapper.UserMapper;
import com.example.ruiji.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author 袁希宏
 * @since 2022-06-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
