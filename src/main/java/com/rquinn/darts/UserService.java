package com.rquinn.darts;

/**
 * Created with IntelliJ IDEA.
 * User: rquinn
 * Date: 12/10/12
 * Time: 10:38 PM
 * To change this template use File | Settings | File Templates.
 */

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class UserService
{
    public void insertUser(String name, String encryptedPassword) throws MySQLIntegrityConstraintViolationException {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try{
            DartsMapper dartsMapper = sqlSession.getMapper(DartsMapper.class);
            dartsMapper.insertUser(name, encryptedPassword);
            sqlSession.commit();
        }finally{
            sqlSession.close();
        }
    }
}