package com.crud.tasks.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class CompanyConfig {
    @Value("${info.company.email}")
    private String companyMail;

    @Value("${info.company.name}")
    private String companyName;

    @Value("${info.company.phone}")
    private String companyPhone;

    @Value("${info.company.goal}")
    private String companyGoal;
}
