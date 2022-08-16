package com.ryhnik;

import com.ryhnik.service.DropBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ControllerApplication implements CommandLineRunner {

    @Autowired
    private DropBoxService dropBoxService;

    public static void main(String[] args) {
        SpringApplication.run(ControllerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        dropBoxService.saveImage();
    }
}
