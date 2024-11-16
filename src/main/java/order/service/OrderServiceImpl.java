package order.service;

import order.model.Order;
import order.model.OrderResponse;

public class OrderServiceImpl implements OrderService {
    private final OrderParser orderParser;

    public OrderServiceImpl(OrderParser orderParser) {
        this.orderParser = orderParser;
    }

    public OrderResponse registerOrder(String orderInput) {
        Order order = orderParser.parseOrder(orderInput);
        return OrderResponse.from(order);
    }
}
