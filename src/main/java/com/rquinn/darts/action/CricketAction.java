package com.rquinn.darts.action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rquinn.darts.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: rquinn
 * Date: 2/3/13
 * Time: 12:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class CricketAction extends PracticeAction {

    private static final Logger slf4jLogger = LoggerFactory.getLogger(CricketAction.class);

    public String insert() throws Exception {

        SqlSessionFactory sqlSessionFactory = getSqlSession();
        SqlSession session = sqlSessionFactory.openSession();

        HttpServletRequest request = ServletActionContext.getRequest();
        String url = request.getRequestURI();
        String type = StringUtils.substringAfter(url, "/data/");
        PracticeType practiceType = PracticeType.getPracticeTypeForString(type);

        if (practiceType == null || practiceType != PracticeType.CRICKET) {
            slf4jLogger.error("practice type is not recognized or is not cricket: " + type + ", redirecting back to the practie page");
            // maybe return a json error message to the front end?
            return NONE;
        }

        slf4jLogger.debug("request type: " + type);
        slf4jLogger.debug("request practice type: " + practiceType.getValue());

        ThreeDartResult cricketResult = null;
        try {
            BufferedReader is = new BufferedReader(new InputStreamReader(request.getInputStream()));
            Type listType = new TypeToken<ArrayList<ThreeDartRoundResult>>() {}.getType();
            ArrayList<ThreeDartRoundResult> roundResultList = new Gson().fromJson(is, listType);

            slf4jLogger.debug("practiceType: " + practiceType.getValue());


            cricketResult = new ThreeDartResult(roundResultList, practiceType);
            Subject currentUser = SecurityUtils.getSubject();
            cricketResult.setUsername(currentUser.getPrincipal().toString());
            slf4jLogger.debug("cricket result: " + cricketResult);
            DartsResultService dartsResultService = new DartsResultService();
            cricketResult.initializeDates();
            dartsResultService.insertCricketGame(cricketResult);
        } catch (Exception e) {
            slf4jLogger.error("Error inserting data: " + e);
        }  finally {
            session.close();
        }

        if (cricketResult != null) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            String json = gson.toJson(cricketResult);
            System.out.println(json);
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setHeader("Content-type", "application/json");
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        }
        return NONE;
    }

    public String load() throws Exception {
        slf4jLogger.debug("Load us some twenties!");

        Subject currentUser = SecurityUtils.getSubject();

        SqlSessionFactory sqlSessionFactory = getSqlSession();

        DartsResultResponse dartsResultResponse = null;

        HttpServletRequest request = ServletActionContext.getRequest();
        String url = request.getRequestURI();
        String type = StringUtils.substringAfter(url, "/data/load");
        PracticeType practiceType = PracticeType.getPracticeTypeForString(type);
        slf4jLogger.debug("PracticeType: " + practiceType.getValue());

        if (practiceType == null) {
            slf4jLogger.error("practice type is not recognized: " + type + ", returning no data ");
            // maybe return a json error message to the front end?
            return NONE;
        }

        SqlSession session = sqlSessionFactory.openSession();
        try {
            DartsResultService dartsResultService = new DartsResultService();
            dartsResultResponse = dartsResultService.getTenResults(currentUser.getPrincipal().toString(), practiceType);
        } finally {
            session.close();
        }

        if (dartsResultResponse != null && dartsResultResponse.getDartsResults() != null && dartsResultResponse.getDartsResults().size() > 0) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            String json = gson.toJson(dartsResultResponse);
            //slf4jLogger.info("json response: " + json);
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setHeader("Content-type", "application/json");
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        }
        return NONE;
    }

    public String loadAll() throws Exception {
        slf4jLogger.debug("Load us all the targets!");

        Subject currentUser = SecurityUtils.getSubject();

        SqlSessionFactory sqlSessionFactory = getSqlSession();

        HttpServletRequest request = ServletActionContext.getRequest();
        String url = request.getRequestURI();
        String type = StringUtils.substringAfter(url, "/data/loadAll");
        PracticeType practiceType = PracticeType.getPracticeTypeForString(type);

        if (practiceType == null) {
            slf4jLogger.error("practice type is not recognized: " + type + ", redirecting back to the practie page");
            // maybe return a json error message to the front end?
            return NONE;
        }

        DartsResultResponse dartsResultResponse = null;

        SqlSession session = sqlSessionFactory.openSession();
        try {
            DartsResultService dartsResultService = new DartsResultService();
            dartsResultResponse = dartsResultService.getAllResults(currentUser.getPrincipal().toString(), practiceType);
        } finally {
            session.close();
        }

        if (dartsResultResponse != null && dartsResultResponse.getDartsResults() != null && dartsResultResponse.getDartsResults().size() > 0) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            String json = gson.toJson(dartsResultResponse);
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setHeader("Content-type", "application/json");
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        }
        return NONE;
    }
}
