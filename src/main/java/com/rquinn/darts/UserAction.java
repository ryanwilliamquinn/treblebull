package com.rquinn.darts;


import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.web.config.WebIniSecurityManagerFactory;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class UserAction extends ActionSupport {

    private static final Logger slf4jLogger = LoggerFactory.getLogger(UserAction.class);

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

        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(password)) {
            PasswordService passwordService = new DefaultPasswordService();
            String encryptedPassword = passwordService.encryptPassword(password);

            UserService userService = new UserService();
            try {
                userService.insertUser(name, encryptedPassword);
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

            return SUCCESS;
        }

        return ERROR;

    }

}