package com.swida.documetation.data.serviceImpl.subObjects;

import com.swida.documetation.data.entity.subObjects.ContrAgent;
import com.swida.documetation.data.enums.ContrAgentType;
import com.swida.documetation.data.jpa.subObjects.ContrAgentJPA;
import com.swida.documetation.data.service.subObjects.ContrAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContrAgentServiceImpl implements ContrAgentService {
    ContrAgentJPA contrAgentJPA;

    @Autowired
    public ContrAgentServiceImpl(ContrAgentJPA contrAgentJPA) {
        this.contrAgentJPA = contrAgentJPA;
    }

    @Override
    public void save(ContrAgent provider) {
        contrAgentJPA.save(provider);
    }

    @Override
    public ContrAgent findById(int id) {
        return contrAgentJPA.getOne(id);
    }

    @Override
    public ContrAgent getObjectByName(String name) {
        return contrAgentJPA.getObjectByName(name);
    }

    @Override
    public List<ContrAgent> findAll() {
        return contrAgentJPA.findAll();
    }

    @Override
    public List<ContrAgent> getListByType(ContrAgentType type) {
        return contrAgentJPA.getListByType(type);
    }

    @Override
    public int existByNameOfProvider(String name) {
        return contrAgentJPA.countExistObj(name);
    }

    @Override
    public int getIdByUsername(String name) {
        return contrAgentJPA.getIdByUsername(name);
    }


    @Override
    public void deleteByID(int id) {
        contrAgentJPA.deleteById(id);
    }
}
