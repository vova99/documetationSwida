package com.swida.documetation.controllers;

import com.swida.documetation.data.entity.UserCompany;
import com.swida.documetation.data.entity.storages.*;
import com.swida.documetation.data.entity.subObjects.BreedOfTree;
import com.swida.documetation.data.entity.subObjects.ContrAgent;
import com.swida.documetation.data.entity.subObjects.DeliveryDocumentation;
import com.swida.documetation.data.entity.subObjects.DriverInfo;
import com.swida.documetation.data.enums.StatusOfProduct;
import com.swida.documetation.data.enums.StatusOfTreeStorage;
import com.swida.documetation.data.service.UserCompanyService;
import com.swida.documetation.data.service.storages.*;
import com.swida.documetation.data.service.subObjects.BreedOfTreeService;
import com.swida.documetation.data.service.subObjects.ContrAgentService;
import com.swida.documetation.data.service.subObjects.DeliveryDocumentationService;
import com.swida.documetation.utils.xlsParsers.ParseTreeStorageToXLS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("fabric")
@Controller
public class FabricOakController {
    private TreeStorageService treeStorageService;
    private RawStorageService rawStorageService;
    private DryingStorageService dryingStorageService;
    private DryStorageService dryStorageService;
    private PackagedProductService packagedProductService;
    private DeliveryDocumentationService deliveryDocumentationService;
    private BreedOfTreeService breedOfTreeService;
    private ContrAgentService contrAgentService;
    private UserCompanyService userCompanyService;

    @Autowired
    public FabricOakController(TreeStorageService treeStorageService, RawStorageService rawStorageService, DryingStorageService dryingStorageService, DryStorageService dryStorageService, PackagedProductService packagedProductService, DeliveryDocumentationService deliveryDocumentationService, BreedOfTreeService breedOfTreeService, ContrAgentService contrAgentService, UserCompanyService userCompanyService) {
        this.treeStorageService = treeStorageService;
        this.rawStorageService = rawStorageService;
        this.dryingStorageService = dryingStorageService;
        this.dryStorageService = dryStorageService;
        this.packagedProductService = packagedProductService;
        this.deliveryDocumentationService = deliveryDocumentationService;
        this.breedOfTreeService = breedOfTreeService;
        this.contrAgentService = contrAgentService;
        this.userCompanyService = userCompanyService;
    }

    //Tree Storage page
    @GetMapping("/getListOfTreeStorage-{userId}-2")
    public String getListOfTreeStorage(@PathVariable("userId")int userId, Model model){
        int breedId = 2;
        model.addAttribute("fragmentPathTabTreeStorage","treeStorageOAK");
        model.addAttribute("tabName","treeStorage");
        model.addAttribute("userId",userId);
        model.addAttribute("breedId",breedId);
        model.addAttribute("breedOfTreeList",breedOfTreeService.findAll());
        model.addAttribute("treeStorageList",treeStorageService.getListByUserByBreed(breedId,userId, StatusOfTreeStorage.TREE));
        model.addAttribute("contrAgentList",contrAgentService.findAll());
        return "fabricPage";
    }




    @PostMapping("/cutOfTreeStorage-{userId}-2")
    public String  addCutTreeToRawStorage(@PathVariable("userId")int userId, int idOfTreeStorageRow,
                                         RawStorage rawStorage, String usedExtent,String extentOfWaste){
        int breedId = 2;

        TreeStorage treeStorage = treeStorageService.findById(idOfTreeStorageRow);

        rawStorage.setBreedOfTree(breedOfTreeService.findById(breedId));
        rawStorage.setUserCompany(userCompanyService.findById(userId));
        rawStorage.setTreeStorage(treeStorage);
        rawStorageService.save(rawStorage);
        treeStorage.setExtent(String.format("%.3f",Float.parseFloat(treeStorage.getExtent())-Float.parseFloat(usedExtent)).replace(',','.'));

        TreeStorage recycle = new TreeStorage();
        if(extentOfWaste==null){
            recycle.setExtent("0.000");
        }else {
            recycle.setExtent(String.format("%.3f", Float.parseFloat(extentOfWaste)).replace(',', '.'));
        }
        recycle.setCodeOfProduct(treeStorage.getCodeOfProduct()+"-rec");
        recycle.setStatusOfTreeStorage(StatusOfTreeStorage.RECYCLING);
        recycle.setContrAgent(treeStorage.getContrAgent());

        recycle.setUserCompany(userCompanyService.findById(userId));
        recycle.setBreedOfTree(breedOfTreeService.findById(breedId));
        recycle.setBreedDescription(treeStorage.getBreedDescription());

        treeStorageService.save(recycle);

        treeStorageService.save(treeStorage);
        return "redirect:/fabric/getListOfTreeStorage-"+userId+"-"+breedId;
    }

