package com.swida.documetation.controllers;

import com.swida.documetation.data.entity.storages.PackagedProduct;
import com.swida.documetation.data.entity.subObjects.DeliveryDocumentation;
import com.swida.documetation.data.entity.subObjects.DriverInfo;
import com.swida.documetation.data.enums.StatusOfProduct;
import com.swida.documetation.data.service.storages.PackagedProductService;
import com.swida.documetation.data.service.subObjects.DeliveryDocumentationService;
import com.swida.documetation.data.service.subObjects.DriverInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FabricRestController {
    DriverInfoService driverInfoService;
    PackagedProductService packagedProductService;
    DeliveryDocumentationService deliveryDocumentationService;

    @Autowired
    public FabricRestController(DriverInfoService driverInfoService, PackagedProductService packagedProductService, DeliveryDocumentationService deliveryDocumentationService) {
        this.driverInfoService = driverInfoService;
        this.packagedProductService = packagedProductService;
        this.deliveryDocumentationService = deliveryDocumentationService;
    }

    @PostMapping("/createDeliveryDoc-{userID}-{breedID}")
    public String createDeliveryDoc(@PathVariable("userID") String userID, @PathVariable("breedID") String breedID,
            String[] list, String name, String phone, String idOfTruck, String numberOfTruck, String numberOfTrailer) {
        DriverInfo driverInfo = new DriverInfo();
        driverInfo.setFullName(name);
        driverInfo.setIdOfTruck(idOfTruck);
        driverInfo.setPhone(phone);
        driverInfo.setNumberOfTruck(numberOfTruck);
        driverInfo.setNumberOfTrailer(numberOfTrailer);

        List<PackagedProduct> productList = new ArrayList<>();
        DeliveryDocumentation deliveryDocumentation = new DeliveryDocumentation();
        for (int i=0; i<list.length; i++){
            productList.add(packagedProductService.findById(Integer.parseInt(list[i])));
            productList.get(i).setStatusOfProduct(StatusOfProduct.IN_DELIVERY);
            packagedProductService.save(productList.get(i));
            if (i==0) {
                deliveryDocumentation.setUserCompany(productList.get(0).getUserCompany());
            }
        }

        deliveryDocumentation.setProductList(productList);
        deliveryDocumentation.setDriverInfo(driverInfo);

        driverInfoService.save(driverInfo);
        deliveryDocumentationService.save(deliveryDocumentation);
        return "redirect:/fabric/getListOfPackagedProduct-"+userID+"-"+breedID;
    }
}
