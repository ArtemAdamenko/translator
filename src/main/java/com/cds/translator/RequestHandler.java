package com.cds.translator;
 
import com.cds.mapper.DataMapper;
import com.cds.mybatis.RequestDataSessionManager;
import com.cds.mybatis.testDB;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.buffer.BigEndianHeapChannelBuffer;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

/**
 *
 * @author Adamenko Artem <adamenko.artem@gmail.com>
 * Обработка входящих запросов
 */
public class RequestHandler extends SimpleChannelUpstreamHandler {
        /*Запись логов в файл*/
      private WriteLog writeLog = new WriteLog();
      private TestInsertDB test = new TestInsertDB();
      private testDB db = new testDB();
      /*
       * Обработка входящего сообщения
       */
      @Override
      public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {       
        try{
            
            //обработка входящего пакета 
            BigEndianHeapChannelBuffer packet = (BigEndianHeapChannelBuffer)e.getMessage();
            //шапка пакета
            short service_id = packet.getShort(0);
            short type_header = packet.getShort(2);
            short flag = packet.getShort(4);
            int request_id = packet.getInt(6);
            
            //шапка данных
            byte type_data = packet.getByte(10);
            byte number = packet.getByte(11);
            
            //данные
            int timestamp = packet.getInt(12);
            int longitude = packet.getInt(16);
            int latitude = packet.getInt(20);
            byte extra_dop = packet.getByte(24);
            byte bat_voltage = packet.getByte(25);
            short speed_avg = packet.getShort(26);
            short speed_max = packet.getShort(28);
            short course = packet.getShort(30);
            short track = packet.getShort(32);
            short altitude = packet.getShort(34);
            byte nsat = packet.getByte(36);
            byte pdop = packet.getByte(37);
            
            System.out.println(service_id + " " + type_header + " " + flag + " " + request_id + " "
                    + type_data + " " + number + " " + timestamp + " " + longitude + " " + latitude + " " + extra_dop + " " + 
                    bat_voltage + " " + speed_avg + " " + speed_max + " " + course + " " + track + " " + 
                    altitude + " " + nsat + " " + pdop);
            String log = service_id + " " + type_header + " " + flag + " " + request_id + " "
                    + type_data + " " + number + " " + timestamp + " " + longitude + " " + latitude + " " + extra_dop + " " + 
                    bat_voltage + " " + speed_avg + " " + speed_max + " " + course + " " + track + " " + 
                    altitude + " " + nsat + " " + pdop;
            
            ExecutorService service = Executors.newCachedThreadPool();
            writeLog.msgEvent = e;
            writeLog.msg = log;
            service.submit(writeLog);
            //запись в бд в отдельном потоке
           // Runnable r = db;
           // Thread t = new Thread(r);
           // t.start();
            //запись в бд без отдельного потока
            //test.run2();
            //Logger.getLogger(WriteLog.class.getName()).log(Level.SEVERE, "Данные приняты " + WriteLog.getCurrentTime() + " " + id);
            //closeConn(e);
            //запрос в бд
            /*SqlSession session = RequestDataSessionManager.getRequestSession();
            DataMapper mapper = session.getMapper(DataMapper.class);   
            int id = Integer.parseInt(str);
            String name = mapper.test(3);
            
            //подготавливаем данные для запуска потока записи логов
            writeLog.msgEvent = e;
            writeLog.param = name;
            //создаем поток
            Runnable r = writeLog;           
            Thread t = new Thread(r);
            
            
            //Thread logs = new Thread(new WriteLog(e,name));
            //пустое значение
            if (name == null)
                throw new Exception("Пустое значение");
            //запись в лог
            //вызываем поток
            t.start();*/
        }catch(Exception ex){
            WriteLog.writeErrorLog(ex.toString());
        }finally{
            closeConn(e);
        }  
      }
      
      /*
       * Закрытие канала соединения
       */
      public static void closeConn(MessageEvent e){
        ChannelFuture future = e.getFuture();
        future.addListener(ChannelFutureListener.CLOSE);
      }
      
      
}

