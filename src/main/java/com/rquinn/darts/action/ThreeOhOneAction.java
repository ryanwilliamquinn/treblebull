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
      the 301 darts result is going to take the number you doubled in on, the number you doubled out on, and total number of darts you threw


      here is some mock data
      [{"type":"301","dart":19,"score":"19","dateMilliseconds":1377437199202,"turn":1},{"type":"301","dart":"d20","score":40,"dateMilliseconds":1377437200530,"turn":1},{"type":"301","dart":1,"score":"1","dateMilliseconds":1377437201648,"turn":1},{"type":"301","dart":20,"score":"20","dateMilliseconds":1377437202266,"turn":2},{"type":"301","dart":20,"score":"20","dateMilliseconds":1377437202429,"turn":2},{"type":"301","dart":20,"score":"20","dateMilliseconds":1377437202577,"turn":2},{"type":"301","dart":20,"score":"20","dateMilliseconds":1377437202742,"turn":3},{"type":"301","dart":20,"score":"20","dateMilliseconds":1377437202906,"turn":3},{"type":"301","dart":20,"score":"20","dateMilliseconds":1377437203093,"turn":3},{"type":"301","dart":20,"score":"20","dateMilliseconds":1377437203250,"turn":4},{"type":"301","dart":20,"score":"20","dateMilliseconds":1377437203398,"turn":4},{"type":"301","dart":20,"score":"20","dateMilliseconds":1377437203680,"turn":4},{"type":"301","dart":20,"score":"20","dateMilliseconds":1377437203858,"turn":5},{"type":"301","dart":20,"score":"20","dateMilliseconds":1377437204038,"turn":5},{"type":"301","dart":20,"score":"20","dateMilliseconds":1377437204226,"turn":5},{"type":"301","dart":"d10","score":20,"dateMilliseconds":1377437208937,"turn":6}]



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
      Type listType = new TypeToken<ArrayList<Dart>>() {}.getType();
      ArrayList<Dart> dartsList = new Gson().fromJson(is, listType);
      slf4jLogger.debug("dartslist info: ", dartsList.size());

      slf4jLogger.debug("practiceType: " + practiceType.getValue());

      String doubleIn = ""; // find first double
      String doubleOut = ""; // find last dart
      int out = 0; // sum of last turn

      // find the first double
      for (Dart dart : dartsList) {
        if (StringUtils.startsWith(dart.getActual(), "d")) {
          doubleIn = dart.getActual();
          break;
        }
      }

      // find the last dart
      Dart lastDart = dartsList.get(dartsList.size() -1);
      doubleOut = lastDart.getActual();
      int lastRound = lastDart.getRound();

      for (Dart dart : dartsList) {
        if (dart.getRound() == lastRound) {
          out += dart.getScore();
        }
      }


      threeOhOneResult = new ThreeOhOneResult(dartsList, practiceType, doubleIn, doubleOut, out);
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
