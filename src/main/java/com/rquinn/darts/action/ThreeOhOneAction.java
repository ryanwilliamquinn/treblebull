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
* Date: 12/31/12
* Time: 9:52 PM
* To change this template use File | Settings | File Templates.
*/
public class ThreeOhOneAction extends PracticeAction {

  private static final Logger slf4jLogger = LoggerFactory.getLogger(ThreeOhOneAction.class);

  // test button-insert for darts in this game
  /*
      what will go in the database?  all the rounds, with individual darts (like cricket rounds i guess)
      score will be number of rounds at first, eventually number of darts
      would be nice to also show the double out, keep track of stats like average double out, highest double out etc
   */

  public String insert() throws Exception {

    HttpServletRequest request = ServletActionContext.getRequest();
    String url = request.getRequestURI();
    String type = StringUtils.substringAfter(url, "/data/");
    PracticeType practiceType = PracticeType.getPracticeTypeForString(type);

    if (practiceType == null || practiceType != PracticeType.THREE_OH_ONE) {
      slf4jLogger.error("practice type is not recognized or is not 301: " + practiceType.getValue() + ", redirecting back to the practice page");
      // maybe return a json error message to the front end?
      return NONE;
    }

    slf4jLogger.debug("request type: " + type);
    slf4jLogger.debug("request practice type: " + practiceType.getValue());

    ThreeOhOneResult threeOhOneResult = null;

    SqlSessionFactory sqlSessionFactory = getSqlSession();
    SqlSession session = sqlSessionFactory.openSession();
    try {
      BufferedReader is = new BufferedReader(new InputStreamReader(request.getInputStream()));
      Type listType = new TypeToken<ArrayList<ThreeDartRoundResult>>() {}.getType();
      ArrayList<ThreeDartRoundResult> roundResultList = new Gson().fromJson(is, listType);

      slf4jLogger.debug("practiceType: " + practiceType.getValue());


      threeOhOneResult = new ThreeOhOneResult(roundResultList, practiceType);
      Subject currentUser = SecurityUtils.getSubject();
      threeOhOneResult.setUsername(currentUser.getPrincipal().toString());
      slf4jLogger.debug("301 result: " + threeOhOneResult);
      DartsResultService dartsResultService = new DartsResultService();
      dartsResultService.insert301Game(threeOhOneResult);
      threeOhOneResult.getDateTimeManagement().initializeDates();
    } catch (Exception e) {
        slf4jLogger.error("Error inserting data: " + e);
    }  finally {
        session.close();
    }

    if (threeOhOneResult != null) {
      Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
      String json = gson.toJson(threeOhOneResult);
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
  slf4jLogger.debug("loading 301 history");

  Subject currentUser = SecurityUtils.getSubject();

  SqlSessionFactory sqlSessionFactory = getSqlSession();

  DartsResultResponse dartsResultResponse = null;

  HttpServletRequest request = ServletActionContext.getRequest();
  String url = request.getRequestURI();
  String type = StringUtils.substringAfter(url, "/data/load");
  PracticeType practiceType = PracticeType.getPracticeTypeForString(type);
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


}
