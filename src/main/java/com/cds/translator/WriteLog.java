package com.cds.translator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jboss.netty.channel.MessageEvent;

/**
 *
 * @author Adamenko Artem <adamenko.artem@gmail.com>
 * Запись логов в файл
 */
public class WriteLog implements Runnable{
    /*Путь до папки с логами*/
    private static String LogDir  = "";
    /*Путь до папки с логами по дате*/
    private static String dateDir = "";
    /*Путь до папки с логами по дню*/
    private static String dayDir = "";
    /*путь до папки логов записи траектории*/
    private static String routeLogDir = "";
    /*путь до папки логов записи остановок*/
    private static String stationLogDir = "";
    
    /*шаблон для папки по дате*/
    private static SimpleDateFormat dateDirFormat = new SimpleDateFormat("MM-yyyy");
    /*шаблон для папки по дню*/
    private static SimpleDateFormat dayDirFormat = new SimpleDateFormat("dd");
    
    private static final StringBuilder buf = new StringBuilder();
    
    /*параметры для вызова потока*/
    public MessageEvent msgEvent;
    public String param;
    
    /*
     * Инициализация папок и файлов для логов
     */
    public static void initLogging() throws IOException{
        //создаем общую папку с логами Logs в корне программы
        String curDir = new File(".").getAbsolutePath();
        File LogsDir = new File(curDir.replaceAll(".", "") + "Logs");
        try{
            LogsDir.mkdir();
            LogDir = LogsDir.getAbsolutePath();
            dateDirLog();
            dayDirLog();
            routeDirLog();
            stationDirLog();
        }catch(Exception e){
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    /*
     * Создание папки логов для текущего месяца
     */
    private static void dateDirLog() throws Exception{
        Date date = new Date();
        //создание папки месяца
        try{
            String myDate = dateDirFormat.format(date);
            File myDateDir = new File(LogDir + "/" + myDate);
            myDateDir.mkdir();
            dateDir = myDateDir.getAbsolutePath();
        }catch(Exception e){
            throw new Exception (e);
        }
    }
    
     /*
     * Создание папки логов для текущего дня
     */
    private static void dayDirLog() throws Exception{
        Date date = new Date();
        //создание папки дня
        try{
            String myDay = dayDirFormat.format(date);
            File myDayDir = new File(dateDir + "/" +myDay);
            myDayDir.mkdir();
            dayDir = myDayDir.getAbsolutePath();
        }catch(Exception e){
            throw new Exception (e);
        }
    }
    
    /*
     * Создание папки логов для записи маршрута
     */
    private static void routeDirLog() throws Exception{
        try{
            File myRouteDir = new File(dayDir + "/Route");
            myRouteDir.mkdir();
            routeLogDir = myRouteDir.getAbsolutePath();
        }catch(Exception e){
            throw new Exception (e);
        }
    }
    
    /*
     * Создание папки логов для записи остановок
     */
    private static void stationDirLog() throws Exception{
        try{
            File myStationDir = new File(dayDir + "/Station");
            myStationDir.mkdir();
            stationLogDir = myStationDir.getAbsolutePath();
        }catch(Exception e){
            throw new Exception (e);
        }
    }
    
    /*
     * Получить путь до логов записи маршрутов
     */
    public static String getRouteLogPath(){
        return routeLogDir;
    }
    
    /*
     * Получить путь до логов записи остановок
     */
    public static String getStationLogPath(){
        return stationLogDir;
    }
    
    /*
     * Получение текущего времени
     */
     public static String getCurrentTime() {
           Calendar calendar = Calendar.getInstance();
           int hour = calendar.get(Calendar.HOUR_OF_DAY);
           int minute = calendar.get(Calendar.MINUTE);
           int second = calendar.get(Calendar.SECOND);
           return String.format("%02d:%02d:%02d", hour, minute, second); // ЧЧ:ММ:СС - формат времени
     }
     
     /*
      * Запись данных маршрута в лог
      */
     public static void writeRouteLog(MessageEvent e, String msg) throws IOException{
        
        buf.setLength(0);
        buf.append(getCurrentTime());
        buf.append(" Данные обработаны для ");
        buf.append(e.getRemoteAddress());
        buf.append(" Message: ");
        buf.append(msg);
        buf.append("\r\n");
        
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(routeLogDir + "/work.txt",true)));
        try{
            //throw new Exception("Ошибка записи данных");
            out.print(buf);
            out.flush();
        }catch(Exception ex){
            writeErrorLog(ex.getMessage().toString());
        }finally{
            RequestHandler.closeConn(e);
            out.close();   
        }
     }
     
     /*
      * Запись в лог ошибок записи данных маршрута
      */
     public static void writeErrorLog(String msg) throws IOException{
        buf.setLength(0);
        buf.append(getCurrentTime() + " ");
        buf.append(msg);
        buf.append("\r\n");

        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(routeLogDir + "/Errors.txt",true)));
        out.print(buf);
        out.flush();
        out.close();   
     }
     

    public void run() {
        try {
            writeRouteLog(msgEvent, param);
            Logger.getLogger(WriteLog.class.getName()).log(Level.SEVERE, "Записано " + WriteLog.getCurrentTime());
        } catch (IOException ex) {
            Logger.getLogger(WriteLog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
}
