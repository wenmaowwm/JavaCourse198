package week7.homework2;

import org.apache.ibatis.annotations.*;
import week7.homework2.dto.OrderInfo;

import java.util.List;

//@Mapper
public interface OrderDao {
    //@Options(useGeneratedKeys = true, keyColumn = "id")
    //@Insert("insert into order_info(order_id, order_time, buyer_id, item_id, item_cnt) values (#{orderInfo.orderId},#{orderInfo.orderTime},#{orderInfo.buyerId},#{orderInfo.itemId},#{orderInfo.itemCnt})")
    void insert(@Param("orderInfo")OrderInfo orderInfo);

    void batchInsert(List<OrderInfo> list);

    //@Update("truncate table order_info")
    void truncate();
}
