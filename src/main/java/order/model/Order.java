package order.model;

import order.exception.CustomException;

import java.util.EnumMap;
import java.util.Map;

import static order.exception.ExceptionMessage.*;
import static order.model.NumbersConstant.MAXIMUM_ORDER_QUANTITY;
import static order.model.NumbersConstant.MINIMUM_ORDER_PRICE;

public class Order {
    private final EnumMap<Menu, Integer> orderList;

    private Order(EnumMap<Menu, Integer> orderList) {
        this.orderList = orderList;
    }

    public static Order from(EnumMap<Menu, Integer> orderList) {
        validateOrderQuantity(orderList);
        validateMinimumOrderPrice(orderList);
        validateOnlyDrink(orderList);
        return new Order(orderList);
    }

    public int getDeliveryFee() {
        int totalOrderPrice = getTotalOrderPrice();
        if(totalOrderPrice < 50_000) {
            return 2_000;
        } else if (totalOrderPrice < 100_000) {
            return 1_000;
        }

        return 0;
    }

    public int getServiceMandu() {
        return sumByMenuType(orderList, MenuType.MAIN);
    }

    public int getTotalOrderPrice() {
        return calculateOrderPrice(orderList);
    }

    public EnumMap<Menu, Integer> getOrderList() {
        return orderList;
    }

    private static void validateMinimumOrderPrice(EnumMap<Menu, Integer> orderList) {
        int orderPrice = calculateOrderPrice(orderList);

        if(orderPrice < MINIMUM_ORDER_PRICE) {
            throw new CustomException(MINIMUM_PRICE_EXCEPTION.getMessage());
        }
    }

    private static void validateOrderQuantity(EnumMap<Menu, Integer> orderList) {
        if(orderList.entrySet().stream().anyMatch(entry -> entry.getValue() > MAXIMUM_ORDER_QUANTITY)) {
            throw new CustomException(MENU_QUANTITY_LIMIT.getMessage());
        }
    }

    private static void validateOnlyDrink(EnumMap<Menu, Integer> orderList) {
        if(orderList.entrySet().stream()
                .allMatch(entry -> entry.getKey().getMenuType() == MenuType.DRINK)) {
            throw new CustomException(ONLY_DRINK_EXCEPTION.getMessage());
        }
    }

    private int sumByMenuType(EnumMap<Menu, Integer> orderList, MenuType menuType) {
        return orderList.entrySet().stream()
                .filter(order -> order.getKey().getMenuType() == menuType)
                .mapToInt(Map.Entry::getValue)
                .sum();
    }

    private static int calculateOrderPrice(EnumMap<Menu, Integer> orderList) {
        return orderList.entrySet().stream()
                .mapToInt(entry -> {
                    int price = entry.getKey().getPrice();
                    Integer quantity = entry.getValue();

                    return price * quantity;
                })
                .sum();
    }
}
