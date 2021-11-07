package week7.homework2;

import week7.homework2.dto.OrderInfo;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

public class OrderDetailProvider {
    public String batchInsertOrderDetail(Map<String, List<OrderInfo>> map) {
        List<OrderInfo> list = map.get("list");
        StringBuilder stringBuilder = new StringBuilder(256);
        stringBuilder.append("insert into order_info(order_id, order_time, buyer_id, item_id, item_cnt) values");
        MessageFormat messageFormat = new MessageFormat("(#{list[{0}].orderId,jdbcType=BIGINT}, " +
                "#{list[{0}].orderTime,jdbcType=BIGINT}, #{list[{0}].buyerId,jdbcType=BIGINT}, " +
                "#{list[{0}].itemId,jdbcType=BIGINT}, #{list[{0}].itemCnt,jdbcType=INTEGER})");
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append(messageFormat.format(new Integer[]{i}));
            stringBuilder.append(",");
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
