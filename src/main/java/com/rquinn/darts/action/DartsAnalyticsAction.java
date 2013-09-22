package com.rquinn.darts.action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rquinn.darts.DartsResultService;
import com.rquinn.darts.TargetPracticeType;
import com.rquinn.darts.model.DartsAnalyticsResponse;
import com.rquinn.darts.model.DartsAnalyticsResult;
import com.rquinn.darts.model.PracticeOverviewData;
import com.rquinn.darts.model.TargetAnalyticsData;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * User: rquinn
 * Date: 9/21/13
 * Time: 10:10 PM
 */
public class DartsAnalyticsAction extends BaseAction {

  private static final Logger slf4jLogger = LoggerFactory.getLogger(DartsAnalyticsAction.class);

  public String showAnalytics() throws Exception {

    Subject currentUser = SecurityUtils.getSubject();

    SqlSessionFactory sqlSessionFactory = getSqlSession();

    List<DartsAnalyticsResult> dartsAnalyticsData = null;

    SqlSession session = sqlSessionFactory.openSession();
    try {
      DartsResultService dartsResultService = new DartsResultService();

      dartsAnalyticsData = dartsResultService.getDartsAnalyticsData(currentUser.getPrincipal().toString());
    } finally {
      session.close();
    }

    List<DartsAnalyticsResponse> analyticsResponses = new ArrayList<DartsAnalyticsResponse>();

    for (TargetPracticeType pt : TargetPracticeType.values()) {
      if (pt.isAnalytics()) {
        DartsAnalyticsResponse resp = new DartsAnalyticsResponse();
        analyticsResponses.add(resp);
        resp.setType(pt.getValue());
        for (DartsAnalyticsResult result : dartsAnalyticsData) {
          if (pt.getValue().equals(result.getType())) {
            // this is hits data
            if (StringUtils.contains(pt.getValue(), result.getActual())) {
              TargetAnalyticsData hitsData = resp.getHits();
              if (hitsData == null) {
                hitsData = new TargetAnalyticsData();
              }
              hitsData.setTarget(pt.getValue());
              hitsData.setTotal(hitsData.getTotal() + result.getNumDarts());
              if (StringUtils.equalsIgnoreCase(result.getActual(), "t" + pt.getValue())) {
                hitsData.setNumTriples(result.getNumDarts());
              } else if (StringUtils.equalsIgnoreCase(result.getActual(), "d" + pt.getValue())) {
                hitsData.setNumDoubles(result.getNumDarts());
              }
              resp.setHits(hitsData);
            } else {
              List<TargetAnalyticsData> missDataList = resp.getMisses();
              TargetAnalyticsData missData = null;
              if (missDataList == null) {
                missDataList = new ArrayList<TargetAnalyticsData>();
                resp.setMisses(missDataList);
              }
              for (TargetAnalyticsData tData : missDataList) {
                if (tData.getActual().equals(result.getActual())) {
                  missData = tData;
                }
              }
              if (missData == null) {
                missData = new TargetAnalyticsData();
                resp.getMisses().add(missData);
              }
              missData.setTarget(pt.getValue());
              missData.setActual(result.getActual());
              missData.setTotal(missData.getTotal() + result.getNumDarts());
              if (StringUtils.equalsIgnoreCase(result.getActual(), "t" + pt.getValue())) {
                missData.setNumTriples(result.getNumDarts());
              } else if (StringUtils.equalsIgnoreCase(result.getActual(), "d" + pt.getValue())) {
                missData.setNumDoubles(result.getNumDarts());
              }
            }
          }
        }
      }
    }

    if (analyticsResponses != null && analyticsResponses.size() > 0) {
      Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
      String json = gson.toJson(analyticsResponses);
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
