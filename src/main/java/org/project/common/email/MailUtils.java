package org.project.common.email;

import org.project.entity.MailEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class MailUtils {

    @Resource
    private JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String from;

    @Async
    public void send(MailEntity entity, String... attachments) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(entity.getSubject());
            helper.setText(entity.getText(), true);
            helper.setTo(entity.getTo());
            helper.setFrom(from);

            for (int i = 0; i < attachments.length; i++) {
                File file = new File(attachments[i]);
                helper.addAttachment(file.getName(), new FileSystemResource(file));
            }
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
