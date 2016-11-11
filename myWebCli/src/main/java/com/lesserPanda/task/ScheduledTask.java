package com.lesserPanda.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时执行任务, 如果没有定时任务请删除此类
 *
 */
@Component
public class ScheduledTask {

  // 每天凌晨2点触发
  @Scheduled(cron = "0 0 2 * * ?")  
  public void job1() {
    // TODO your first task
  }
  
  // 每隔2个小时触发一次
  @Scheduled(cron = "0 */2 * * * ?")  
  public void job2() {
    // TODO your second task
  }
  
}
