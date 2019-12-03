package com.sun.kq;

import com.sun.kq.util.ServiceUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KqApplication {
//develop分支
    public static void main(String[] args) {
        ServiceUtil.startMysql();
        ServiceUtil.startRedis();
        SpringApplication.run(KqApplication.class, args);
    }

}
