package week5.homework10;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import week5.homework10.Dto.User;
import week5.homework10.impl.UserDaoImpl;

import java.sql.SQLOutput;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(week5.homework10.App.class, args);
    }

    @Autowired
    UserDao userDao;

    @Bean
    public void test(){
        System.out.println("begin");
        userDao.addUser(new User(1L,"wwm"));
        userDao.updateUser(new User(1L,"wwm123"));
        System.out.println(userDao.queryUser(1));
        userDao.deleteUser(1);
    }
}
