/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cds.translator;

import com.cds.mapper.DataMapper;
import com.cds.mybatis.RequestDataSessionManager;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.ibatis.jdbc.SqlBuilder;
import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.ORDER_BY;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author Администратор
 */
public class TestInsertDB implements Runnable{
    public int id;
    static SqlSession session = RequestDataSessionManager.getRequestSession();
    static DataMapper mapper = session.getMapper(DataMapper.class); 
    
    public void run() {
        //SqlSession session = RequestDataSessionManager.getRequestSession();
        //DataMapper mapper = session.getMapper(DataMapper.class);   
        mapper.insertProvider(id);
        Logger.getLogger(WriteLog.class.getName()).log(Level.SEVERE, "Записано" + WriteLog.getCurrentTime());
        session.commit();
    }
    
    public void run2() {
        SqlSession session = RequestDataSessionManager.getRequestSession();
        DataMapper mapper = session.getMapper(DataMapper.class);   
        mapper.insert(id);
        Logger.getLogger(WriteLog.class.getName()).log(Level.SEVERE, "Записано" + WriteLog.getCurrentTime());
        session.commit();
    }
    
     /*Составной динамический запрос*/
    public String insert(int id) {
      StringBuilder sqlBuilder = new StringBuilder()
        .append("insert into test(id) ")
        .append("values("+ id + ")");
      return sqlBuilder.toString();
    }
}
