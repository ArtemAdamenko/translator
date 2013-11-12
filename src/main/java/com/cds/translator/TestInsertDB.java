/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cds.translator;

import com.cds.mapper.DataMapper;
import com.cds.mybatis.RequestDataSessionManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author Администратор
 */
public class TestInsertDB implements Runnable{
    public int id;
    
    
    public void run() {
        SqlSession session = RequestDataSessionManager.getRequestSession();
        DataMapper mapper = session.getMapper(DataMapper.class);   
        mapper.insert(id);
        Logger.getLogger(WriteLog.class.getName()).log(Level.SEVERE, "Записано" + WriteLog.getCurrentTime(), "Записано" + WriteLog.getCurrentTime());
    }
    
    public void run2() {
        SqlSession session = RequestDataSessionManager.getRequestSession();
        DataMapper mapper = session.getMapper(DataMapper.class);   
        mapper.insert(id);
        Logger.getLogger(WriteLog.class.getName()).log(Level.SEVERE, "Записано" + WriteLog.getCurrentTime(), "Записано" + WriteLog.getCurrentTime());
    }
}
