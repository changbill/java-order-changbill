package order.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static order.exception.ExceptionMessage.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderServiceImpl(new OrderParser());
    }

    @ParameterizedTest
    @ValueSource(strings = {"햄버거(5개), 샐러드(2개), 콜라(3개)",
            "피자(2개), 감자튀김(1개), 콜라(3개)",
            "오렌지 주스(1개),감자튀김(10개),햄버거(5개)"})
    void 메뉴_이름_검증_테스트(String testInput) {
        assertDoesNotThrow(() -> orderService.registerOrder(testInput));
    }

    @ParameterizedTest
    @ValueSource(strings = {"주먹밥(5개), 샐러드(2개), 콜라(3개)",
    "피자(10개),감자튀김(1개),콜라(개)"})
    void 메뉴_이름_검증_예외테스트(String testInput) {
        assertThatThrownBy(() -> orderService.registerOrder(testInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]: ")
                .hasMessageContaining(WRONG_MENU_NAME.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"햄버거(11개), 샐러드(100개), 콜라(3개)",
            "햄버거(100개), 샐러드(2개), 콜라(3개)"})
    void 메뉴_주문개수제한_예외테스트(String testInput) {
        assertThatThrownBy(() -> orderService.registerOrder(testInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]: ")
                .hasMessageContaining(MENU_QUANTITY_LIMIT.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"햄버거(1개)",
            "피자(1개),콜라(1개)"})
    void 메뉴_최소가격제한_예외테스트(String testInput) {
        assertThatThrownBy(() -> orderService.registerOrder(testInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]: ")
                .hasMessageContaining(MINIMUM_PRICE_EXCEPTION.getMessage());
    }
}