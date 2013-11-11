package com.cds.translator;

import com.cds.mybatis.MyBatisManager;
import com.cds.mybatis.RequestDataSessionManager;
import com.cds.mybatis.RequestProjectsSessionManager;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.execution.OrderedMemoryAwareThreadPoolExecutor;
public class App 
{
    public static void main( String[] args ) throws UnknownHostException, IOException
    {
        WriteLog.initLogging();
        try {
            MyBatisManager.initDBFactory("development", "Projects");
            MyBatisManager.initDBFactory("development", "Data");
            RequestProjectsSessionManager.initialize();
            RequestDataSessionManager.initialize();
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        //число рабочих потоков, максимум памяти на 1 канал, максимальный суммарный размер памяти, время жизни
        //(int corePoolSize, long maxChannelMemorySize, long maxTotalMemorySize, long keepAliveTime, TimeUnit unit) 
        ExecutorService bossExec = new OrderedMemoryAwareThreadPoolExecutor(1, 400000000, 2000000000, 60, TimeUnit.SECONDS);
        ExecutorService ioExec = new OrderedMemoryAwareThreadPoolExecutor(4 /* число рабочих потоков */, 400000000, 2000000000, 60, TimeUnit.SECONDS);
        // Configure the server.
          ServerBootstrap bootstrap = new ServerBootstrap(
                  new NioServerSocketChannelFactory(bossExec, ioExec, 4 /* то же самое число рабочих потоков */));
  
          // Set up the event pipeline factory.
          bootstrap.setPipelineFactory(new ServerPipelineFactory());  
          // Bind and start to accept incoming connections.
          bootstrap.bind(new InetSocketAddress(8080));
    }
}
