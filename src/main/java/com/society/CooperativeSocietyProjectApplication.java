package com.society;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@EnableScheduling
public class CooperativeSocietyProjectApplication {
    private static final Logger log = LoggerFactory.getLogger(CooperativeSocietyProjectApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CooperativeSocietyProjectApplication.class, args);
        try {
            log.info("\n--------------------------------------------------------------------------------------------------\n\t" +
                            "Application  is running! Access URLs:\n\t" +
                            "Local:    \thttp://localhost:{}\n\t" +
                            "External: \thttp://{}:{}\n \t \n" +
                            "--------------------------------------------------------------------------------------------------\n" +
                            "Server started successfully please don't close this window if want to close press [ctrl + c]\n" +
                            "--------------------------------------------------------------------------------------------------",
                    Integer.parseInt("2017"),
                    InetAddress.getLocalHost().getHostAddress(),
                    Integer.parseInt("2017")
            );
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
