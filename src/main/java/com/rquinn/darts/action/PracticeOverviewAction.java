package com.rquinn.darts.action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rquinn.darts.DartsResultResponse;
import com.rquinn.darts.DartsResultService;
import com.rquinn.darts.TargetPracticeType;
import com.rquinn.darts.model.PracticeOverviewData;
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
import java.io.PrintWriter;
import java.util.List;

/**
 * User: rquinn
 * Date: 9/8/13
 * Time: 8:28 PM
 */
public class PracticeOverviewAction extends PracticeAction {

  private static final Logger slf4jLogger = LoggerFactory.getLogger(PracticeOverviewAction.class);

  public String load() throws Exception {
    slf4jLogger.debug("Load us some twenties!");

    Subject currentUser = SecurityUtils.getSubject();

    SqlSessionFactory sqlSessionFactory = getSqlSession();

    List<PracticeOverviewData> practiceOverviewData = null;


    SqlSession session = sqlSessionFactory.openSession();
    try {
      DartsResultService dartsResultService = new DartsResultService();
      practiceOverviewData = dartsResultService.getPracticeOverviewData(currentUser.getPrincipal().toString());
      for (PracticeOverviewData data : practiceOverviewData) {
        data.setAverageScore((double) data.getTotalScore() / data.getTotalNumRounds());
        data.setTotalNumDarts(data.getTotalNumRounds() * 3);
      }
    } finally {
      session.close();
    }

    if (practiceOverviewData != null && practiceOverviewData.size() > 0) {
      Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
      String json = gson.toJson(practiceOverviewData);
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
