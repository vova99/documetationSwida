package com.swida.documetation.data.serviceImpl.subObjects;

import com.swida.documetation.data.entity.subObjects.ContrAgent;
import com.swida.documetation.data.enums.ContrAgentType;
import com.swida.documetation.data.jpa.subObjects.ContrAgentJPA;
import com.swida.documetation.data.service.subObjects.ContrAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        return contrAgentJPA.findAll(Sort.by(Sort.Direction.ASC,"id"));
    }

    @Override
    public List<ContrAgent> getListByType(ContrAgentType type) {
        return contrAgentJPA.getListByType(type)
                .stream()
                .sorted((o1, o2) -> o2.getId()-o1.getId())
                .collect(Collectors.toList());
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
