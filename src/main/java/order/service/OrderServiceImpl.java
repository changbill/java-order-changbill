package order.service;

import order.model.OrderResponse;

public class OrderServiceImpl implements OrderService {
    private final OrderParser orderParser;

    public OrderServiceImpl(OrderParser orderParser) {
        this.orderParser = orderParser;
    }

    public OrderResponse registerOrder(String orderInput) {
        return orderParser.parseOrder(orderInput);
    }
}
