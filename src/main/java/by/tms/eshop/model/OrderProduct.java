package by.tms.eshop.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderProduct {

    private Long id;
    private Long orderId;
    private Long productId;
}