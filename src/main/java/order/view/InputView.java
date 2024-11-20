package order.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static order.constant.ViewConstant.ORDER_INPUT_GUIDE;

public class InputView {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public String orderInput() throws IOException {
        System.out.println(ORDER_INPUT_GUIDE);
        return bufferedReader.readLine();
    }
}
