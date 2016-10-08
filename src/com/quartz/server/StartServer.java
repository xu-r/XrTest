package com.quartz.server;

import com.quartz.util.ProcessJob;
import com.quartz.util.QuartzManager;

/**平台启动程序
 * 
 * @author xr
 * 
 */
public class StartServer {
	public static void main(String args[]){
		//获取定时任务管理类
		QuartzManager quartzManager = new QuartzManager();
	    //每天8：00-18：00点 隔一分钟执行一次
		String cronExpression = "0 0/1 8-18 * * ?";
		quartzManager.addJob("ProcessJob", ProcessJob.class.getName(), cronExpression);
	}
}
