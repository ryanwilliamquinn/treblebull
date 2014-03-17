package com.rquinn.darts;

import com.rquinn.darts.model.Dart;
import com.rquinn.darts.model.DartsAnalyticsResult;
import com.rquinn.darts.model.PracticeOverviewData;
import com.rquinn.darts.model.User;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * User: rquinn
 * Date: 12/10/12
 * Time: 10:25 PM
 */
public interface DartsMapper {

  public void insertResult(DartsResult dartsResult);

  public int insertGame(DartsResult dartsResult);

  public int insert301Game(ThreeOhOneResult dartsResult);

  public void insertUser(@Param("name") String name, @Param("encryptedPassword") String encryptedPassword, @Param("email") String email);

  public void insertResetToken(@Param("token") String token, @Param("email") String email);

  public Timestamp isPasswordResetTokenValid(String token);

  public User getUserDetailsByToken(@Param("token") String token, @Param("email") String email);

  public User getUserByEmail(String email);

  public User getUserByUsername(String username);

  public void resetPassword(@Param("email") String email, @Param("pw") String pw);

  public void resetPasswordByUsername(@Param("username") String username, @Param("pw") String pw);

  public int isValidEmail(String email);

  public void insertRound(@Param("foreignKey") int foreignKey, @Param("roundResult") RoundResult roundResult);

  public void insertThreeDartRound(@Param("foreignKey") int foreignKey, @Param("roundResult") ThreeDartRoundResult roundResult);

  public int getPrimaryKey();

  public DartsResult getResultById(Integer id);

  public List<DartsResult> getAllResults(@Param("username") String username, @Param("type") String type);

  public List<Dart> getGameDetails(int gameId);

  public List<DartsResult> getTenResults(@Param("username") String username, @Param("type") String type);

  public List<DartsResult> getTenOhOneResults(@Param("username") String username, @Param("type") String type);

  public int getNumResults(@Param("username") String username, @Param("type") String type);

  public void updateResult(DartsResult dartsResult);

  public void deleteResult(Integer userId);

  public List<FreeAverageData> getFreeAverages(String userName);

  public List<Dart> getFreeTargetHistory(@Param("username") String userName, @Param("target") String target);

  public List<PracticeOverviewData> getPracticeOverviewData(String userName);

  public DartsResult getLatestTargetPracticeRound(@Param("userName") String userName, @Param("type") String type);

  public DartsResult getHistoryOverview(@Param("userName") String userName, @Param("type") String type, @Param("days") Integer days);

  public List<DartsAnalyticsResult> getAnalyticsData(@Param("userName") String userName, @Param("type") String type);

  public void insertFreeDart(@Param("foreignKey") int foreignKey, @Param("dart") Dart dart);

  public void insertDart(@Param("foreignKey") int foreignKey, @Param("dart") Dart dart);


}


