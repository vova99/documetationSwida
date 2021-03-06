package com.swida.documetation.data.service.storages;

import com.swida.documetation.data.entity.storages.DryStorage;
import com.swida.documetation.data.entity.storages.DryingStorage;

import java.util.List;

public interface DryStorageService {
    void save(DryStorage ds);
    DryStorage findById(int id);
    List<DryStorage> findAll();
    List<DryStorage> getListByUserByBreed(int breedId, int userId);
    void deleteByID(int id);
}
