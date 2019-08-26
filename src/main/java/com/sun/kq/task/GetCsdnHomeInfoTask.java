package com.sun.kq.task;

import com.sun.kq.service.CsdnService;
import com.sun.kq.service.impl.CsdnServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GetCsdnHomeInfoTask {

    @Autowired
    CsdnService csdnService;

    /**
     * //每隔5分钟获取csdn主页信息
     */
    //秒 分 时 日 月 周 年
    @Scheduled(cron = "0 */5 * * * ?")
    public void getCsdnHomeInfo() {
        csdnService.getCsdnHomeInfo();
        log.info("{}-定时任务-{}", CsdnServiceImpl.class, "获取csdn主页信息");
    }
}
