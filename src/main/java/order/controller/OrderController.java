package order.controller;

import order.model.OrderResponse;
import order.service.OrderService;

public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public OrderResponse registerOrder(String orderInput) {
        return orderService.registerOrder(orderInput);
    }
}
