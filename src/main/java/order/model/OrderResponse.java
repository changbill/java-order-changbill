package order.model;

import order.exception.CustomException;

import java.util.EnumMap;

import static order.exception.ExceptionMessage.*;

public class OrderResponse {
    private final EnumMap<Menu, Integer> orderList;

    private OrderResponse(EnumMap<Menu, Integer> orderList) {
        this.orderList = orderList;
    }

    public static OrderResponse from(EnumMap<Menu, Integer> orderList) {
        validateOrderQuantity(orderList);
        validateMinimumOrderPrice(orderList);
        validateOnlyDrink(orderList);
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

    private static void validateOrderQuantity(EnumMap<Menu, Integer> orderList) {
        if(orderList.entrySet().stream().anyMatch(entry -> entry.getValue() > 10)) {
            throw new CustomException(MENU_QUANTITY_LIMIT.getMessage());
        }
    }

    private static void validateOnlyDrink(EnumMap<Menu, Integer> orderList) {
        if(orderList.entrySet().stream().allMatch(entry -> entry.getKey().getMenuType() == MenuType.DRINK)) {
            throw new CustomException(ONLY_DRINK_EXCEPTION.getMessage());
        }
    }
}
