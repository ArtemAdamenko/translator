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
            BigEndianHeapChannelBuffer res = (BigEndianHeapChannelBuffer)e.getMessage();
            byte[] res2 = res.array();
            String str = new String(res2, "UTF-8");
            int id = Integer.parseInt(str);
            
            //test.id = id;
            db.id = id;
            /*ExecutorService service = Executors.newCachedThreadPool();
            writeLog.msgEvent = e;
            writeLog.param = name;
            service.submit(writeLog);*/
            //запись в бд в отдельном потоке
            Runnable r = db;
            Thread t = new Thread(r);
            t.start();
            //запись в бд без отдельного потока
            //test.run2();
            Logger.getLogger(WriteLog.class.getName()).log(Level.SEVERE, "Данные приняты " + WriteLog.getCurrentTime() + " " + id);
            closeConn(e);
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

