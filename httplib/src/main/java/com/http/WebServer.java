package com.http;

import com.http.intercept.ShInterceptor;


import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.DefaultHttpServerConnection;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.HttpRequestHandlerRegistry;
import org.apache.http.protocol.HttpService;
import org.apache.http.protocol.ResponseConnControl;
import org.apache.http.protocol.ResponseContent;
import org.apache.http.protocol.ResponseDate;
import org.apache.http.protocol.ResponseServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class WebServer extends Thread {

	private int port;

	private boolean isLoop = false;

	public static List<Object> controllers = new ArrayList();
	public static List<ShInterceptor> intercepts = new ArrayList();
	public String controllerPackageName;
	public String interceptPackageName;

	List<String>  controllerName = new ArrayList();
	List<String>  interceptPName  = new ArrayList();


	public WebServer(int port) {
		super();
		this.port = port;
	}

	@Override
	public void run() {

		initController();
		ServerSocket serverSocket = null;
		try {
			// 创建服务器套接字
			serverSocket = new ServerSocket(port);
			// 创建HTTP协议处理器
			BasicHttpProcessor httpproc = new BasicHttpProcessor();
			// 增加HTTP协议拦截器
			httpproc.addInterceptor(new ResponseDate());
			httpproc.addInterceptor(new ResponseServer());
			httpproc.addInterceptor(new ResponseContent());
			httpproc.addInterceptor(new ResponseConnControl());
			// 创建HTTP服务
			HttpService httpService = new HttpService(httpproc,
					new DefaultConnectionReuseStrategy(),
					new DefaultHttpResponseFactory());
			// 创建HTTP参数
			HttpParams params = new BasicHttpParams();
			params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 5000)
					.setIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE,
							8 * 1024)
					.setBooleanParameter(
							CoreConnectionPNames.STALE_CONNECTION_CHECK, false)
					.setBooleanParameter(CoreConnectionPNames.TCP_NODELAY, true)
					.setParameter(CoreProtocolPNames.ORIGIN_SERVER,
							"WebServer/1.1");
			// 设置HTTP参数
			httpService.setParams(params);
			// 创建HTTP请求执行器注册表
			HttpRequestHandlerRegistry reqistry = new HttpRequestHandlerRegistry();
			// 增加HTTP请求执行器

			reqistry.register("*", new DispatcherServlet());


			// 设置HTTP请求执行器
			httpService.setHandlerResolver(reqistry);
			/* 循环接收各客户端 */
			isLoop = true;
			while (isLoop && !Thread.interrupted()) {
				// 接收客户端套接字
				Socket socket = serverSocket.accept();
				// 绑定至服务器端HTTP连接
				DefaultHttpServerConnection conn = new DefaultHttpServerConnection();
				conn.bind(socket, params);
				// 派送至WorkerThread处理请求
				Thread t = new WorkerThread(httpService, conn);
				t.setDaemon(true); // 设为守护线程
				t.start();
			}
		} catch (IOException e) {
			isLoop = false;
			e.printStackTrace();
		} finally {
			try {
				if (serverSocket != null) {
					serverSocket.close();
				}
			} catch (IOException e) {
			}
		}
	}

	public void close() {
		isLoop = false;
	}



	/**
	 * 初始化控制器
	 */
	void initController(){


		for(String con:controllerName){
			try {
				Class c=Class.forName(con);
				controllers.add(c.newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		for(String inter:interceptPName){
			try {
				Class c=Class.forName(inter);
				c.newInstance();
				intercepts.add((ShInterceptor)c.newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * 设置要映射的控制器
	 * @param controllerName
	 */
	public void setControllerName(List<String> controllerName) {
		this.controllerName = controllerName;
	}

	/**
	 * 设置要映射的拦截器
	 * @param interceptPName
	 */
	public void setInterceptPName(List<String> interceptPName) {
		this.interceptPName = interceptPName;
	}
}
