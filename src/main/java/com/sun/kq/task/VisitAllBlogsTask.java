package com.sun.kq.task;

import com.sun.kq.service.CsdnService;
import com.sun.kq.service.impl.CsdnServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VisitAllBlogsTask {

    @Autowired
    CsdnService csdnService;

    /**
     * //每隔2分钟访问csdn内的所有博文
     */
    //秒 分 时 日 月 周 年
    @Scheduled(cron = "0 */2 * * * ?")
    public void visitAllBlogs() {
        csdnService.visitAllBlogs();
        log.info("{}-定时任务-{}", CsdnServiceImpl.class, "访问所有博文");
    }
}
