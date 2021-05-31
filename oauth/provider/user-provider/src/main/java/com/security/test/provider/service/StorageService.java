package com.security.test.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.security.test.provider.domain.Storage;

import java.util.List;

public interface StorageService extends IService<Storage> {

    Storage sum(String status);

    List<Storage> listStorages(int status);

    boolean updateStorageById(String id, int status);
}
