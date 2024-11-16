package order;

import order.controller.OrderController;
import order.model.OrderResponse;
import order.service.OrderParser;
import order.service.OrderServiceImpl;
import order.view.InputView;
import order.view.OutputView;

import java.io.IOException;

public class OrderSystem {
    private final OrderController orderController;
    private final InputView inputView;
    private final OutputView outputView;

    public OrderSystem() {
        inputView = new InputView();
        outputView = new OutputView();
        orderController = new OrderController(new OrderServiceImpl(new OrderParser()));
    }

    public void run() throws IOException {
        OrderResponse orderResponse = orderController.registerOrder(inputView.orderInput());
        outputView.printLine();
        outputView.print(orderResponse.toPrettyString());
    }
}
