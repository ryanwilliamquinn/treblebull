package com.rquinn.darts;

/**
 * Created with IntelliJ IDEA.
 * User: rquinn
 * Date: 12/10/12
 * Time: 10:38 PM
 * To change this template use File | Settings | File Templates.
 */

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.rquinn.darts.model.User;
import org.apache.ibatis.session.SqlSession;

import java.sql.Timestamp;
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

    public void insertResetToken(String token, String name) {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try{
            DartsMapper dartsMapper = sqlSession.getMapper(DartsMapper.class);
            dartsMapper.insertResetToken(token, name);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    public boolean isValidEmail(String email) {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        int isValid = 0;
        try {
            DartsMapper dartsMapper = sqlSession.getMapper(DartsMapper.class);
            isValid = dartsMapper.isValidEmail(email);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
        return isValid > 0;
    }

    public boolean isPasswordResetTokenValid(String token) {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        DateTimeManagement dt = null;
        try {
            DartsMapper dartsMapper = sqlSession.getMapper(DartsMapper.class);
            java.sql.Timestamp timestamp = dartsMapper.isPasswordResetTokenValid(token);
            dt = new DateTimeManagement(timestamp);
        } finally {
            sqlSession.close();
        }
        if (dt == null) {
            return false;
        } else {
            return dt.isTimestampWithinOneHourOfCurrentTime();
        }

    }

    public User getUserDetailsByToken(String token, String email) {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        User user = null;
        try {
            DartsMapper dartsMapper = sqlSession.getMapper(DartsMapper.class);
            user = dartsMapper.getUserDetailsByToken(token, email);
        } finally {
            sqlSession.close();
        }
        return user;
    }

    public User getUserByEmail(String email) {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        User user = null;
        try {
            DartsMapper dartsMapper = sqlSession.getMapper(DartsMapper.class);
            user = dartsMapper.getUserByEmail(email);
        } finally {
            sqlSession.close();
        }
        return user;
    }


    public void resetPassword(String email, String encryptedPassword) {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try{
            DartsMapper dartsMapper = sqlSession.getMapper(DartsMapper.class);
            dartsMapper.resetPassword(email, encryptedPassword);
            sqlSession.commit();
        }finally{
            sqlSession.close();
        }
    }

}