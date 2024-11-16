package order.model;

import java.util.EnumMap;
import java.util.Map;

public class OrderResponse {
    private final EnumMap<Menu, Integer> orderList;
    private final int deliveryFee;
    private final int serviceMandu;
    private final int totalOrderPrice;

    private OrderResponse(Order order) {
        this.orderList = order.getOrderList();
        this.deliveryFee = order.getDeliveryFee();
        this.serviceMandu = order.getServiceMandu();
        this.totalOrderPrice = order.getTotalOrderPrice();
    }

    public static OrderResponse from(Order order) {
        return new OrderResponse(order);
    }

    public String toPrettyString() {
        StringBuilder stringBuilder = new StringBuilder();
        orderDetailsToString(stringBuilder);
        totalOrderPriceToString(stringBuilder);
        serviceManduToString(stringBuilder);
        finalPaymentAmount(stringBuilder);

        return stringBuilder.toString();
    }

    private void finalPaymentAmount(StringBuilder stringBuilder) {
        stringBuilder.append(
                "[최종 결제 금액]" + "\n"
                + String.format("%,d", totalOrderPrice + deliveryFee) + "원"
        );
    }

    private void serviceManduToString(StringBuilder stringBuilder) {
        if(serviceMandu > 0) {
            stringBuilder.append(
                    "[서비스]" + "\n"
                    + "서비스 만두(" + serviceMandu + "개)" + "\n"
                    + "\n"
            );
        }
    }

    private void totalOrderPriceToString(StringBuilder stringBuilder) {
        stringBuilder.append(
                "총 주문 금액: " + String.format("%,d",totalOrderPrice) + "원" + "\n"
                + "배달비: " + String.format("%,d", deliveryFee) + "원" + "\n"
                + "\n");
    }

    private void orderDetailsToString(StringBuilder stringBuilder) {
        stringBuilder.append("[주문 내역]" + "\n");
        for (Map.Entry<Menu, Integer> order : orderList.entrySet()) {
            Menu menu = order.getKey();
            Integer quantity = order.getValue();
            stringBuilder.append(
                    menu.getMenuName()
                            + "(" + quantity + "개): "
                            + String.format("%,d", menu.getPrice() * quantity) + "원" + "\n"
            );
        }
    }
}
