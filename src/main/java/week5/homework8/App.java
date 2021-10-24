package week5.homework8;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        ConfigurableApplicationContext context =SpringApplication.run(App.class, args);
        School school = (School) context.getBean("createSchool");
        school.ding();
    }

}
