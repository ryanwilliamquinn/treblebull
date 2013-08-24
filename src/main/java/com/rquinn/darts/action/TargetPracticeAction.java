package com.rquinn.darts.action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rquinn.darts.*;
import com.rquinn.darts.model.Dart;
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
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: rquinn
 * Date: 12/27/12
 * Time: 10:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class TargetPracticeAction extends PracticeAction {

    private static final Logger slf4jLogger = LoggerFactory.getLogger(TargetPracticeAction.class);

    public String insert() throws Exception {

        SqlSessionFactory sqlSessionFactory = getSqlSession();
        SqlSession session = sqlSessionFactory.openSession();

        HttpServletRequest request = ServletActionContext.getRequest();
        String url = request.getRequestURI();
        String type = StringUtils.substringAfter(url, "/data/");
        TargetPracticeType practiceType = TargetPracticeType.getTargetPracticeTypeForString(type);

        if (practiceType == null) {
            slf4jLogger.error("practice type is not recognized: " + type + ", redirecting back to the practie page");
            // maybe return a json error message to the front end?
            return NONE;
        }

        slf4jLogger.debug("request type: " + type);

        DartResult simplePracticeResult = null;
        try {
            BufferedReader is = new BufferedReader(new InputStreamReader(request.getInputStream()));
            Type listType = new TypeToken<ArrayList<Dart>>() {}.getType();
            ArrayList<Dart> darts = new Gson().fromJson(is, listType);

            if (darts != null && darts.size() > 0) {
                slf4jLogger.debug(darts.get(0).toString());
            } else {
                slf4jLogger.debug("something is real broken");
            }
            slf4jLogger.debug("practiceType: " + practiceType.getValue());


            simplePracticeResult = new DartResult(darts, practiceType);
            Subject currentUser = SecurityUtils.getSubject();
            simplePracticeResult.setUsername(currentUser.getPrincipal().toString());
            slf4jLogger.debug("simple practice result: " + simplePracticeResult);
            DartsResultService dartsResultService = new DartsResultService();
            simplePracticeResult.getDateTimeManagement().initializeDates();
            dartsResultService.insertGame(simplePracticeResult);
        } catch (IOException e) {
            slf4jLogger.error("Error inserting data: " + e);
        }  finally {
            session.close();
        }

        if (simplePracticeResult != null) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            String json = gson.toJson(simplePracticeResult);
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
        TargetPracticeType practiceType = TargetPracticeType.getTargetPracticeTypeForString(type);
        slf4jLogger.debug("targetPracticeType: " + practiceType.getValue());

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
        TargetPracticeType practiceType = TargetPracticeType.getTargetPracticeTypeForString(type);

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

    public String getGameDetails() throws Exception {
        slf4jLogger.debug("Load game details");

        // might be nice to put a check in here for the gameid and be sure the current user owns the game -- especially if we let the user edit the rounds

        Subject currentUser = SecurityUtils.getSubject();
        try {
            SqlSessionFactory sqlSessionFactory = getSqlSession();

            HttpServletRequest request = ServletActionContext.getRequest();
            String url = request.getRequestURI();
            int gameId = -1;
            try {
                gameId = Integer.parseInt(StringUtils.substringAfter(url, "/data/gameDetails"));
            } catch (NumberFormatException e) {
                slf4jLogger.error("Problem with casting game id to integer: " + gameId);
            }
            slf4jLogger.debug("gameid: " + gameId);
            if (gameId < 0) {
                slf4jLogger.error("invalid game id: " + gameId);
                // maybe return a json error message to the front end?
                return NONE;
            }

            List<RoundResult> rounds = null;

            SqlSession session = sqlSessionFactory.openSession();
            try {
                DartsResultService dartsResultService = new DartsResultService();
                rounds = dartsResultService.getGameDetails(gameId);
            } finally {
                session.close();
            }

            if (rounds != null && rounds.size() > 0) {
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                String json = gson.toJson(rounds);
                // slf4jLogger.info("game details json: " + json);
                HttpServletResponse response = ServletActionContext.getResponse();
                response.setHeader("Content-type", "application/json");
                PrintWriter out = response.getWriter();
                out.print(json);
                out.flush();
            }
        } catch (Exception e) {
            slf4jLogger.error("errrror: " + e);
        }
        return NONE;
    }

    public String loadFree() throws Exception {
        slf4jLogger.debug("Load the last 100 darts from free practice");

        Subject currentUser = SecurityUtils.getSubject();

        SqlSessionFactory sqlSessionFactory = getSqlSession();

        DartsResultResponse dartsResultResponse = null;

        HttpServletRequest request = ServletActionContext.getRequest();
        PracticeType practiceType = PracticeType.FREE_TARGET;
        slf4jLogger.debug("targetPracticeType: " + practiceType.getValue());

        SqlSession session = sqlSessionFactory.openSession();
        List<FreeAverageData> averages = null;
        FreeResultResponse freeResultResponse = new FreeResultResponse();
        try {
            DartsResultService dartsResultService = new DartsResultService();
            /* will be interesting what we do with this.
            * options:
            *   get last 10 turns
            *   show averages for all targets (could be expensive?)
            *   show nothing
            */
            averages = dartsResultService.getFreeAverages(currentUser.getPrincipal().toString());
            freeResultResponse.setAggregateData(averages);

            /* okay, so we got all of the averages
                the next thing we need is to get the single target histories.  this means we need lists of say the last 100 darts for each target
                the sql will iterate through all the targets that exist (select uniq target types from results, make them into a list, then run
                a select for each of them to get the last 100 results by date)

                select * from free_targets where target
             */
            for (TargetPracticeType target : TargetPracticeType.values()) {
                List<SingleDartResult> singleDartsResult = dartsResultService.getFreeTargetHistory(currentUser.getPrincipal().toString(), target.getValue());
                if (singleDartsResult != null) {
                    FreeTargetHistory freeTargetHistory = new FreeTargetHistory();
                    freeTargetHistory.setSingleDartResults(singleDartsResult);
                    freeResultResponse.getFreeTargetHistories().add(freeTargetHistory);
                }
            }


        } finally {
            session.close();
        }

        if (averages != null && averages.size() > 0) {
            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(averages);
            slf4jLogger.info("json response: " + json);
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setHeader("Content-type", "application/json");
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        }
        return NONE;
    }

    public String insertFree() throws Exception {

        slf4jLogger.debug("are we even getting here??  wtf?");

        SqlSessionFactory sqlSessionFactory = getSqlSession();
        SqlSession session = sqlSessionFactory.openSession();

        HttpServletRequest request = ServletActionContext.getRequest();
        PracticeType practiceType = PracticeType.FREE_TARGET;

        if (practiceType == null) {
            slf4jLogger.error("practice type is not recognized: " + practiceType.getValue() + ", redirecting back to the practice page");
            // maybe return a json error message to the front end?
            return NONE;
        }

        slf4jLogger.debug("request type: " + practiceType.getValue());

        try {
            BufferedReader is = new BufferedReader(new InputStreamReader(request.getInputStream()));
            slf4jLogger.debug("before gson builder");
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(SingleDartResult.class, new SingleDartResultDeserializer())
                    .create();
            slf4jLogger.debug("before type token");
            Type listType = new TypeToken<ArrayList<SingleDartResult>>() {}.getType();
            slf4jLogger.debug("before fromJson");
            ArrayList<SingleDartResult> singleDartResults = gson.fromJson(is, listType);
            slf4jLogger.debug("whats going on here?");
            if (singleDartResults != null && singleDartResults.size() > 0) {
                slf4jLogger.debug(singleDartResults.get(0).toString());
            } else {
                slf4jLogger.debug("something is real broken");
            }
            slf4jLogger.debug("practiceType: " + practiceType.getValue());

            Subject currentUser = SecurityUtils.getSubject();
            DartsResultService dartsResultService = new DartsResultService();
            for (SingleDartResult dart : singleDartResults) {
                dart.setUsername(currentUser.getPrincipal().toString());
                dart.getDateTimeManagement().initializeDatesWithEpochTime();
                slf4jLogger.debug("display date time: " + dart.getDateTimeManagement().getDisplayDateTime());
            }
            dartsResultService.insertFreeDart(singleDartResults);

        } catch (IOException e) {
            slf4jLogger.debug("Error inserting data: " + e);
        }  finally {
            session.close();
        }

        return NONE;
    }
}
