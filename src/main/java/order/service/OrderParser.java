package order.service;

import order.exception.CustomException;
import order.exception.ExceptionMessage;
import order.model.Menu;
import order.model.Order;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static order.constant.ViewConstant.ORDER_INPUT_DELIMITER;

public class OrderParser {
    private static final String ORDER_REGEX = "([가-힣\\s]+)\\(([0-9]+)개\\)";     // 예: 오렌지 주스(5개)

    public Order parseOrder(String orderInput) {
        List<String> splitOrders =
                Arrays.stream(orderInput.split(ORDER_INPUT_DELIMITER))
                        .map(String::trim)
                        .toList();

        return Order.from(getOrderList(splitOrders));
    }

    private static EnumMap<Menu, Integer> getOrderList(List<String> splitOrders) {
        return splitOrders.stream()
                .map(OrderParser::parseOrderItem)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Integer::sum, // 중복된 메뉴 이름의 수량을 합산
                        () -> new EnumMap<>(Menu.class)
                ));
    }

    private static Map.Entry<Menu, Integer> parseOrderItem(String splitOrder) {
        Matcher matcher = Pattern.compile(ORDER_REGEX).matcher(splitOrder);
        if (!matcher.matches()) {
            throw new CustomException(ExceptionMessage.WRONG_MENU_NAME.getMessage());
        }

        String menuName = matcher.group(1);
        int quantity = Integer.parseInt(matcher.group(2));
        return Map.entry(Menu.ofMenuName(menuName), quantity);
    }
}
