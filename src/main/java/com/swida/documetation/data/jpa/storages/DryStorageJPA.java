package com.swida.documetation.data.jpa.storages;

import com.swida.documetation.data.entity.storages.DryStorage;
import com.swida.documetation.data.entity.storages.DryingStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DryStorageJPA extends JpaRepository<DryStorage,Integer> {
    @Query("select r from  DryStorage  r where r.breedOfTree.id=?1 and  r.userCompany.id=?2 and r.countOfDesk<>0")
    List<DryStorage> getListByUserByBreed(int breedId, int userId);

    @Query("select r from  DryStorage  r where r.breedOfTree.id=?1 and  r.userCompany.id=?2 and  r.extent<>'0.000'")
    List<DryStorage> getListByUserByBreedOak(int breedId, int userId);
}