    @PostMapping("/editTreeStorageRow-{userId}-2")
    public String editTreeStorageRow(@PathVariable("userId")int userId, String nameOfAgent, TreeStorage treeStorage){
        int breedId = 2;

        treeStorage.setBreedOfTree(breedOfTreeService.findById(breedId));
        treeStorage.setUserCompany(userCompanyService.findById(userId));
        ContrAgent contrAgent =  new ContrAgent();
        contrAgent.setNameOfAgent(nameOfAgent);
        treeStorage.setContrAgent(contrAgent);
        treeStorageService.putNewTreeStorageObj(treeStorage);
        return "redirect:/fabric/getListOfTreeStorage-"+userId+"-"+breedId;
    }

    @PostMapping("/addDeskFromProvider-{userId}-2")
    public String addDeskFromProvider(@PathVariable("userId")int userId, TreeStorage treeStorage, String sizeOfHeight,
                                      String description, String nameOfAgent, String extent){
       int breedId = 2;

        treeStorage.setUserCompany(userCompanyService.findById(userId));
        treeStorage.setBreedOfTree(breedOfTreeService.findById(breedId));
        treeStorage.setExtent("0.000");
        ContrAgent contrAgent =  new ContrAgent();
        contrAgent.setNameOfAgent(nameOfAgent);
        treeStorage.setContrAgent(contrAgent);
        treeStorage.setStatusOfTreeStorage(StatusOfTreeStorage.PROVIDER_DESK);
        treeStorageService.putNewTreeStorageObj(treeStorage);

        RawStorage rawStorage = new RawStorage();
        rawStorage.setUserCompany(userCompanyService.findById(userId));
        rawStorage.setBreedOfTree(breedOfTreeService.findById(breedId));
        rawStorage.setTreeStorage(treeStorage);

        rawStorage.setCodeOfProduct(treeStorage.getCodeOfProduct());
        rawStorage.setBreedDescription(treeStorage.getBreedDescription());
        rawStorage.setExtent(extent);

        rawStorage.setSizeOfHeight(sizeOfHeight);
        rawStorage.setDescription(description);

        rawStorageService.save(rawStorage);
        return "redirect:/fabric/getListOfTreeStorage-"+userId+"-"+breedId;
    }


    //RawStorage page
    @GetMapping("/getListOfRawStorage-{userId}-2")
    public String getListOfRawStorage(@PathVariable("userId")int userId, Model model){
        int breedId = 2;

        model.addAttribute("fragmentPathTabRawStorage","rawStorageOAK");
        model.addAttribute("tabName","rawStorage");
        model.addAttribute("userId",userId);
        model.addAttribute("breedId",breedId);
        model.addAttribute("breedOfTreeList",breedOfTreeService.findAll());
        model.addAttribute("rawStorageList",rawStorageService.getListByUserByBreed(breedId, userId));
        return "fabricPage";
    }
    @PostMapping("/addDeskToDrying-{userId}-2")
    private String addDeskToDrying(@PathVariable("userId")int userId, String rawStorageId, DryingStorage dryingStorage,
                                   String extentOfDrying){
        int breedId = 2;

        dryingStorage.setUserCompany(userCompanyService.findById(userId));
        dryingStorage.setBreedOfTree(breedOfTreeService.findById(breedId));
        dryingStorage.setExtent(extentOfDrying);

        RawStorage rawStorage = rawStorageService.findById(Integer.parseInt(rawStorageId));
        rawStorage.setExtent(String.format("%.3f",Float.parseFloat(rawStorage.getExtent())-Float.parseFloat(extentOfDrying)).replace(',','.'));

        dryingStorage.setSizeOfHeight(rawStorage.getSizeOfHeight());
        dryingStorage.setBreedDescription(rawStorage.getBreedDescription());
        rawStorageService.save(rawStorage);
        dryingStorage.setRawStorage(rawStorage);
        dryingStorageService.save(dryingStorage);
        return "redirect:/fabric/getListOfRawStorage-"+userId+"-"+breedId;
    }

    @PostMapping("/editRawStorageObj-{userId}-2")
    public String editRawStorageObj(@PathVariable("userId")int userId, RawStorage rawStorage){
        int breedId = 2;

        RawStorage rawStorageDB = rawStorageService.findById(rawStorage.getId());
        rawStorageDB.setBreedDescription(rawStorage.getBreedDescription());
        rawStorageDB.setCodeOfProduct(rawStorage.getCodeOfProduct());
        rawStorageDB.setExtent(rawStorage.getExtent());
        rawStorageDB.setSizeOfHeight(rawStorage.getSizeOfHeight());
        rawStorageDB.setDescription(rawStorage.getDescription());
        rawStorageService.save(rawStorageDB);
        return "redirect:/fabric/getListOfRawStorage-"+userId+"-"+breedId;
    }


    //Drying page
    @GetMapping("/getListOfDryingStorage-{userId}-2")
    public String getListOfDryingStorage(@PathVariable("userId")int userId, Model model){
        int breedId = 2;

        model.addAttribute("fragmentPathTabDryingStorage","dryingStorageOAK");
        model.addAttribute("tabName","dryingStorage");
        model.addAttribute("userId",userId);
        model.addAttribute("breedId",breedId);
        model.addAttribute("breedOfTreeList",breedOfTreeService.findAll());
        model.addAttribute("dryingStorageList",dryingStorageService.getListByUserByBreed(breedId,userId));
        return "fabricPage";
    }

