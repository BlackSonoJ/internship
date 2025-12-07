package org.example.notificationservice.services.implementation;

import lombok.RequiredArgsConstructor;
import org.example.notificationservice.common.enums.EmailResource;
import org.example.notificationservice.common.enums.Language;
import org.example.notificationservice.common.enums.Operation;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class EmailTemplateService {
    private final TemplateEngine templateEngine;

    public String getBody(Language language,
                          Operation operation,
                          EmailResource emailResource) {
        String langFolder = language.name().toLowerCase();
        String resourceFolder = emailResource.name().toLowerCase();
        String operationFolder = operation.name().toLowerCase();

        String templatePath = String.format("email/%s/%s/%s/index", langFolder, resourceFolder, operationFolder);

        Context context = new Context(new Locale(langFolder));

        return templateEngine.process(templatePath, context).trim();
    }
}
