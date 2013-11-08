package com.cds.translator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.jboss.netty.buffer.BigEndianHeapChannelBuffer;
 
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

/**
 *
 * @author Администратор
 */
public class RequestHandler extends SimpleChannelUpstreamHandler {
      
      
      @Override
      public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
          
        
        
        WriteLog.writeRouteLog(e);
        
        closeConn(e);
      }
      
      /*
       * Закрытие канала соединения
       */
      private void closeConn(MessageEvent e){
        ChannelFuture future = e.getFuture();
        future.addListener(ChannelFutureListener.CLOSE);
      }
      
      
}

