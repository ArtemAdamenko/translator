package com.cds.mybatis;

import com.cds.mapper.ProjectsMapper;
import java.io.Reader;
import java.util.logging.Level;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.util.logging.Logger;
import com.cds.mapper.DataMapper;

/**
 *
 * @author Artem Adamenko <adamenko.artem@gmail.com>
 * Класс для работы с сессиями
 */
public class MyBatisManager {
    /*Объект хранящий Sql Project сессию*/
    private static SqlSessionFactory sqlProjectSessionFactory;
    /*Объект хранящий Sql Data сессию*/
    private static SqlSessionFactory sqlDataSessionFactory;
    /*Запись в лог*/
    private final static Logger Log = Logger.getLogger(MyBatisManager.class.getName());
    /*сообщение об ошибке в бд Data*/
    final static String ERROR_DB_DATA = "Ошибка подключения к БД Data";
     /*сообщение об ошибке в бд Project*/
    final static String ERROR_DB_PROJECT = "Ошибка подключения к БД Project";
    
    
    /*
     * Инициализация подключения к БД
     * @param String environment
     */
    public static void initDBFactory(String environment, String db) throws Exception{
        if (db.equals("Projects")){     
            try{
                String resource = "com/cds/mybatis/mybatis-config-projects.xml";
                Reader reader = Resources.getResourceAsReader(resource);
                if (sqlProjectSessionFactory == null){
                    Log.info("Запуск SqlProjectSessionFactory");
                    sqlProjectSessionFactory = new SqlSessionFactoryBuilder().build(reader, environment);
                    sqlProjectSessionFactory.getConfiguration().addMapper(ProjectsMapper.class);
                }            
            }catch(Exception e){
                Log.log(Level.SEVERE, ERROR_DB_PROJECT, e);
            }
        }if (db.equals("Data")){
            try{          
                String resource = "com/cds/mybatis/mybatis-config-data.xml";
                Reader reader = Resources.getResourceAsReader(resource);
                if (sqlDataSessionFactory == null){
                    Log.info("Запуск SqlDataSessionFactory");
                    sqlDataSessionFactory = new SqlSessionFactoryBuilder().build(reader, environment);
                    sqlDataSessionFactory.getConfiguration().addMapper(DataMapper.class);
                }
            }catch(Exception e){
                Log.log(Level.SEVERE, ERROR_DB_DATA, e);
            }
        }
    }
    
    /*
     * Возвращает singleton SqlProjectSessionFactory
     * @return SqlSessionFactory
     */
    public static SqlSessionFactory getProjectSessionFactory() throws Exception{
        return sqlProjectSessionFactory;
    }
    
     /*
     * Возвращает singleton SqlDataSessionFactory
     * @return SqlSessionFactory
     */
    public static SqlSessionFactory getDataSessionFactory() throws Exception{
        return sqlDataSessionFactory;
    }
}

