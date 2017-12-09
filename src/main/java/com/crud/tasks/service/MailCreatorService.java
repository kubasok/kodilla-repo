package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyConfig;
import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.controller.TrelloController;
import com.crud.tasks.domain.TrelloBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private CompanyConfig companyConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    @Autowired
    private TrelloController controller;

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/crud");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("goodbye_message", "Goodbye!");
        context.setVariable("preview_message", "New card created...");
        context.setVariable("company_details", companyConfig.getCompanyName() + "\n" + companyConfig.getCompanyMail()
                + "\n" + "Phone: " + companyConfig.getCompanyPhone() + "\n" + companyConfig.getCompanyGoal());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", true);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildTrelloDailyEmail(String message) throws TaskNotFoundException {

        List<TrelloBoardDto> trelloBoardDtoList = controller.getTrelloBoards();

        Context context = new Context();
        context.setVariable("boards_number", trelloBoardDtoList.size());
        context.setVariable("tasks_url", "http://localhost:8888/crud");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("goodbye_message", "Goodbye!");
        context.setVariable("preview_message", "Number of emails as at " + LocalDateTime.now());
        context.setVariable("company_details", companyConfig.getCompanyName() + "\n" + companyConfig.getCompanyMail()
                + "\n" + "Phone: " + companyConfig.getCompanyPhone() + "\n" + companyConfig.getCompanyGoal());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", true);
        context.setVariable("admin_config", adminConfig);
        return templateEngine.process("mail/daily-trello-email", context);
    }

}
