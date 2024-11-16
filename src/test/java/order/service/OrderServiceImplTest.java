package order.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
    void registerOrderTest(String testInput) {
        assertDoesNotThrow(() -> orderService.registerOrder(testInput));
    }
}