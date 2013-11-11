package com.cds.translator;
 
import com.cds.mapper.DataMapper;
import com.cds.mybatis.RequestDataSessionManager;
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

            //запрос в бд
            SqlSession session = RequestDataSessionManager.getRequestSession();
            DataMapper mapper = session.getMapper(DataMapper.class);   
            int id = Integer.parseInt(str);
            String name = mapper.test(id);
            
            //пустое значение
            if (name == null)
                throw new Exception("Пустое значение");
            //запись в лог
            WriteLog.writeRouteLog(e, name);
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

