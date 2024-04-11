package com.study.emailservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
    private OrderStatus status;
    private String message;
    private Order order;
    private Date orderCreationDate;
    private Date orderLastUpdateDate;
}
