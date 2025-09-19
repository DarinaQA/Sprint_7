package ru.yandex.praktikum.tests.login;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import ru.yandex.praktikum.clients.CourierClient;
import ru.yandex.praktikum.data.Courier;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Авторизация курьера")
@Feature("Авторизация курьера")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginCourierTest {

    private CourierClient courierClient;
    private Courier courier;
    private Integer courierId;

    @BeforeAll
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
        courierClient = new CourierClient();
        courier = new Courier("Petr01", "12345", "Petr");
        courierClient.create(courier);
    }

    @AfterAll
    public void tearDown() {
        if (courierId != null) {
            courierClient.delete(courierId);
        }
    }

    @Test
    @Story("Успешная авторизация в системе")
    @DisplayName("Курьер может авторизоваться с корректными данными")
    @Description("Курьер успешно авторизуется в системе по логину и паролю, в теле ответа возвращается id")
    public void courierCanLoginSuccessfully() {
        Response loginResponse = courierClient.login(courier);
        int statusCode = loginResponse.statusCode();
        assertEquals(200, statusCode, "Статус код неверный");
        courierId = loginResponse.then().extract().path("id");
        assertThat(loginResponse.then().extract().path("id"), notNullValue());
    }
}
