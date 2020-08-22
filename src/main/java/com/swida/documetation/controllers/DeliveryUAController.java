package com.swida.documetation.controllers;

import com.swida.documetation.data.entity.OrderInfo;
import com.swida.documetation.data.entity.UserCompany;
import com.swida.documetation.data.entity.storages.DescriptionDeskOak;
import com.swida.documetation.data.entity.storages.PackagedProduct;
import com.swida.documetation.data.entity.subObjects.BreedOfTree;
import com.swida.documetation.data.entity.subObjects.DeliveryDocumentation;
import com.swida.documetation.data.enums.ContrAgentType;
import com.swida.documetation.data.enums.DeliveryDestinationType;
import com.swida.documetation.data.enums.StatusOfOrderInfo;
import com.swida.documetation.data.service.OrderInfoService;
import com.swida.documetation.data.service.UserCompanyService;
import com.swida.documetation.data.service.storages.DescriptionDeskOakService;
import com.swida.documetation.data.service.storages.PackagedProductService;
import com.swida.documetation.data.service.subObjects.*;
import com.swida.documetation.utils.xlsParsers.ImportOakOrderDataFromXLS;
import com.swida.documetation.utils.xlsParsers.ImportOrderDataFromXLS;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/multimodal")
public class DeliveryUAController {
    private UserCompanyService userCompanyService;
    private BreedOfTreeService breedOfTreeService;
    private ContrAgentService contrAgentService;
    private OrderInfoService orderInfoService;
    private ContainerService containerService;
    private DeliveryDocumentationService deliveryDocumentationService;
    private PackagedProductService packagedProductService;
    private DriverInfoService driverInfoService;
    private DescriptionDeskOakService deskOakService;

    @Autowired
    public DeliveryUAController(UserCompanyService userCompanyService, BreedOfTreeService breedOfTreeService,
                                ContrAgentService contrAgentService, OrderInfoService orderInfoService,
                                ContainerService containerService,DeliveryDocumentationService deliveryDocumentationService,
                                PackagedProductService packagedProductService, DriverInfoService driverInfoService,
                                DescriptionDeskOakService deskOakService) {
        this.userCompanyService = userCompanyService;
        this.breedOfTreeService = breedOfTreeService;
        this.contrAgentService = contrAgentService;
        this.orderInfoService = orderInfoService;
        this.containerService = containerService;
        this.deliveryDocumentationService = deliveryDocumentationService;
        this.packagedProductService = packagedProductService;
        this.driverInfoService = driverInfoService;
        this.deskOakService = deskOakService;
    }



    // Main page
    @GetMapping("/getDeliveryInUkraine")
    public String getDeliveryInUkraine(Model model){

        model.addAttribute("navTabName","delivery");
        model.addAttribute("tabName","contracts");
        model.addAttribute("fragmentPathTabConfig","deliveryUA");
        model.addAttribute("fragmentPathOrderInfo", "ordersForDelivery");
        model.addAttribute("orderInfoList",orderInfoService.getOrdersByStatusOfOrderByDestination(StatusOfOrderInfo.MAIN, DeliveryDestinationType.COUNTRY));
        model.addAttribute("breedOfTreeList",breedOfTreeService.findAll());
        model.addAttribute("userCompanyList",userCompanyService.getListOfAllUsersROLE());
        model.addAttribute("contrAgentProviderList",contrAgentService.getListByType(ContrAgentType.PROVIDER));


        UserCompany userCompany = userCompanyService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (userCompany!=null){
            model.addAttribute("userCompanyName",userCompany);
            model.addAttribute("userId",userCompany.getId());
        }
        return "multimodalPage";
    }

    @GetMapping("/getDeliveryPort")
    public String getDeliveryPort(Model model){

        model.addAttribute("navTabName","delivery");
        model.addAttribute("tabName","contracts");
        model.addAttribute("fragmentPathTabConfig","deliveryUA");
        model.addAttribute("fragmentPathOrderInfo", "ordersForDelivery");
        model.addAttribute("orderInfoList",orderInfoService.getOrdersByStatusOfOrderByDestination(StatusOfOrderInfo.MAIN, DeliveryDestinationType.PORT));
        model.addAttribute("breedOfTreeList",breedOfTreeService.findAll());
        model.addAttribute("userCompanyList",userCompanyService.getListOfAllUsersROLE());
        model.addAttribute("contrAgentProviderList",contrAgentService.getListByType(ContrAgentType.PROVIDER));


        UserCompany userCompany = userCompanyService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (userCompany!=null){
            model.addAttribute("userCompanyName",userCompany);
            model.addAttribute("userId",userCompany.getId());
        }
        return "multimodalPage";
    }

