package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.entity.Companies;
import com.example.companyemployeespring.entity.Employees;
import com.example.companyemployeespring.repository.CompaniesRepository;
import com.example.companyemployeespring.repository.EmployeesRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.Optional;

@Controller
public class EmployeesController {
    @Autowired
    EmployeesRepository employeesRepository;
    @Autowired
    CompaniesRepository companiesRepository;

    @Value("${company.emplyee.profilePic.folder}")
    private String folderPath;

    @GetMapping("/employees")
    public String employees(ModelMap modelMap) {
        List<Employees> employees = employeesRepository.findAll();
        modelMap.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping("/employees/add")
    public String employeesAddPage(ModelMap modelMap) {
        List<Companies> companies = companiesRepository.findAll();
        modelMap.addAttribute("companies", companies);
        return "employeesAdd";
    }

    @PostMapping("/employees/add")
    public String employeesAdd(@ModelAttribute Employees employees,
                               @RequestParam("employeeImage") MultipartFile file) throws IOException {

        if (!file.isEmpty() && file.getSize() > 0) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File file1 = new File(folderPath + File.separator + fileName);
            file.transferTo(file1);
            employees.setProfilePic(fileName);

        }
        Companies company = employees.getCompany();
        company.setSize(company.getSize() + 1);
        companiesRepository.save(company);
        employeesRepository.save(employees);

        return "redirect:/employees";
    }

    @GetMapping(value = "/employees/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("fileName") String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(folderPath + File.separator + fileName);
        return IOUtils.toByteArray(inputStream);
    }

    @GetMapping("/employees/delete")
    public String delete(@RequestParam("id") int id) {

        Optional<Employees> byId = employeesRepository.findById(id);
        Employees employees = byId.get();
        Companies company = employees.getCompany();
        company.setSize(company.getSize() - 1);
        companiesRepository.save(company);

        employeesRepository.deleteById(id);
        return "redirect:/employees";

    }
}
