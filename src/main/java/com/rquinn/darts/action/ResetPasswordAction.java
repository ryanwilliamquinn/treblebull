package com.rquinn.darts.action;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: rquinn
 * Date: 10/4/13
 * Time: 9:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResetPasswordAction {

    private static final Logger slf4jLogger = LoggerFactory.getLogger(ResetPasswordAction.class);

    private static final String SMTP_HOST_NAME = System.getenv("SENDGRID_HOST_NAME");
    private static final String SMTP_PORT = "587"; //System.getenv("SENDGRID_PORT");
    private static final String SMTP_AUTH_USER = System.getenv("SENDGRID_USERNAME");
    private static final String SMTP_AUTH_PWD  = System.getenv("SENDGRID_PASSWORD");


    public String resetPwd() {

        slf4jLogger.debug("host/auth/pwd info: " + SMTP_HOST_NAME + ", " + SMTP_AUTH_USER + ", " + SMTP_AUTH_PWD + ", " + SMTP_PORT);

        HttpServletRequest req = ServletActionContext.getRequest();
        HttpServletResponse res = ServletActionContext.getResponse();

        String articleId = req.getParameter("articleId");
        String challenge = req.getParameter("recaptcha_challenge_field");
        String answer = req.getParameter("recaptcha_response_field");
        String email = req.getParameter("usrEmail");

        slf4jLogger.debug("articleid: " + articleId + ", challenge: " + challenge + ", answer: " + answer + ", email: " + email);

        String remoteAddr = req.getRemoteAddr();
        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();

        reCaptcha.setPrivateKey("6LcGY-gSAAAAACFtP57eO62Zh3cDWNZuSBnA7-wT");

        ReCaptchaResponse reCaptchaResponse =
                reCaptcha.checkAnswer(remoteAddr, challenge, answer);

        if (!reCaptchaResponse.isValid()) {
            // recaptcha failed
            req.setAttribute("usrEmail", email);
            req.setAttribute("captchaFailure", true);
            return "failure";
        } else {
            try {
                Properties props = new Properties();
                props.put("mail.transport.protocol", "smtp");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", SMTP_HOST_NAME);
                props.put("mail.smtp.port", SMTP_PORT);

                Session mailSession = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(SMTP_AUTH_USER, SMTP_AUTH_PWD);
                    }
                });
                // uncomment for debugging infos to stdout
                // mailSession.setDebug(true);
                Transport transport = mailSession.getTransport();

                MimeMessage message = new MimeMessage(mailSession);

                //Multipart multipart = new MimeMultipart("alternative");

                BodyPart part1 = new MimeBodyPart();
                part1.setText("This is multipart mail and u read part1......");

                String pw = RandomStringUtils.randomAlphanumeric(16);

                String text = "Your new password is: " + pw;

                BodyPart part2 = new MimeBodyPart();
                //part2.setContent("<b>This is multipart mail and u read part2......</b>", "text/html");

                //multipart.addBodyPart(part1);
                //multipart.addBodyPart(part2);

                message.setText(text);
                message.setFrom(new InternetAddress("admin@treblebull.com"));
                message.setSubject("This is the subject");
                message.addRecipient(Message.RecipientType.TO,
                        new InternetAddress("ryanwilliamquinn@gmail.com"));

                transport.connect();
                transport.sendMessage(message,
                        message.getRecipients(Message.RecipientType.TO));
                transport.close();
            } catch (Exception e) {
                slf4jLogger.error("issue sending mail", e);
            }

        }

        return "success";
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            String username = SMTP_AUTH_USER;
            String password = SMTP_AUTH_PWD;
            return new PasswordAuthentication(username, password);
        }
    }
}
