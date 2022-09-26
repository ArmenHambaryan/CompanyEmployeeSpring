package com.example.companyemployeespring.repository;

import com.example.companyemployeespring.entity.Companies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompaniesRepository extends JpaRepository<Companies,Integer> {
}
