package com.swida.documetation.data.service.storages;

import com.swida.documetation.data.entity.storages.DryingStorage;
import com.swida.documetation.data.entity.storages.RawStorage;

import java.util.List;

public interface DryingStorageService {
    void save(DryingStorage ds);
    DryingStorage findById(int id);
    DryingStorage createFromRawStorage(RawStorage rawStorage);
    List<DryingStorage> findAll();
    List<DryingStorage> getListByUserByBreed(int breedId, int userId);
    void deleteByID(int id);

    //for statistic
    List<String> getListOfUnicBreedDescription(int breedId);
    List<String> getListOfUnicSizeOfHeight(int breedId);
    List<String> getListOfUnicSizeOfWidth(int breedId);
    List<String> getListOfUnicSizeOfLong(int breedId);
}
