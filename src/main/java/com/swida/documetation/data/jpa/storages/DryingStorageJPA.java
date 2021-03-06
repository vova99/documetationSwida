package com.swida.documetation.data.jpa.storages;

import com.swida.documetation.data.entity.storages.DryingStorage;
import com.swida.documetation.data.entity.storages.RawStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DryingStorageJPA extends JpaRepository<DryingStorage,Integer> {
    @Query("select r from  DryingStorage  r where r.breedOfTree.id=?1 and  r.userCompany.id=?2")
    List<DryingStorage> getListByUserByBreed(int breedId, int userId);
}
