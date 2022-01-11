package com.ap.iamstu.application.service;


import com.ap.iamstu.domain.User;

import javax.mail.MessagingException;

public interface SendEmailService {

    void send(User user, String templateName, String titleKey, String token) throws MessagingException;
}
