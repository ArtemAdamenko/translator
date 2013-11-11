package com.cds.mybatis;

import org.apache.ibatis.session.SqlSession;

/**
 * Класс с функциями работы с поточной сессией БД Data
 * @author Adamenko Artem <adamenko.artem@gmail.com>
 */
public class RequestDataSessionManager {

    private static RequestSessionProvider requestSessionProvider;

    public static void initialize() {
        // инициализируем requestSessionProvider
        requestSessionProvider = new ThreadLocalRequestDataSessionProvider();
    }

    public static SqlSession getRequestSession() {
        return requestSessionProvider.getRequestSession();
    }

    public static boolean isRequestSessionOpen() {
        return requestSessionProvider.isRequestSessionOpen();
    }

    public static void closeRequestSession() {
        requestSessionProvider.closeRequestSession();
    }
}
