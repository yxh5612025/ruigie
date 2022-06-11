package com.example.ruiji.mapper;

import com.example.ruiji.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 菜品及套餐分类 Mapper 接口
 * </p>
 *
 * @author 袁希宏
 * @since 2022-06-04
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}
