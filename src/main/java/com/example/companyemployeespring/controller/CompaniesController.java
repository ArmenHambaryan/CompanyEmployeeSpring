package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.entity.Companies;
import com.example.companyemployeespring.repository.CompaniesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class CompaniesController {
    @Autowired
    CompaniesRepository companiesRepository;

    @GetMapping("/companies")
    public String companies(ModelMap modelMap) {
        List<Companies> companies = companiesRepository.findAll();
        modelMap.addAttribute("companies", companies);
        return "companies";
    }

    @GetMapping("/companies/add")
    public String companiesAddPage() {
        return "companiesAdd";
    }

    @PostMapping("/companies/add")
    public String companiesAdd(@ModelAttribute Companies companies) {
        companiesRepository.save(companies);

        return "redirect:/companies";
    }

    @GetMapping("/companies/delete")
    public String delete(@RequestParam("id") int id) {
        companiesRepository.deleteById(id);
        return "companies";

    }
}
