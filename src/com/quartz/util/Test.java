package com.quartz.util;

/**测试类
 * 
 * @author xu-rui
 * 0 0/2 8-18 * * ? * 每天的8：00 - 18:00 每隔2分钟执行一次
 */
public class Test {

	public static void main(String args[]){
		
		QuartzManager quartzManager = new QuartzManager();
		ProcessJob testJob = new ProcessJob();
		quartzManager.addJob("ProcessJob", ProcessJob.class.getName(), "0 0/1 9-18 * * ?");
		//quartzManager.addJob("TestJob2", TestJob2.class.getName(), "0/2 * * * * ?");
		//quartzManager.removeJob("TestJob2");
	}
}
