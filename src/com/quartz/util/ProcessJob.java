package com.quartz.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.job.service.JobService;
/**流程任务
 * 
 * @author xr
 * 
 *
 */
public class ProcessJob implements Job {

  public void execute(JobExecutionContext context) throws JobExecutionException {
	  JobService job = new JobService();
	  //设置日期格式
	  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  //获取当前时间
	  String nowTime = df.format(new Date());
	  String sql = "select p.card_id,p.card_type,p.in_out,p.plate_number,DATE_FORMAT(p.date_created,'%Y-%m-%d %H:%i:%S') date_created,c.user_name,c.phone from parking_record_real p left join card c on p.card_id = c.id where p.in_out='IN' and p.card_type='FREE' and DATE_FORMAT(p.date_created,'%Y-%m-%d') ='"+nowTime.substring(0, 10)+"'";
  	 // System.out.println(nowTime+"="+job.getCarInfo(sql));
      job.judge(nowTime, job.getCarInfo(sql));
   }
}