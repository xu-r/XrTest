package com.job.service;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import com.dao.util.Dao;
import com.dao.util.DaoImpl;

/**定时任务 实现类
 * 
 * @author xr
 *
 */
public class JobService {
	
	//最大发送次数
	private static int maxNumber;
	//发送短信 时间间隔 单位分钟
	private static int time;
	//是否带客户姓名 0不带 ；1带
	private static String isAddCustomerName;
	static {
		Properties prop = new Properties();     
        //读取属性文件a.properties
        InputStream in;
		try {
			in =JobService.class.getResourceAsStream("/config/config.properties");
			///加载属性列表
			prop.load(in);     
			maxNumber = Integer.valueOf(prop.getProperty("maxNumber"));
			time = Integer.valueOf(prop.getProperty("time"));
			isAddCustomerName = prop.getProperty("isAddCustomerName");
			in.close();
		} catch (Exception e) {
			System.out.println("读取发送短信配置文件出错！");
			e.printStackTrace();
		}
	}

	/**根据Sql 获取查询集合
	 * 
	 * @param sql
	 * @return
	 */
	public List<Map<String,Object>> getCarInfo(String sql){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		//创建数据库连接
		Dao dao = new DaoImpl();
	    list = dao.select(sql);
		return list;
	}
	/**逻辑判断 计算停车时间
	 * 
	 * @param list
	 */
	public void judge(String nowTime,List<Map<String,Object>> list){
		if(list.size()>0){
		for(Map<String,Object> map : list){
			//入库时间
			String date_created = String.valueOf(map.get("date_created"));
			//电话号码
			String phone = String.valueOf(map.get("phone"));
			//用户名
			String user_name = String.valueOf(map.get("user_name"));
			//车牌号
			String plate_number = String.valueOf(map.get("plate_number"));
			int timeLength = getDistanceTime(date_created,nowTime);
			//System.out.println("timeLength："+timeLength);
			if(timeLength<time*60 ||timeLength>maxNumber*time*60){
				System.out.println("车辆["+plate_number+"]停放时间不在发送短信范围内！");
				continue;
			}else{
					if(timeLength%(time*60)==0){
						for(int i=1;i<=maxNumber;i++){
						if(timeLength/(time*60)==i){
							SendMsgService sms = new SendMsgService();
							//短信内容不带客户姓名
							if("0".equals(isAddCustomerName)){
								String command = sms.msgFormat(phone, "尊敬的客户您好,您的车["+plate_number+"]已经在车库中停放了"+(time/60)*i+"个小时.");
								sms.sendMsg(command);
							//短信内容带客户姓名
							}else if("1".equals(isAddCustomerName)){
								String command = sms.msgFormat(phone, user_name+"您好,您的车["+plate_number+"]已经在车库中停放了"+(time/60)*i+"个小时.");
								sms.sendMsg(command);
							}else{
								break;
							}
						 }
					 }
					}else{
						continue;
					}
			}
		}
		}else{
			System.out.println("车库中当前无停放车辆！");
		}
	}
	
	/**日期计算
	 * 返回相差 单位秒  
	 * 计算时精确到分 省略后面的秒 
	 */
    public int getDistanceTime(String startTime, String endTime) {  
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date one;  
        Date two;  
        long day = 0;  
        long hour = 0;  
        long min = 0;  
        long sec = 0;  
        try {  
            one = df.parse(startTime);  
            two = df.parse(endTime);  
            long time1 = one.getTime();  
            long time2 = two.getTime();  
            long diff ;  
            if(time1<time2) {  
                diff = time2 - time1;  
            } else {  
                diff = time1 - time2;  
            }  
            day = diff / (24 * 60 * 60 * 1000);  
            hour = (diff / (60 * 60 * 1000) - day * 24);  
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);  
            sec = (diff/1000-day*24*60*60-hour*60*60-min*60);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        } 
        //return day + "天" + hour + "小时" + min + "分" + sec + "秒";  
        return Integer.valueOf((int)(day*24*60*60 + hour*60*60 + min*60));
    }  
 
	
	/**测试
	 * 
	 * @param args
	 */
/*	public static void main(String args[]){
		JobService job = new JobService();
		int date = job.getDistanceTime("2016-05-31 13:55:00","2016-05-31 15:56:00");
		System.out.println("date="+date);
	}*/
}
