package com.swida.documetation.utils.other;

import com.swida.documetation.data.entity.OrderInfo;
import com.swida.documetation.data.entity.storages.PackagedProduct;
import com.swida.documetation.data.entity.subObjects.BreedOfTree;
import com.swida.documetation.data.entity.subObjects.Container;
import com.swida.documetation.data.entity.subObjects.DeliveryDocumentation;
import com.swida.documetation.data.enums.StatusOfEntity;
import lombok.Data;

@Data
public class PackageProductToJson {
    public PackageProductToJson(PackagedProduct packagedProduct) {
        id = packagedProduct.getId();
        codeOfPackage = packagedProduct.getCodeOfPackage();
        codeOfDeliveryCompany = packagedProduct.getCodeOfDeliveryCompany();
        if(packagedProduct.getBreedOfTree()!=null){
            breedOfTree = packagedProduct.getBreedOfTree();
        }
        breedDescription = packagedProduct.getBreedDescription();
        quality =packagedProduct.getQuality();
        sizeOfHeight = packagedProduct.getSizeOfHeight();
        sizeOfWidth = packagedProduct.getSizeOfWidth();
        sizeOfLong = packagedProduct.getSizeOfLong();

        countDeskInHeight = packagedProduct.getCountDeskInHeight();
        countDeskInWidth = packagedProduct.getCountDeskInWidth();
        longFact = packagedProduct.getLongFact();

        sumHeightOfPackage = packagedProduct.getSumHeightOfPackage();
        sumWidthOfPackage = packagedProduct.getSumWidthOfPackage();
        countOfDesk = packagedProduct.getCountOfDesk();
        extent = packagedProduct.getExtent();
        height_width = packagedProduct.getHeight_width();
        date = packagedProduct.getDate();
        if (packagedProduct.getContainer()!=null){
            containerName = packagedProduct.getContainer().getCode();
            containerId = packagedProduct.getContainer().getId();
        }
        if(packagedProduct.getOrderInfo()!=null) {
            orderInfoName = packagedProduct.getOrderInfo().getCodeOfOrder();
        }
        if(packagedProduct.getDeliveryDocumentation()!=null && packagedProduct.getDeliveryDocumentation().getDriverInfo()!=null) {
            idOFTruck = packagedProduct.getDeliveryDocumentation().getDriverInfo().getIdOfTruck();
        }
        statusOfEntity = packagedProduct.getStatusOfEntity();
    }

    private int id;

    private String codeOfPackage;
    private String codeOfDeliveryCompany;
    //Detail of Product

    private BreedOfTree breedOfTree;
    private String breedDescription="";
    private String quality;

    //Size of one desk
    private String sizeOfHeight;
    private String sizeOfWidth;
    private String sizeOfLong;


    //Info about package
    private String countDeskInHeight;
    private String countDeskInWidth;
    private String longFact;

    private String sumWidthOfPackage;
    private String sumHeightOfPackage;
    private String countOfDesk;
    private String extent;
    private String height_width;

    private String date;


    private String orderInfoName;
    private String containerName;
    private int containerId;
    private String idOFTruck;
    private StatusOfEntity statusOfEntity = StatusOfEntity.ACTIVE;
}