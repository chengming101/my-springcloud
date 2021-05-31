package com.security.test.provider.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.security.test.provider.domain.Storage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StorageMapper extends BaseMapper<Storage> {

    Storage getStorage();
}
