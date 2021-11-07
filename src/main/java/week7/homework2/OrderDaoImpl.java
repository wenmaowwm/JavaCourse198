package week7.homework2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;
import week7.homework2.dto.OrderInfo;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class OrderDaoImpl implements OrderDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;
    @Override
    public void insert(OrderInfo orderInfo) {
        String sql = "insert into order_info(order_id, order_time, buyer_id, item_id, item_cnt) values (?,?,?,?,?)";
        jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1, orderInfo.getOrderId());
                ps.setLong(2, orderInfo.getOrderTime());
                ps.setLong(3, orderInfo.getBuyerId());
                ps.setLong(4, orderInfo.getItemId());
                ps.setLong(5, orderInfo.getItemCnt());
            }
        });
    }

    @Override
    public void batchInsert(List<OrderInfo> list) {
        String sql = "insert into order_info(order_id, order_time, buyer_id, item_id, item_cnt) values (?,?,?,?,?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, list.get(i).getOrderId());
                ps.setLong(2, list.get(i).getOrderTime());
                ps.setLong(3, list.get(i).getBuyerId());
                ps.setLong(4, list.get(i).getItemId());
                ps.setLong(5, list.get(i).getItemCnt());
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
    }

    @Override
    public void truncate() {

    }
}
