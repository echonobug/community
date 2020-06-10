package fun.jwei.community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("fun.jwei.community.mapper")
public class SpringbootBeginApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootBeginApplication.class, args);
    }

}
