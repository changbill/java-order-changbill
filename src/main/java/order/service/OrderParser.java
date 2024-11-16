package order.service;

import order.exception.CustomException;
import order.exception.ExceptionMessage;
import order.model.Menu;
import order.model.OrderResponse;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderParser {
    public OrderResponse parseOrder(String orderInput) {
        List<String> splitOrders =
                Arrays.stream(orderInput.split(","))
                        .map(String::trim)
                        .toList();

        EnumMap<Menu, Integer> orderList = new EnumMap<>(Menu.class);
        Pattern pattern = Pattern.compile("([가-힣\\s]+)\\(([0-9]+)개\\)");

        splitOrders.forEach(splitOrder -> {
            Matcher matcher = pattern.matcher(splitOrder);
            if(matcher.matches()) {
                String menuName = matcher.group(1);
                int quantity = Integer.parseInt(matcher.group(2));
                orderList.put(Menu.parseToMenu(menuName), quantity);
            } else if(!matcher.matches()){
                throw new CustomException(ExceptionMessage.WRONG_MENU_NAME.getMessage());
            }
        });

        return OrderResponse.from(orderList);
    }
}
