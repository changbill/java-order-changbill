package order.service;

import order.model.OrderResponse;

public interface OrderService {
    OrderResponse registerOrder(String orderInput);
}
