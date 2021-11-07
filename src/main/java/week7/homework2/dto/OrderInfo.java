package week7.homework2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderInfo {
    Long orderId;
    Long orderTime;
    Long buyerId;
    Long itemId;
    Integer itemCnt;
}
