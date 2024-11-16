package order.model;

import order.exception.CustomException;

import java.util.Arrays;

import static order.exception.ExceptionMessage.WRONG_MENU_NAME;
import static order.model.MenuType.*;

public enum Menu {
    PIZZA(MAIN, "피자", 25_000),
    HAMBURGER(MAIN, "햄버거", 10_000),
    CHICKEN(MAIN, "치킨", 23_000),
    FRENCH_FRIES(SIDE, "감자튀김", 5_000),
    CHEESE_STICK(SIDE, "치즈스틱", 7_000),
    SALAD(SIDE, "샐러드", 8_000),
    COKE(DRINK, "콜라", 2_000),
    ZERO_COKE(DRINK, "제로 콜라", 2_500),
    ORANGE_JUICE(DRINK, "오렌지 주스", 3_000),
    ;

    private final MenuType menuType;
    private final String menuName;
    private final int price;

    Menu(MenuType menuType, String menuName, int price) {
        this.menuType = menuType;
        this.menuName = menuName;
        this.price = price;
    }

    public static Menu parseToMenu(String menuName) {
        return Arrays.stream(Menu.values())
                .filter(menu -> menu.menuName.equals(menuName))
                .findFirst()
                .orElseThrow(() -> new CustomException(WRONG_MENU_NAME.getMessage()));
    }

    public MenuType getMenuType() {
        return menuType;
    }

    public String getMenuName() {
        return menuName;
    }

    public int getPrice() {
        return price;
    }
}
