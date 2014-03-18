package com.rquinn.darts.action;


import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.mysql.jdbc.log.Slf4JLogger;
import com.opensymphony.xwork2.ActionSupport;
import com.rquinn.darts.PageData;
import com.rquinn.darts.UserService;

import com.rquinn.darts.model.User;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.web.config.WebIniSecurityManagerFactory;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Properties;

public class UserAction extends BaseAction {

    private static final Logger slf4jLogger = LoggerFactory.getLogger(UserAction.class);
    private static final String SMTP_HOST_NAME = System.getenv("SENDGRID_HOST_NAME");
    private static final String SMTP_PORT = "587"; //System.getenv("SENDGRID_PORT");
    private static final String SMTP_AUTH_USER = System.getenv("SENDGRID_USERNAME");
    private static final String SMTP_AUTH_PWD  = System.getenv("SENDGRID_PASSWORD");

    private String url;

    public String getUrl() {
        return url;
    }

    public String processLogin() {    	
        Factory<SecurityManager> factory = new WebIniSecurityManagerFactory();
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated()) {
            url = "/user/ryan";
            return "redirect";
        } else {
            slf4jLogger.debug("failed login for user: " + currentUser);
            return ERROR;
        }
    }

    public String processSignup() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String name = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(password)) {
            PasswordService passwordService = new DefaultPasswordService();
            String encryptedPassword = passwordService.encryptPassword(password);


            try {
                UserService.insertUser(name, encryptedPassword, email);
            } catch (MySQLIntegrityConstraintViolationException exception) {
                slf4jLogger.error("mysql problem creating new user: " + exception);
                return ERROR;
            } catch (PersistenceException exception) {
                slf4jLogger.error("User tried to create account, but name was already taken: " + name + ", exception info: " + exception);
                request.setAttribute("errorMessage", "This username is already taken: " + name);
                return ERROR;
            }

            // is this really the right way to do the authentication?  Seems arbitrary...
            Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
            SecurityManager securityManager = factory.getInstance();
            SecurityUtils.setSecurityManager(securityManager);
            SecurityUtils.getSubject().login(new UsernamePasswordToken(name, password));

            return SUCCESS;
        }

        return ERROR;

    }


    public String sendResetEmail() {

        slf4jLogger.debug("host/auth/pwd info: " + SMTP_HOST_NAME + ", " + SMTP_AUTH_USER + ", " + SMTP_AUTH_PWD + ", " + SMTP_PORT);

        HttpServletRequest req = ServletActionContext.getRequest();

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
            return "captchaFailure";
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

                Multipart multipart = new MimeMultipart("alternative");

                String reset_token = RandomStringUtils.randomAlphanumeric(32);
                PageData pageData = new PageData();


//              // set reset_token and reset_timestamp now

                BodyPart part1 = new MimeBodyPart();
                BodyPart part2 = new MimeBodyPart();

                String protocol = pageData.getHttpsToggle();
                String domain = pageData.getDomain();
                if (UserService.isValidEmail(email)) {
                    try {
                        UserService.insertResetToken(reset_token, email);
                    } catch (Exception e) {
                        slf4jLogger.debug("problem inserting reset token", e);
                    }
                    part1.setText("Go to this url to reset your password.  " + protocol + "://" + domain + "/reset?rid=" + reset_token);
                    part2.setContent("Click here to reset your password: <a href='" + protocol + "://" + domain + "/reset?rid=" + reset_token + "'>Reset password</a><br/>" +
                            "If that link does not work, paste this url into your browser: " + protocol + "://" + domain + "/reset?rid=" + reset_token, "text/html");
                } else {
                    part1.setText("You (or someone else) attempted to reset your password for www.trebleBull.com.  Unfortunately, this email address does not exist in our system.");
                    part2.setContent("You (or someone else) attempted to reset your password for <a href='" + protocol + "://" + domain + "'>www.trebleBull.com</a>.  " +
                            "Unfortunately, this email address does not exist in our system.", "text/html");
                }

                multipart.addBodyPart(part1);
                multipart.addBodyPart(part2);

                message.setContent(multipart);

                //message.setText(text);
                message.setFrom(new InternetAddress("admin@treblebull.com"));
                message.setSubject("TrebleBull password reset instructions");
                message.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(email));

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


    public String resetPassword() {
        // we have rid
        HttpServletRequest req = ServletActionContext.getRequest();
        String rid = req.getParameter("rid");
        String email = req.getParameter("emailAddress");
        User user = UserService.getUserDetailsByToken(rid, email);
        // what we want - the email
        if (user != null && UserService.isPasswordResetTokenValid(rid)) {
            String pw = req.getParameter("password");
            PasswordService passwordService = new DefaultPasswordService();
            String encryptedPassword = passwordService.encryptPassword(pw);
            UserService.resetPassword(email, encryptedPassword);
            return "success";
        } else {
            req.setAttribute("pwdNotResetMsg", "Your token is expired, request a new one <a href='/forgotPwd'>here</a>.");
            return "failure";
        }
    }

    public String changePassword() {
        HttpServletRequest req = ServletActionContext.getRequest();
        String newPassword = req.getParameter("newPassword");
        String newPasswordConfirm = req.getParameter("passwordConfirm");
        if (StringUtils.isNotBlank(newPassword) && StringUtils.equals(newPassword, newPasswordConfirm)) {
            PasswordService passwordService = new DefaultPasswordService();
            String encryptedPassword = passwordService.encryptPassword(newPassword);
            Subject subject = SecurityUtils.getSubject();
            slf4jLogger.debug("principal", subject.getPrincipal().toString());
            UserService.resetPasswordByUsername(subject.getPrincipal().toString(), encryptedPassword);
            return "success";
        } else {
            req.setAttribute("errorMessage", "There was a problem resetting your password, please try again.");
            return "failure";
        }
    }

    public String showResetForm() {
        // if the reset timestamp is valid, show the form, if not, show the appropriate error message
        HttpServletRequest req = ServletActionContext.getRequest();
        String rid = req.getParameter("rid");
        if (UserService.isPasswordResetTokenValid(rid)) {
            req.setAttribute("rid", rid);
            return "success";
        };
        req.setAttribute("pwdNotResetMsg", "Your token is expired, request a new one <a href='/forgotPwd'>here</a>.");
        return "failure";
    }

    public String requestUsername() {
        HttpServletRequest req = ServletActionContext.getRequest();

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

                // mailSession.setDebug(true);
                Transport transport = mailSession.getTransport();

                MimeMessage message = new MimeMessage(mailSession);

                Multipart multipart = new MimeMultipart("alternative");

                PageData pageData = new PageData();

                BodyPart part1 = new MimeBodyPart();
                BodyPart part2 = new MimeBodyPart();

                String protocol = pageData.getHttpsToggle();
                String domain = pageData.getDomain();

                User user = UserService.getUserByEmail(email);
                if (user != null && StringUtils.isNotBlank(user.getUsername())) {
                    String userName = user.getUsername();
                    part1.setText("Here is your requested username: " + userName + ".  You can login here - "  + protocol + "://" + domain + "/login");
                    part2.setContent("Here is your requested username: " + userName + ".  You can login here - <a href='" + protocol + "://" + domain + "/login'>Login</a><br/>" +
                            "If that link does not work, paste this url into your browser: " + protocol + "://" + domain + "/login", "text/html");
                } else {
                    part1.setText("You (or someone else) requested the username associated with this email address.  Unfortunately, this email address does not exist in our system.");
                    part2.setContent("You (or someone else) requested the username associated with this email address for <a href='" + protocol + "://" + domain + "'>www.trebleBull.com</a>.  " +
                            "Unfortunately, this email address does not exist in our system.", "text/html");
                }

                multipart.addBodyPart(part1);
                multipart.addBodyPart(part2);

                message.setContent(multipart);

                message.setFrom(new InternetAddress("admin@treblebull.com"));
                message.setSubject("TrebleBull username reminder");
                message.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(email));

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

}