package ru.yandex.praktikum.tests.order;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.praktikum.clients.OrderClient;
import ru.yandex.praktikum.tests.BaseTest;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.empty;

@Epic("Получение заказов")
@Feature("Получение заказов")
@Story("Список заказов")
public class GetOrderTest extends BaseTest {

    private static OrderClient orderClient;

    @BeforeAll
    static void setUpTest() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка, что API возвращает список заказов со статусом 200, список не пустой")
    public void getOrderListReturnsList() {
        Response response = orderClient.getList();

        response.then().assertThat()
                .statusCode(200)
                .and()
                .body("orders", not(empty()));
    }
}
