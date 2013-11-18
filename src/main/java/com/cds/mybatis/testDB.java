/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cds.mybatis;

import com.cds.translator.WriteLog;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Администратор
 */
public class testDB  implements Runnable {
    public int id;
    public void run(){
        try {
            //SqlSession session = RequestDataSessionManager.getRequestSession();
            //DataMapper mapper = session.getMapper(DataMapper.class);   
            JDBCConn();
            //session.commit();
        } catch (SQLException ex) {
            Logger.getLogger(testDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void JDBCConn() throws SQLException{
      
    String user = "postgres";//Логин пользователя
    String password = "zaUgD5Lt";//Пароль пользователя
    String url = "jdbc:postgresql://192.168.137.142:5432/CDS";//URL адрес
    String driver = "org.postgresql.Driver";//Имя драйвера
    try {
        Class.forName(driver);//Регистрируем драйвер
    } catch (ClassNotFoundException e) {
    // TODO Auto-generated catch block
        e.printStackTrace();
    }
    Connection c = null;//Соединение с БД
    //подготавливаем запрос с параметрами
    PreparedStatement preparedStatement = null;
    String UsersSelect = "insert into test(id) values(?)";

        c = DriverManager.getConnection(url, user, password);//Установка соединения с БД

        preparedStatement = c.prepareStatement(UsersSelect);//Готовим запрос
        //готовим параметры(в данном случае один)
        preparedStatement.setInt(1, this.id);
        preparedStatement.execute();//Выполняем запрос к БД, результат передаем в rs
        Logger.getLogger(WriteLog.class.getName()).log(Level.SEVERE, "Записано" + WriteLog.getCurrentTime());
        //c.commit();
        c.close();
        preparedStatement.close();
    }
}
