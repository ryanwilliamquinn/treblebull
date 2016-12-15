package com.rquinn.darts;

/**
 * Created with IntelliJ IDEA.
 * User: rquinn
 * Date: 12/10/12
 * Time: 10:36 PM
 * To change this template use File | Settings | File Templates.
 */

import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil
{
    private static SqlSessionFactory factory;

    private MyBatisUtil() {
    }

    static
    {
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("mybatis-config.xml");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        factory = new SqlSessionFactoryBuilder().build(reader, System.getenv("MYBATIS_ENVIRONMENT"));
    }

    public static SqlSessionFactory getSqlSessionFactory()
    {
        return factory;
    }
}
