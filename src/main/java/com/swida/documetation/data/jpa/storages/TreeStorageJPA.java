package com.swida.documetation.data.jpa.storages;

import com.swida.documetation.data.entity.storages.TreeStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TreeStorageJPA extends JpaRepository<TreeStorage,Integer> {
    @Query("select t from  TreeStorage  t where t.breedOfTree.id=?1 and  t.userCompany.id=?2")
    List<TreeStorage> getListByUserByBreed(int breedId, int userId);
}
