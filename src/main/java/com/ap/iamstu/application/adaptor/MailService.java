package com.ap.iamstu.application.adaptor;

import javax.mail.MessagingException;
import java.util.List;

public interface MailService {

    void sendSimpleMail(String to, String subject, String content, String... cc);

    void sendHtmlMail(String to, String subject, String content, String... cc) throws MessagingException;

    void sendAttachmentsMail(String to, String subject, String content, String filePath, String... cc) throws MessagingException;

    void sendAttachmentsMail(List<String> to, String subject, String content, List<String> filePaths, List<String> fileNames, String... cc) throws MessagingException;

    void sendResourceMail(String to, String subject, String content, String rscPath, String rscId, String... cc) throws MessagingException;
}