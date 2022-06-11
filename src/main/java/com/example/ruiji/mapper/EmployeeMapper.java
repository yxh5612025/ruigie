package com.example.ruiji.mapper;

import com.example.ruiji.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 员工信息 Mapper 接口
 * </p>
 *
 * @author 袁希宏
 * @since 2022-06-02
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}
