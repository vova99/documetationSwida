package com.swida.documetation.data.serviceImpl.subObjects;

import com.swida.documetation.data.entity.subObjects.Container;
import com.swida.documetation.data.enums.StatusOfEntity;
import com.swida.documetation.data.jpa.subObjects.ContainerJPA;
import com.swida.documetation.data.service.subObjects.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContainerServiceImpl implements ContainerService {
    private ContainerJPA containerJPA;

    @Autowired
    public ContainerServiceImpl(ContainerJPA containerJPA) {
        this.containerJPA = containerJPA;
    }

    @Override
    public void save(Container container) {
        container.setEqualsToUAH(
                String.format("%.3f",
                        (Float.parseFloat(container.getCostOfDeliveryToPort())+Float.parseFloat(container.getCostOfUploading()))
                                *Float.parseFloat(container.getExchangeRate())+Float.parseFloat(container.getCostOfWeighing())
                ).replace(",",".")
        );
        containerJPA.save(container);
    }

    @Override
    public Container findById(int id) {
        return containerJPA.getOne(id);
    }

    @Override
    public List<Container> findAll() {
        return containerJPA.findAll();
    }

    @Override
    public List<Container> selectByStatusOfEntity(StatusOfEntity status) {
        return containerJPA.selectByStatusOfEntity(status);
    }

    @Override
    public void deleteByID(int id) {
        containerJPA.deleteById(id);
    }
}
