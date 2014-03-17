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
    public static void insertUser(String name, String encryptedPassword, String email) throws MySQLIntegrityConstraintViolationException {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try{
            DartsMapper dartsMapper = sqlSession.getMapper(DartsMapper.class);
            dartsMapper.insertUser(name, encryptedPassword, email);
            sqlSession.commit();
        }finally{
            sqlSession.close();
        }
    }

    public static void insertResetToken(String token, String name) {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try{
            DartsMapper dartsMapper = sqlSession.getMapper(DartsMapper.class);
            dartsMapper.insertResetToken(token, name);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    public static boolean isValidEmail(String email) {
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

    public static boolean isPasswordResetTokenValid(String token) {
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

    public static User getUserDetailsByToken(String token, String email) {
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

    public static User getUserByEmail(String email) {
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

    public static User getUserByUsername(String username) {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        User user = null;
        try {
            DartsMapper dartsMapper = sqlSession.getMapper(DartsMapper.class);
            user = dartsMapper.getUserByUsername(username);
        } finally {
            sqlSession.close();
        }
        return user;
    }


    public static void resetPassword(String email, String encryptedPassword) {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try{
            DartsMapper dartsMapper = sqlSession.getMapper(DartsMapper.class);
            dartsMapper.resetPassword(email, encryptedPassword);
            sqlSession.commit();
        }finally{
            sqlSession.close();
        }
    }

    public static void resetPasswordByUsername(String username, String encryptedPassword) {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try{
            DartsMapper dartsMapper = sqlSession.getMapper(DartsMapper.class);
            dartsMapper.resetPasswordByUsername(username, encryptedPassword);
            sqlSession.commit();
        }finally{
            sqlSession.close();
        }
    }



}