    @PostMapping("/addDeskToDryStorage-{userId}-2")
    private String addDeskToDryStorage(@PathVariable("userId")int userId, String dryingStorageId, String codeOfProduct,
                                       String breedDescription){
        int breedId = 2;

        DryingStorage dryingStorageDB = dryingStorageService.findById(Integer.parseInt(dryingStorageId));
        DryStorage dryStorage = new DryStorage();
        dryStorage.setCodeOfProduct(codeOfProduct);

        dryStorage.setBreedOfTree(dryingStorageDB.getBreedOfTree());
        dryStorage.setBreedDescription(breedDescription);
        dryStorage.setExtent(dryingStorageDB.getExtent());
        dryStorage.setSizeOfHeight(dryingStorageDB.getSizeOfHeight());
        dryStorage.setDescription(dryingStorageDB.getDescription());
        dryStorage.setUserCompany(dryingStorageDB.getUserCompany());
        dryStorage.setDryingStorage(dryingStorageDB);

        dryStorageService.save(dryStorage);
        dryingStorageDB.setExtent("0.000");
        dryingStorageService.save(dryingStorageDB);
        return "redirect:/fabric/getListOfDryingStorage-"+userId+"-"+breedId;
    }

    @PostMapping("/editDryingStorageObj-{userId}-2")
    public String editDryingStorageObj(@PathVariable("userId")int userId, DryingStorage dryingStorage,String breedDescription,
                                       String description){
        int breedId = 2;

        DryingStorage dryingStorageDB = dryingStorageService.findById(dryingStorage.getId());
        if(dryingStorageDB.getCountOfDesk()!=dryingStorage.getCountOfDesk()){
            int count = dryingStorageDB.getCountOfDesk()-dryingStorage.getCountOfDesk();
            RawStorage rawStorage = dryingStorageDB.getRawStorage();
            rawStorage.setCountOfDesk(rawStorage.getCountOfDesk()+count);
            rawStorageService.save(rawStorage);
        }
        dryingStorageDB.setDescription(description);
        dryingStorageDB.setCodeOfProduct(dryingStorage.getCodeOfProduct());
        dryingStorageDB.setCountOfDesk(dryingStorage.getCountOfDesk());
        dryingStorageDB.setBreedDescription(breedDescription);
        dryingStorageService.save(dryingStorageDB);
        return "redirect:/fabric/getListOfDryingStorage-"+userId+"-"+breedId;
    }


    //Dry Storage page
    @GetMapping("/getListOfDryStorage-{userId}-2")
    public String getListOfDryStorage(@PathVariable("userId")int userId,Model model){
        int breedId = 2;

        model.addAttribute("fragmentPathTabDryStorage","dryStorageOAK");
        model.addAttribute("tabName","dryStorage");
        model.addAttribute("userId",userId);
        model.addAttribute("breedId",breedId);
        model.addAttribute("breedOfTreeList",breedOfTreeService.findAll());
        model.addAttribute("dryStorageList",dryStorageService.getListByUserByBreed(breedId,userId));
        return "fabricPage";
    }

    @PostMapping("/editDryStorageRow-{userId}-2")
    public String editDryStorageRow(@PathVariable("userId")int userId, String id, String codeOfProduct,String breedDescription){
        int breedId = 2;
        DryStorage dryStorage = dryStorageService.findById(Integer.parseInt(id));
        dryStorage.setCodeOfProduct(codeOfProduct);
        dryStorage.setBreedDescription(breedDescription);
        dryStorageService.save(dryStorage);
        return "redirect:/fabric/getListOfDryStorage-"+userId+"-"+breedId;
    }


    //Packaged product page
    @GetMapping("/getListOfPackagedProduct-{userId}-2")
    public String getListOfPackagedProduct(@PathVariable("userId")int userId, Model model){
        int breedId = 2;
        model.addAttribute("fragmentPathTabPackageStorage","packageStorageOak");
        model.addAttribute("tabName","packageStorage");
        model.addAttribute("userId",userId);
        model.addAttribute("breedId",breedId);
        model.addAttribute("breedOfTreeList",breedOfTreeService.findAll());
        model.addAttribute("packageOakList",packagedProductService.getListByUserByBreed(breedId,userId, StatusOfProduct.ON_STORAGE));
        return "fabricPage";
    }


    //Delivery page
    @GetMapping("/getListOfDeliveryDocumentation-{userId}-2")
    public String getListOfDeliveryDocumentation(@PathVariable("userId")int userId,Model model){

        int breedId = 2;
        model.addAttribute("fragmentPathTabDelivery","deliveryInfoOak");
        model.addAttribute("tabName","deliveryInfo");
        model.addAttribute("userId",userId);
        model.addAttribute("breedId",breedId);
        model.addAttribute("breedOfTreeList",breedOfTreeService.findAll());
        model.addAttribute("deliveryDocumentations",deliveryDocumentationService.getListByUserByBreed(breedId,userId));
        return "fabricPage";
    }

}

