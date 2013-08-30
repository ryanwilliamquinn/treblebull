package com.rquinn.darts;

/**
 * Created with IntelliJ IDEA.
 * User: rquinn
 * Date: 12/10/12
 * Time: 10:38 PM
 * To change this template use File | Settings | File Templates.
 */

import com.rquinn.darts.model.Dart;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class DartsResultService
{

  private static final Logger slf4jLogger = LoggerFactory.getLogger(DartsResultService.class);

  /**
   * This method does the initial insert of the round with the total score and the game type, into darts result
   * It returns the primary key, to be used for the foreign key in the insertRounds method.
   * @param spr
   * @return
   */
  public void insertGame(DartResult spr) {

    SqlSession sqlSession = null;
    try {
      slf4jLogger.debug(spr.getType().getValue() + " " + spr.getScore() + " " + spr.getDateTimeManagement().getTimestamp() + " " + spr.getUsername());
      sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
      DartsMapper dartsMapper = sqlSession.getMapper(DartsMapper.class);
      dartsMapper.insertGame(spr);
      int gameId = dartsMapper.getPrimaryKey();
      for (Dart result : spr.getDarts()) {
        dartsMapper.insertDart(gameId, result);
      }
      sqlSession.commit();
    } catch (Exception e) {
      slf4jLogger.debug("error inserting game: " + e);
    } finally {
      sqlSession.close();
    }
  }

  /**
   * This method does the initial insert of the round with the total score and the game type, into darts result
   * It returns the primary key, to be used for the foreign key in the insertRounds method.
   * @param cricketResult
   * @return
   */
  public void insertCricketGame(ThreeDartResult cricketResult) {
    slf4jLogger.debug(cricketResult.getType() + " " + cricketResult.getScore() + " " + cricketResult.getDateTimeManagement().getTimestamp() + " " + cricketResult.getUsername());
    SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
    try {
      DartsMapper dartsMapper = sqlSession.getMapper(DartsMapper.class);
      dartsMapper.insertGame(cricketResult);
      int primaryKey = dartsMapper.getPrimaryKey();
      for (ThreeDartRoundResult result : cricketResult.getRoundResult()) {
        dartsMapper.insertThreeDartRound(primaryKey, result);
      }
      sqlSession.commit();
    }finally{
      sqlSession.close();
    }
  }

  /**
   * This method does the initial insert of the round with the total score and the game type, into darts result
   * It returns the primary key, to be used for the foreign key in the insertRounds method.
   * @param threeOhOneResult
   * @return
   */
  public void insert301Game(ThreeOhOneResult threeOhOneResult) {
    slf4jLogger.debug(threeOhOneResult.getType().getValue() + " " + threeOhOneResult.getScore() + " " + threeOhOneResult.getDateTimeManagement().getTimestamp() + " " + threeOhOneResult.getUsername());
    SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
    try {
      DartsMapper dartsMapper = sqlSession.getMapper(DartsMapper.class);
      dartsMapper.insert301Game(threeOhOneResult);
      int primaryKey = dartsMapper.getPrimaryKey();
      for (Dart result : threeOhOneResult.getRoundResult()) {
        dartsMapper.insertDart(primaryKey, result);
      }
      sqlSession.commit();
    }finally{
      sqlSession.close();
    }
  }

  public DartsResult getResultById(Integer id) {
    SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
    try{
      DartsMapper dartsMapper = sqlSession.getMapper(DartsMapper.class);
      return dartsMapper.getResultById(id);
    }finally{
      sqlSession.close();
    }
  }

  public DartsResultResponse getAllResults(String userName, BasePracticeType type) {
    SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
    try{
      DartsMapper dartsMapper = sqlSession.getMapper(DartsMapper.class);
      String typeValue = type.getValue();
      DartsResultResponse dartsResultResponse = new DartsResultResponse();
      List<DartsResult> resultsList =  dartsMapper.getAllResults(userName, typeValue);
      dartsResultResponse.setDartsResults(resultsList);
      int totalNumResults = dartsMapper.getNumResults(userName, typeValue);
      dartsResultResponse.setTotalNumResults(totalNumResults);
      return dartsResultResponse;
    }finally{
      sqlSession.close();
    }
  }

  public DartsResultResponse getTenResults(String userName, BasePracticeType type) {
    SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
    try{
      DartsMapper dartsMapper = sqlSession.getMapper(DartsMapper.class);
      String typeValue = type.getValue();
      slf4jLogger.debug("sql info for get ten results - username: " + userName + ", type: " + typeValue);
      DartsResultResponse dartsResultResponse = new DartsResultResponse();
      List<DartsResult> dartsResults = null;
      if (type.isTargetPracticeType()) {
        dartsResults = dartsMapper.getTenResults(userName, typeValue);
      } else {
        dartsResults = dartsMapper.getTenOhOneResults(userName, typeValue);
      }
      for (DartsResult dr : dartsResults) {
        dr.getDateTimeManagement().initializeDates();
      }
      dartsResultResponse.setDartsResults(dartsResults);
      int totalNumResults = dartsMapper.getNumResults(userName,typeValue);
      dartsResultResponse.setTotalNumResults(totalNumResults);
      slf4jLogger.debug("darts result: " + dartsResultResponse.getDartsResults().toString());
      return dartsResultResponse;
    }catch (Exception e) {
      slf4jLogger.debug("exception eeeee: " + e);
    } finally {
      sqlSession.close();
    }
    return null;
  }

  public void updateResult(DartsResult dartsResult) {
    SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
    try{
      DartsMapper dartsMapper = sqlSession.getMapper(DartsMapper.class);
      dartsMapper.updateResult(dartsResult);
      sqlSession.commit();
    }finally{
      sqlSession.close();
    }

  }

  public void deleteResult(Integer id) {
    SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
    try{
      DartsMapper dartsMapper = sqlSession.getMapper(DartsMapper.class);
      dartsMapper.deleteResult(id);
      sqlSession.commit();
    }finally{
      sqlSession.close();
    }

  }

  public List<Dart> getGameDetails(int gameId) {
    SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
    try{
      DartsMapper dartsMapper = sqlSession.getMapper(DartsMapper.class);
      List<Dart> rounds = dartsMapper.getGameDetails(gameId);
      return rounds;
    }finally{
      sqlSession.close();
    }
  }

  /*
      return a map with key as target and value as average for this game mode for that target
   */
  public List<FreeAverageData> getFreeAverages(String userName) {
    SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
    try{
      DartsMapper dartsMapper = sqlSession.getMapper(DartsMapper.class);
      List<FreeAverageData> rounds = dartsMapper.getFreeAverages(userName);
      return rounds;
    } catch (Exception e) {
      slf4jLogger.debug("some db problem with getfreeaverages", e);
      return null;
    } finally {
      sqlSession.close();
    }
  }

  public List<Dart> getFreeTargetHistory(String userName, String target) {
    SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
    try{
      DartsMapper dartsMapper = sqlSession.getMapper(DartsMapper.class);
      List<Dart> singleDartResults = dartsMapper.getFreeTargetHistory(userName, target);
      return singleDartResults;
    }finally{
      sqlSession.close();
    }
  }

}