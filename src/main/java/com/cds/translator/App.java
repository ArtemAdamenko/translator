package com.cds.translator;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
 
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.execution.OrderedMemoryAwareThreadPoolExecutor;
public class App 
{
    public static void main( String[] args ) throws UnknownHostException, IOException
    {
        
        
        //Socket s = new Socket(InetAddress.getByName("localhost"),8080);
        //OutputStream os = s.getOutputStream();
        //os.write("Hello".getBytes());
        //os.flush();
        //Socket sock = new Socket("192.168.137.3",8080);
        //OutputStream os = sock.getOutputStream();
        //os.write("Hello".getBytes());
        //os.flush();
        ExecutorService bossExec = new OrderedMemoryAwareThreadPoolExecutor(1, 400000000, 2000000000, 60, TimeUnit.SECONDS);
        ExecutorService ioExec = new OrderedMemoryAwareThreadPoolExecutor(4 /* число рабочих потоков */, 400000000, 2000000000, 60, TimeUnit.SECONDS);
        // Configure the server.
          ServerBootstrap bootstrap = new ServerBootstrap(
                  new NioServerSocketChannelFactory(bossExec, ioExec,  4 /* то же самое число рабочих потоков */));
  
          // Set up the event pipeline factory.
          bootstrap.setPipelineFactory(new HttpServerPipelineFactory());  
          // Bind and start to accept incoming connections.
          bootstrap.bind(new InetSocketAddress(8080));
    }
}
