package week7.homework2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import week5.homework10.Dto.User;
import week5.homework10.UserDao;
import week7.homework2.dto.OrderInfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(week7.homework2.App.class, args);
    }

    @Autowired
    OrderDao orderDao;

    @Bean
    public void test() throws SQLException {
        orderDao.truncate();
        List<OrderInfo> list = new ArrayList<>(1000001);
        for (long i = 0; i<1000000; i++){
            list.add(new OrderInfo(100L+i,System.currentTimeMillis(), i, 111L+i,1));
        }

        Long s1 = System.currentTimeMillis();
        for (OrderInfo orderInfo : list) {
            orderDao.insert(orderInfo);
        }
        Long s2 = System.currentTimeMillis();
        System.out.println((s2 - s1)/1000);
        orderDao.truncate();

        Long s3 = System.currentTimeMillis();
        orderDao.batchInsert(list);
        Long s4 = System.currentTimeMillis();
        System.out.println((s4 - s3)/1000);
        orderDao.truncate();
    }
}
