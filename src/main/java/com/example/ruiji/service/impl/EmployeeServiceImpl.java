package com.example.ruiji.service.impl;

import com.example.ruiji.entity.Employee;
import com.example.ruiji.mapper.EmployeeMapper;
import com.example.ruiji.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 员工信息 服务实现类
 * </p>
 *
 * @author 袁希宏
 * @since 2022-06-02
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
