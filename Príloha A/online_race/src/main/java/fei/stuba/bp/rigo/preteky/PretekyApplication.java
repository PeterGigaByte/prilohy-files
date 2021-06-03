package fei.stuba.bp.rigo.preteky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class PretekyApplication {


    public static void main(String[] args) {
        SpringApplication.run(PretekyApplication.class, args);
    }
}