    @GetMapping("/getDeliveryTrucksByContract-{id}")
    public String getDeliveryTrucksByContract(@PathVariable("id")int contractId,Model model){
        OrderInfo mainOrder = orderInfoService.findById(contractId);
        BreedOfTree breedOfTree = mainOrder.getBreedOfTree();
        List<DeliveryDocumentation> docList = deliveryDocumentationService.getListByDistributionContractsId(orderInfoService.findDistributionId(contractId));

        model.addAttribute("navTabName","delivery");
        model.addAttribute("tabName","details");
        model.addAttribute("fragmentPathTabConfig","deliveryUA");
        model.addAttribute("deliveryDocumentations",docList);
        model.addAttribute("breedOfTreeList",breedOfTreeService.findAll());
        UserCompany userCompany = userCompanyService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (userCompany!=null){
            model.addAttribute("userCompanyName",userCompany);
            model.addAttribute("userId",userCompany.getId());
        }
        if (breedOfTree.getId()!=2){
            model.addAttribute("fragmentPathDeliveryDoc","deliveryInfo");
        }else{
            model.addAttribute("fragmentPathDeliveryDoc","deliveryInfoOak");
        }
        if(orderInfoService.findById(contractId).getDestinationType()==DeliveryDestinationType.COUNTRY){
            model.addAttribute("urlContractPage","/multimodal/getDeliveryInUkraine");
        }
        if(orderInfoService.findById(contractId).getDestinationType()==DeliveryDestinationType.PORT){
            model.addAttribute("urlContractPage","/multimodal/getDeliveryPort");
        }

        model.addAttribute("urlEditDriver","/multimodal/editDeliveryDocumentation-"+contractId);
        model.addAttribute("urlEditPackage","/multimodal/editPackageProduct-"+contractId);
        model.addAttribute("urlAddPackage","/multimodal/addPackageProduct-"+contractId);
        model.addAttribute("urlDeletePackage","/multimodal/deletePackageProduct-"+contractId);

        model.addAttribute("urlEditPackageDescriptionOak","/multimodal/editPackageDescriptionOak-"+contractId);
        model.addAttribute("urlAddPackageDescriptionOak","/multimodal/addPackageDescriptionOak-"+contractId);
        model.addAttribute("urlDeleteDescriptionOak","/multimodal/deletePackageDescriptionOak-"+contractId);


        return "multimodalPage";
    }

//    Work with Delivery doc

    @PostMapping("/editDeliveryDocumentation-{contractId}")
    public String editDeliveryDocumentation(@PathVariable("contractId")int contractId,DeliveryDocumentation documentation){
        deliveryDocumentationService.editDeliveryDoc(documentation);
        return "redirect:/multimodal/getDeliveryTrucksByContract-"+contractId;
    }

    @PostMapping("/editPackageProduct-{contractId}")
    public String editPackageProduct(@PathVariable("contractId")int contractId, PackagedProduct product){
        packagedProductService.editPackageProduct(product);
        return "redirect:/multimodal/getDeliveryTrucksByContract-"+contractId;
    }
    @PostMapping("/addPackageProduct-{contractId}")
    public String addPackageProduct(@PathVariable("contractId")int contractId,String docId,PackagedProduct product){
        deliveryDocumentationService.addPackageProductToDeliveryDoc(docId,product);
        return "redirect:/multimodal/getDeliveryTrucksByContract-"+contractId;
    }
    @PostMapping("/deletePackageProduct-{contractId}")
    public String deletePackageProduct(@PathVariable("contractId")int contractId,String id, String deliveryId){
        deliveryDocumentationService.deletePackage(id,deliveryId);
        return "redirect:/multimodal/getDeliveryTrucksByContract-"+contractId;
    }

    //    Work with Delivery doc OAK

    @PostMapping("/editPackageDescriptionOak-{contractId}")
    public String editPackageDescriptionOak(@PathVariable("contractId")int contractId,
                                            String rowId,String packageId, String width, String count) {

        deskOakService.editDescription(rowId,width,count);
        packagedProductService.countExtentOak(packagedProductService.findById(Integer.parseInt(packageId)));
        return "redirect:/multimodal/getDeliveryTrucksByContract-"+contractId;
    }

    @PostMapping("/addPackageDescriptionOak-{contractId}")
    public String addPackageDescriptionOak(@PathVariable("contractId")int contractId,
                                           String packageId, String width, String count) {
        packagedProductService.addDescriptionOak(packageId,width,count);
        packagedProductService.countExtentOak(packagedProductService.findById(Integer.parseInt(packageId)));
        return "redirect:/multimodal/getDeliveryTrucksByContract-"+contractId;
    }

    @PostMapping("/deletePackageDescriptionOak-{contractId}")
    public String deletePackageDescriptionOak(@PathVariable("contractId")int contractId,
                                              String packageId, String id) {
        packagedProductService.deleteDescriptionOak(packageId,id);
        packagedProductService.countExtentOak(packagedProductService.findById(Integer.parseInt(packageId)));
        return "redirect:/multimodal/getDeliveryTrucksByContract-"+contractId;
    }


    @PostMapping("/importOakXLS")
    public String importOakXLS(@RequestParam MultipartFile fileXLS, String contractId) throws IOException, InvalidFormatException {
        ImportOakOrderDataFromXLS dataFromXLS = new ImportOakOrderDataFromXLS(fileXLS);
        System.out.println("contractId "+ contractId);
        dataFromXLS.importData();

//        OrderInfo orderInfo = orderInfoService.findByCodeOfOrder(contractId);

//        deliveryDocumentationService.checkInfoFromImport(dataFromXLS.importData(),orderInfo);
        return "redirect:/multimodal/getDeliveryPort";
    }
}