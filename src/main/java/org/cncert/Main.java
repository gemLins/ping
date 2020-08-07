package org.cncert;

import org.apache.commons.cli.*;
import org.cncert.app.PingResultService;
import org.cncert.app.VFetcher;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;

/**
 * @Author: sdcert
 * @Date 2020/5/26 上午5:39
 * @Description
 */
public class Main {
    private final static Logger LOGGER = LoggerFactory.getLogger(Main.class);



    private static  String usage;
    private  static  String path;

    public static String getPath() {
        return path;
    }

    public static void main(String[] args) {
        String host = "localhost";
        int port = 8080;
        CommandLineParser commandLineParser = new DefaultParser();
        Options options = new Options();
        Option option = new Option("help","help",false,"帮助");
        option.setRequired(false);
        options.addOption(option);
         option = new Option("h","host",true,"主机名");
        option.setRequired(false);
        options.addOption(option);
         option = new Option("p","port",true,"端口");
        option.setRequired(false);
        options.addOption(option);
         option = new Option("s","scriptPath",true,"脚本位置的绝对路径,例如 /root/ip.sh");
        option.setRequired(false);
        options.addOption(option);
        try {
            CommandLine parse = commandLineParser.parse(options, args);
            if(parse.hasOption("host")){
                host = parse.getOptionValue("host");
            }
            if(parse.hasOption("port")){
                port = Integer.valueOf(parse.getOptionValue("port"));
            }
            if(parse.hasOption("scriptPath")){
               path = parse.getOptionValue("scriptPath");
            }else {
                path = "/root";
            }
            if(path.endsWith("/")){
                path = path.substring(0,path.length() - 1);
            }
            if(parse.hasOption("help")){
                getHelpString(options);
                System.out.println(usage);
                System.exit(0);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            getHelpString(options);
            System.out.println(usage);
            System.exit(0);

        }
        InetSocketAddress inetSocketAddress = new InetSocketAddress(host,port);
        Server server=new Server(inetSocketAddress);
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath("/");
        server.setHandler(handler);
        ServletHolder servlet = new ServletHolder(ServletContainer.class);
        servlet.setInitParameter("jersey.config.server.provider.packages","org.cncert.resource");
        servlet.setInitOrder(0);
        handler.addServlet(servlet, "/*");

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                PingResultService.close(); //jvm停止前，关闭线程池
            }
        }));

        try {
            // new Thread(new VFetcher()).start();
            server.start();
            System.out.println("start...in "+port);
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            server.destroy();
        }
    }

    private static void getHelpString(Options options){
        HelpFormatter helpFormatter = new HelpFormatter();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(byteArrayOutputStream);
        helpFormatter.printHelp(printWriter,HelpFormatter.DEFAULT_WIDTH,"java -jar ping-1.0.jar  参数",null,options,HelpFormatter.DEFAULT_LEFT_PAD,HelpFormatter.DEFAULT_DESC_PAD,null);
        printWriter.flush();
        usage = new String(byteArrayOutputStream.toByteArray());
        printWriter.close();
    }

}
