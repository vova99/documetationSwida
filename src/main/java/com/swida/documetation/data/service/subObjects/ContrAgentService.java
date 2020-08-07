package com.swida.documetation.data.service.subObjects;

import com.swida.documetation.data.entity.subObjects.ContrAgent;
import com.swida.documetation.data.enums.ContrAgentType;

import java.util.List;

public interface ContrAgentService {
    void save(ContrAgent provider);
    ContrAgent findById(int id);
    List<ContrAgent> findAll();
    List<ContrAgent> getListByType(ContrAgentType type);
    //return 0 if doesnot Exist
    int existByNameOfProvider(String name);
    int getIdByUsername(String name);
    void deleteByID(int id);
}
