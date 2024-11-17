package order.model;

import java.util.EnumMap;
import java.util.Map;

import static order.view.ViewConstant.*;

public class OrderResponse {
    private final EnumMap<Menu, Integer> orderList;
    private final int deliveryFee;
    private final int serviceManduQuantity;
    private final int totalOrderPrice;

    private OrderResponse(Order order) {
        this.orderList = order.getOrderList();
        this.deliveryFee = order.getDeliveryFee();
        this.serviceManduQuantity = order.getServiceMandu();
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
                FINAL_PAYMENT_AMOUNT_HEADER + "\n"
                + getThousandCommaFormat(totalOrderPrice + deliveryFee) + "원"
        );
    }

    private void serviceManduToString(StringBuilder stringBuilder) {
        if(serviceManduQuantity > 0) {
            stringBuilder.append(
                    SERVICE_HEADER + "\n"
                    + "서비스 만두(" + serviceManduQuantity + "개)" + "\n"
                    + "\n"
            );
        }
    }

    private void totalOrderPriceToString(StringBuilder stringBuilder) {
        stringBuilder.append(
                TOTAL_ORDER_PRICE_TITLE + getThousandCommaFormat(totalOrderPrice) + "원" + "\n"
                + DELIVERY_PRICE_TITLE + getThousandCommaFormat(deliveryFee) + "원" + "\n"
                + "\n");
    }

    private void orderDetailsToString(StringBuilder stringBuilder) {
        stringBuilder.append(ORDER_LIST_HEADER + "\n");
        for (Map.Entry<Menu, Integer> order : orderList.entrySet()) {
            Menu menu = order.getKey();
            Integer quantity = order.getValue();
            stringBuilder.append(
                    menu.getMenuName()
                            + "(" + quantity + "개): "
                            + getThousandCommaFormat(menu.getPrice() * quantity) + "원" + "\n"
            );
        }
    }

    private String getThousandCommaFormat(int value) {
        return String.format("%,d", value);
    }
}
