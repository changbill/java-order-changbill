package order.service;

import order.exception.CustomException;
import order.exception.ExceptionMessage;
import order.model.Menu;
import order.model.Order;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderParser {
    private static final String ORDER_REGEX = "([가-힣\\s]+)\\(([0-9]+)개\\)";

    public Order parseOrder(String orderInput) {
        List<String> splitOrders =
                Arrays.stream(orderInput.split(","))
                        .map(String::trim)
                        .toList();

        return Order.from(getOrderList(splitOrders));
    }

    private static EnumMap<Menu, Integer> getOrderList(List<String> splitOrders) {
        EnumMap<Menu, Integer> orderList = new EnumMap<>(Menu.class);

        splitOrders.forEach(splitOrder -> {
            Matcher matcher = Pattern.compile(ORDER_REGEX).matcher(splitOrder);
            if(matcher.matches()) {
                String menuName = matcher.group(1);
                int quantity = Integer.parseInt(matcher.group(2));
                orderList.put(Menu.parseToMenu(menuName), quantity);
            } else if(!matcher.matches()){
                throw new CustomException(ExceptionMessage.WRONG_MENU_NAME.getMessage());
            }
        });

        return orderList;
    }
}
