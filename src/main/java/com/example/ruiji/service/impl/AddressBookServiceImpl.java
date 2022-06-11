package com.example.ruiji.service.impl;

import com.example.ruiji.entity.AddressBook;
import com.example.ruiji.mapper.AddressBookMapper;
import com.example.ruiji.service.IAddressBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 地址管理 服务实现类
 * </p>
 *
 * @author 袁希宏
 * @since 2022-06-09
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements IAddressBookService {

}
