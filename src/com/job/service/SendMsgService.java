package com.job.service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Properties;
/**发送远程命令服务类
 * 
 * @author xr
 *
 */
public class SendMsgService {

	// 发送报文编码格式
	private static final String ENCODING = "GBK";
	// 报文类型 03 车库提醒模式
	private static final String TYPE = "03";
	// 接收短信包 服务器地址（后期根据短信平台部署地址确定）
	private static String ip;
	// 接收短信包 端口
	private static int port;
	static{
		Properties prop = new Properties();     
        //读取属性文件a.properties
        InputStream in;
		try {
			in = SendMsgService.class.getResourceAsStream("/config/socket.properties");
			///加载属性列表
			prop.load(in);     
			ip = prop.getProperty("ip");
			port = Integer.valueOf(prop.getProperty("port"));
			in.close();
		} catch (Exception e) {
			System.out.println("读取socket配置文件出错！");
			e.printStackTrace();
		}
	}

	/**
	 * 按格式生成发送报文
	 * 
	 * 发送短信：长度+报文类型|手机号| 短信内容
	 */
	public String msgFormat(String manage_tel,String info) {
		StringBuffer returnMsg = new StringBuffer();
		String head = null;
		// 报文体
		String body = TYPE + "|" + manage_tel + "|" +info;
		try {
			// 获取报文长度 含自身长度8位
			head = String.format("%08d", Integer.valueOf(8) + body.getBytes(ENCODING).length);
		} catch (UnsupportedEncodingException e) {
			System.out.println("组装报文出错！");
		}
		returnMsg.append(head);
		returnMsg.append(body);
		return returnMsg.toString();
	}

	/**
	 * 发送报文
	 * 
	 * @param command
	 */
	public void sendMsg(String command) {
		Socket socket = null;
		try {
			// 创建一个流套接字并将其连接到指定主机上的指定端口号
			socket = new Socket(ip, port);
			// 读取服务器端数据
			DataInputStream input = new DataInputStream(socket.getInputStream());
			// 向服务器端发送数据
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			out.write(command.getBytes(ENCODING));
			out.close();
			input.close();
			System.out.print("成功连接："+ip+",向端口："+port+"发送报文："+command);
		} catch (Exception e) {
			System.out.println("客户端异常！"+e.getMessage());
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					System.out.println("尝试与客户端断开连接失败！"+e.getMessage());
				}
			}
		}
	}
}
