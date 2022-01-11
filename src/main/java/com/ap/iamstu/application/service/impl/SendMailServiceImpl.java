package com.ap.iamstu.application.service.impl;

import com.ap.iamstu.application.adaptor.MailService;
import com.ap.iamstu.application.service.SendEmailService;
import com.ap.iamstu.domain.User;
import com.ap.iamstu.infrastructure.support.util.Const;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import java.util.Locale;

@Service
@Slf4j
public class SendMailServiceImpl implements SendEmailService {
    private static final String USER = "user";
    private static final String TOKEN = "token";
    private static final String LINK = "redirectLink";
    private final MailService mailService;
    private final SpringTemplateEngine templateEngine;
    private final MessageSource messageSource;
    private final String redirectLink;

    public SendMailServiceImpl(MailService mailService,
                               SpringTemplateEngine templateEngine,
                               @Value("${app.iam.domain}") String domain,
                               MessageSource messageSource) {
        this.mailService = mailService;
        this.templateEngine = templateEngine;
        this.messageSource = messageSource;
        if (StringUtils.hasText(domain)) {
            redirectLink = String.format("%s%s", domain, Const.REDIRECT_LINK_CHANGE_PASSWORD);
        } else {
            redirectLink = String.format("%s%s", Const.DEFAULT_DOMAIN, Const.REDIRECT_LINK_CHANGE_PASSWORD);
        }
    }

    @Override
    @Async
    public void send(User user, String templateName, String titleKey, String token) throws MessagingException {
        if (user.getEmail() == null) {
            log.debug("Email doesn't exist for user '{}'", user.getUsername());
            return;
        }
        Locale locale = Locale.forLanguageTag("vi");
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(TOKEN, token);
        context.setVariable(LINK, redirectLink);
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        mailService.sendHtmlMail(user.getEmail(), subject, content);
    }
}
