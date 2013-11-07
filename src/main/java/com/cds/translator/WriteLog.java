package com.cds.translator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Adamenko Artem <adamenko.artem@gmail.com>
 * Запись логов в файл
 */
public class WriteLog {
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
    /*
     * Инициализация папок и файлов для логов
     */
    public static void initLogging(){
        //создаем общую папку с логами Logs в корне программы
        String curDir = new File(".").getAbsolutePath();
        File LogsDir = new File(curDir.replaceAll(".", "") + "Logs");
        LogsDir.mkdir();
        LogDir = LogsDir.getAbsolutePath();
        dateDirLog();
        dayDirLog();
        routeDirLog();
        stationDirLog();
    }
    
    /*
     * Создание папки логов для текущего месяца
     */
    private static void dateDirLog(){
        Date date = new Date();
        //создание папки месяца
        String myDate = dateDirFormat.format(date);
        File myDateDir = new File(LogDir + "/" + myDate);
        myDateDir.mkdir();
        dateDir = myDateDir.getAbsolutePath();       
    }
    
     /*
     * Создание папки логов для текущего дня
     */
    private static void dayDirLog(){
        Date date = new Date();
        //создание папки дня
        String myDay = dayDirFormat.format(date);
        File myDayDir = new File(dateDir + "/" +myDay);
        myDayDir.mkdir();
        dayDir = myDayDir.getAbsolutePath();
    }
    
    /*
     * Создание папки логов для записи маршрута
     */
    private static void routeDirLog(){
        File myRouteDir = new File(dayDir + "/Route");
        myRouteDir.mkdir();
        routeLogDir = myRouteDir.getAbsolutePath();
    }
    
    /*
     * Создание папки логов для записи остановок
     */
    private static void stationDirLog(){
        File myStationDir = new File(dayDir + "/Station");
        myStationDir.mkdir();
        stationLogDir = myStationDir.getAbsolutePath();
    }
    
    public static String getRouteLogPath(){
        return routeLogDir;
    }
    
    public static String getStationLogPath(){
        return stationLogDir;
    }
    
    public static boolean checkLogFile(){
        File logFile = new File(routeLogDir + "/work.txt");
        if (logFile.exists())
            return true;
        else
            return false;
    }
    
    public void createFile() throws IOException{
        File flt = new File("javaprobooks.txt");
        PrintWriter out = new PrintWriter(new BufferedWriter(
            new FileWriter(flt)));
        out.print("Welcome to javaprobooks.ru");
        out.flush();
    }
}
