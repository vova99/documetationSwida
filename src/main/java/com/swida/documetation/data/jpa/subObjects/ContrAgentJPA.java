package com.swida.documetation.data.jpa.subObjects;

import com.swida.documetation.data.entity.subObjects.ContrAgent;
import com.swida.documetation.data.enums.ContrAgentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface ContrAgentJPA extends JpaRepository<ContrAgent,Integer> {
    @Query("select count(pr.id) from ContrAgent pr where pr.nameOfAgent=?1 and pr.statusOfEntity='ACTIVE'")
    int countExistObj(String nameOfTreeProvider);

    @Query("select pr.id from ContrAgent pr where pr.nameOfAgent=?1 and pr.statusOfEntity='ACTIVE'")
    int getIdByUsername(String nameOfTreeProvider);

    @Query("select obj from  ContrAgent obj where obj.nameOfAgent=?1 and obj.statusOfEntity='ACTIVE'")
    ContrAgent getObjectByName (String name);

    @Query("select pr from ContrAgent pr where pr.contrAgentType=?1 and pr.statusOfEntity='ACTIVE'")
    List<ContrAgent> getListByType(ContrAgentType type);
}
