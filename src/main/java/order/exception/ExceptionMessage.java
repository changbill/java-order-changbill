package order.exception;

public enum ExceptionMessage {
    MINIMUM_PRICE_EXCEPTION("최소 주문 금액을 만족하지 못했습니다."),
    ONLY_DRINK_EXCEPTION("음료만으로는 주문할 수 없습니다."),
    MENU_QUANTITY_LIMIT("메뉴는 최대 10개까지 주문 가능합니다."),
    WRONG_MENU_NAME("주문 형식이 잘못되었습니다."),
    ;

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
