package com.rquinn.darts;

import com.rquinn.darts.model.Dart;
import org.apache.ibatis.annotations.Param;
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

  public void insertUser(@Param("name") String name, @Param("encryptedPassword") String encryptedPassword);

  public void insertRound(@Param("foreignKey") int foreignKey, @Param("roundResult") RoundResult roundResult);

  public void insertThreeDartRound(@Param("foreignKey") int foreignKey, @Param("roundResult") ThreeDartRoundResult roundResult);

  public int getPrimaryKey();

  public DartsResult getResultById(Integer id);

  public List<DartsResult> getAllResults(@Param("username") String username, @Param("type") String type);

  public List<Dart> getGameDetails(int gameId);

  public List<DartsResult> getTenResults(@Param("username") String username, @Param("type") String type);

  public int getNumResults(@Param("username") String username, @Param("type") String type);

  public void updateResult(DartsResult dartsResult);

  public void deleteResult(Integer userId);

  public List<FreeAverageData> getFreeAverages(String userName);

  public List<Dart> getFreeTargetHistory(@Param("username") String userName, @Param("target") String target);

  public void insertFreeDart(@Param("foreignKey") int foreignKey, @Param("dart") Dart dart);

  public void insertDart(@Param("foreignKey") int foreignKey, @Param("dart") Dart dart);

}


