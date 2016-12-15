package com.rquinn.darts.action;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.opensymphony.xwork2.ActionSupport;
import com.rquinn.darts.PageData;
import com.rquinn.darts.PracticeType;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.protocol.HTTP;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: rquinn
 * Date: 12/24/12
 * Time: 1:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseAction extends ActionSupport {

    private String practiceTemplate;

    public String getPracticeTemplate() {
        return practiceTemplate;
    }
    
    public void initializePageData(HttpServletRequest request) {
    	request.setAttribute("pageData", new PageData());
    }

    private static final Logger slf4jLogger = LoggerFactory.getLogger(BaseAction.class);

    protected SqlSessionFactory getSqlSession()  {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            slf4jLogger.error("problem getting sql session: " + e);
        }
        return new SqlSessionFactoryBuilder().build(inputStream, System.getenv("MYBATIS_ENVIRONMENT"));
    }

    public String practiceTemplate() throws Exception {
        PracticeType practiceType = getPracticeTypeFromString("practice/");

        practiceTemplate = practiceType.getTemplate();

        if (practiceType == null) {
            // this was logged in getpracticetype. so just do what you're gonna do here...
            return ERROR;
        }

        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("practiceMode", practiceType.getValue());
        return SUCCESS;
    }

    public PracticeType getPracticeTypeFromString(String substringPath) {
        HttpServletRequest request = ServletActionContext.getRequest();
        String url = request.getRequestURI();
        String type = StringUtils.substringAfter(url, substringPath);

        PracticeType practiceType = PracticeType.getPracticeTypeForString(type);
        if (practiceType == null) {
            slf4jLogger.error("practice type is not recognized: " + type + ", redirecting back to the practie page");
        }

        return practiceType;
    }

    protected String getRemoteAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    protected Boolean isCaptchaValid(HttpServletRequest request) {
        String googleRecaptchaResponse = request.getParameter("g-recaptcha-response");

        HttpResponse<JsonNode> jsonResponse = null;
        try {
            slf4jLogger.debug("data sent to google: " + getRemoteAddress(request));
            jsonResponse = Unirest.post("https://www.google.com/recaptcha/api/siteverify")
                    .header("accept", "application/json")
                    .field("secret", "6LcGY-gSAAAAACFtP57eO62Zh3cDWNZuSBnA7-wT")
                    .field("response", googleRecaptchaResponse)
                    .field("remoteip", getRemoteAddress(request))
                    .asJson();
        } catch (UnirestException unirestException) {
            slf4jLogger.error("Error verifying captcha: " + unirestException);
        }

        JSONObject recaptchaVerification = jsonResponse.getBody().getObject();
        return recaptchaVerification.getBoolean("success");
    }

}
