package order.service;

import order.model.Order;
import order.model.OrderResponse;

public class OrderServiceImpl implements OrderService {

    public OrderResponse registerOrder(String orderInput) {
        Order order = OrderParser.parseOrder(orderInput);
        return OrderResponse.from(order);
    }
}
