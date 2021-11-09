package week7.homework9;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import week7.homework2.OrderDao;
import week7.homework2.dto.OrderInfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Autowired
    T1Dao t1Dao;

    @Bean
    public void test() throws SQLException {
        System.out.println(t1Dao.getAll());
        t1Dao.insert(7);
        t1Dao.insert(8);
        System.out.println(t1Dao.getAll());
        t1Dao.deleteById(7);
        t1Dao.deleteById(8);
    }
}
