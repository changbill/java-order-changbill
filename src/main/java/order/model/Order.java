package order.model;

import order.exception.CustomException;

import java.util.EnumMap;
import java.util.Map;

import static order.exception.ExceptionMessage.*;
import static order.constant.OrderConstant.*;

public class Order {
    private final EnumMap<Menu, Integer> orderList;

    private Order(EnumMap<Menu, Integer> orderList) {
        this.orderList = orderList;
    }

    public static Order from(EnumMap<Menu, Integer> orderList) {
        validateQuantity(orderList);
        validateMinimumOrderPrice(orderList);
        validateOnlyDrink(orderList);
        return new Order(orderList);
    }

    public int getDeliveryFee() {
        int totalOrderPrice = getTotalOrderPrice();
        if(totalOrderPrice < FIRST_TIER_STANDARD) {
            return FIRST_TIER_DELIVERY_FEE;
        } else if (totalOrderPrice < SECOND_TIER_STANDARD) {
            return SECOND_TIER_DELIVERY_FEE;
        }

        return THIRD_TIER_DELIVERY_FEE;
    }

    public int getServiceMandu() {
        return sumQuantityByMenuType(orderList, MenuType.MAIN);
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

    private static void validateQuantity(EnumMap<Menu, Integer> orderList) {
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

    private int sumQuantityByMenuType(EnumMap<Menu, Integer> orderList, MenuType menuType) {
        return orderList.entrySet().stream()
                .filter(order -> order.getKey().getMenuType() == menuType)
                .mapToInt(Map.Entry::getValue)
                .sum();
    }

    private static int calculateOrderPrice(EnumMap<Menu, Integer> orderList) {
        return orderList.entrySet().stream()
                .mapToInt(entry -> {
                    int price = entry.getKey().getPrice();
                    int quantity = entry.getValue();

                    return price * quantity;
                })
                .sum();
    }
}
