package ru.yandex.praktikum.tests.order;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.yandex.praktikum.clients.OrderClient;
import ru.yandex.praktikum.data.Order;
import java.util.stream.Stream;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.params.provider.Arguments;

@Epic("Создание заказа")
@Feature("Создание заказа")
@Story("Успешное создание заказа с разными вариантами цветов самоката")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreateOrderTest {

    private OrderClient orderClient;

    @BeforeAll
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
        orderClient = new OrderClient();
    }

    static Stream<Arguments> colorsProvider() {
        return Stream.of(
                Arguments.of((Object) new String[]{"BLACK"}),
                Arguments.of((Object) new String[]{"GREY"}),
                Arguments.of((Object) new String[]{"BLACK", "GREY"}),
                Arguments.of((Object) new String[]{})
        );
    }

    @ParameterizedTest(name = "Создание заказа с цветами: {0}")
    @MethodSource("colorsProvider")
    @DisplayName("Создание заказа с разными цветами")
    @Description("Система допускает возможность создания заказа с любым набором цветов: один, несколько или без указания")
    public void createOrderWithDifferentColorsIsSuccessful(String[] colors) {
        Order order = new Order(
                "Alisa",
                "Ivanova",
                "Москва, ул. Лесная, д. 28",
                "123",
                "+79776535335",
                3,
                "2020-06-06",
                "Позвонить за полчаса",
                colors
        );

        Response response = orderClient.create(order);
        response.then().assertThat()
                .statusCode(201)
                .and()
                .body("track", notNullValue());
    }
}
