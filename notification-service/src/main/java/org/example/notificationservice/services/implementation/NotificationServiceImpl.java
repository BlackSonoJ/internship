package org.example.notificationservice.services.implementation;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.example.notificationservice.api.dto.MessageDto;
import org.example.notificationservice.common.enums.EmailResource;
import org.example.notificationservice.common.enums.Language;
import org.example.notificationservice.services.NotificationService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender mailSender;
    private final EmailTemplateService emailTemplateService;

    @Override
    public void sendMessage(MessageDto messageDto) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

        helper.setFrom("BlackSon.1@yandex.ru");
        helper.setTo(messageDto.email());
        helper.setSubject(
                emailTemplateService.getBody(
                        Language.RU,
                        messageDto.operation(),
                        EmailResource.SUBJECT
                )
        );
        helper.setText(
                emailTemplateService.getBody(
                        Language.RU,
                        messageDto.operation(),
                        EmailResource.TEXT
                ), true);

        mailSender.send(message);
    }
}