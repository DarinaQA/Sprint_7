package ru.yandex.praktikum.tests.order;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.praktikum.clients.OrderClient;
import java.util.List;
import static org.hamcrest.Matchers.instanceOf;

@Epic("Получение заказов")
@Feature("Получение заказов")
@Story("Список заказов")
public class GetOrderTest {

    private static OrderClient orderClient;

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка, что API возвращает список заказов со статусом 200")
    public void getOrderListReturnsList() {
        Response response = orderClient.getList();

        response.then().assertThat()
                .statusCode(200)
                .and()
                .body("orders", instanceOf(List.class));
    }
}
