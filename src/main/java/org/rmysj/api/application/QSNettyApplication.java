package org.rmysj.api.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by rmysj on 2018/3/7 下午5:39.
 */
@SpringBootApplication
public class QSNettyApplication implements CommandLineRunner{

    public static void main(String[] args) {
        SpringApplication.run(QSNettyApplication.class, args);
    }
    @Override
    public void run(String... strings) throws Exception {

    }
}
