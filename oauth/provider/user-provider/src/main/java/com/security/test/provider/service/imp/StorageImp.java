package com.security.test.provider.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.security.test.provider.domain.Storage;
import com.security.test.provider.domain.User;
import com.security.test.provider.mapper.StorageMapper;
import com.security.test.provider.service.StorageService;
import com.security.test.provider.utils.Convert;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StorageImp extends ServiceImpl<StorageMapper, Storage> implements StorageService {

    public Storage sum(String status) {
        return super.getById(status);
    }

    public List<Storage> listStorages(int status) {
        return super.list(new QueryWrapper<Storage>().lambda().eq(Storage::getStatus, status));
    }

    public Page<User> listPage(String id) {
//       IPage<Storage> ipage=super.page(new Page<>(1, 1), new QueryWrapper<Storage>().lambda()
//                .eq(Storage::getStatus, id));
////               .convert(Convert::convert);
        return (Page<User>) super.page(new Page<>(1, 11), new QueryWrapper<Storage>().lambda()
                .eq(Storage::getStatus, id)).convert(Convert::convert);
//        return null;
    }

    public void deleteByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", "1");
        super.baseMapper.deleteByMap(map);
    }

    @Override
    public boolean updateStorageById(String id, int status) {
        return super.update(new UpdateWrapper<Storage>().lambda().set(Storage::getStatus, status).eq(Storage::getId, id));
    }
}
