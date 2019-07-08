package com.sun.kq;

import com.sun.kq.util.ServiceUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KqApplication {

    public static void main(String[] args) {
        ServiceUtil.startMysql();
        ServiceUtil.startRedis();
        SpringApplication.run(KqApplication.class, args);
    }

}
