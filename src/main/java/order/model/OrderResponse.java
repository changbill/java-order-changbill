package order.model;

import order.exception.CustomException;

import java.util.EnumMap;

import static order.exception.ExceptionMessage.MINIMUM_PRICE_EXCEPTION;

public class OrderResponse {
    private final EnumMap<Menu, Integer> orderList;

    private OrderResponse(EnumMap<Menu, Integer> orderList) {
        this.orderList = orderList;
    }

    public static OrderResponse from(EnumMap<Menu, Integer> orderList) {
        validateMinimumOrderPrice(orderList);
        return new OrderResponse(orderList);
    }

    private static void validateMinimumOrderPrice(EnumMap<Menu, Integer> orderList) {
        int orderPrice = orderList.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();

        if(orderPrice < 30_000) {
            throw new CustomException(MINIMUM_PRICE_EXCEPTION.getMessage());
        }
    }
}
