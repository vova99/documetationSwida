package com.swida.documetation.controllers;

import com.swida.documetation.data.service.UserCompanyService;
import com.swida.documetation.data.service.storages.TreeStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SecurityController {
    @Autowired
    UserCompanyService userCompanyService;
    @Autowired
    TreeStorageService treeStorageService;


    @PostMapping("/enterRequest")
    public String loginPost(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int userID = userCompanyService.findByUsername(auth.getName()).getId();
        return "redirect:/fabric/index-"+userID;
    }

}
