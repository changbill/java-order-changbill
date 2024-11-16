import order.controller.OrderController;
import order.service.OrderParser;
import order.service.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationTest {
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        orderController = new OrderController(new OrderServiceImpl(new OrderParser()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"감자튀김(5개), 제로 콜라(5개), 치즈스틱(7개)",
            "샐러드(5개), 콜라(5개)"})
    void 서비스만두_없는_경우_테스트(String input) {
        assertThat(orderController.registerOrder(input).toPrettyString()).doesNotContain("[서비스]");
    }

    @ParameterizedTest
    @ValueSource(strings = {"피자(4개), 감자튀김(5개), 제로 콜라(5개), 치즈스틱(7개)",
            "햄버거(4개),샐러드(5개), 콜라(5개)"})
    void 서비스만두_있는_경우_테스트(String input) {
        assertThat(orderController.registerOrder(input).toPrettyString()).contains("[서비스]");
    }

    @ParameterizedTest
    @ValueSource(strings = {"피자(4개)", "햄버거(4개)", "치킨(4개)"})
    void 서비스만두_개수_테스트(String input) {
        assertThat(orderController.registerOrder(input).toPrettyString()).contains(
                """
                [서비스]
                서비스 만두(4개)"""
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"치킨(2개)","햄버거(3개)","피자(1개),감자튀김(1개)"})
    void 배달비_50000원_미만_테스트(String input) {
        assertThat(orderController.registerOrder(input).toPrettyString()).contains("배달비: 2,000원");
    }

    @ParameterizedTest
    @ValueSource(strings = {"치킨(3개)","햄버거(3개),피자(2개)","치즈스틱(10개),감자튀김(1개)"})
    void 배달비_50000원_이상_100000원_미만_테스트(String input) {
        assertThat(orderController.registerOrder(input).toPrettyString()).contains("배달비: 1,000원");
    }

    @ParameterizedTest
    @ValueSource(strings = {"치킨(10개)","햄버거(10개)","치즈스틱(10개),감자튀김(10개)"})
    void 배달비_100000원_이상_테스트(String input) {
        assertThat(orderController.registerOrder(input).toPrettyString()).contains("배달비: 0원");
    }

    @Test
    void 정상_테스트1() {
        String input = "햄버거(5개), 샐러드(2개), 콜라(3개)";

        assertThat(orderController.registerOrder(input).toPrettyString()).isEqualTo(
                """
                        [주문 내역]
                        햄버거(5개): 50,000원
                        샐러드(2개): 16,000원
                        콜라(3개): 6,000원
                        총 주문 금액: 72,000원
                        배달비: 1,000원
                        
                        [서비스]
                        서비스 만두(5개)
                        
                        [최종 결제 금액]
                        73,000원"""
        );
    }

    @Test
    void 정상_테스트2() {
        String input = "감자튀김(9개), 샐러드(8개), 콜라(8개), 오렌지 주스(9개)";

        assertThat(orderController.registerOrder(input).toPrettyString()).isEqualTo(
                """
                       [주문 내역]
                       감자튀김(9개): 45,000원
                       샐러드(8개): 64,000원
                       콜라(8개): 16,000원
                       오렌지 주스(9개): 27,000원
                       총 주문 금액: 152,000원
                       배달비: 0원
                
                       [최종 결제 금액]
                       152,000원"""
        );
    }
}
