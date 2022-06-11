package com.example.ruiji.service;

import com.example.ruiji.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 菜品及套餐分类 服务类
 * </p>
 *
 * @author 袁希宏
 * @since 2022-06-04
 */
public interface ICategoryService extends IService<Category> {

    void delete(Long id);
}
