package com.dingkoshop.dto;

import com.dingkoshop.constant.OrderStatus;
import com.dingkoshop.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class OrderHisDto {

    private Long orderId;
    private String orderDate;
    private OrderStatus orderStatus;
    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();

    public OrderHisDto(Order order) {
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = order.getOrderStatus();
    }

    public void addOrderItemDto(OrderItemDto orderItemDto) {
        orderItemDtoList.add(orderItemDto);
    }
}
