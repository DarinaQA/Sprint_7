package ru.yandex.praktikum.tests.login;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.yandex.praktikum.clients.CourierClient;
import ru.yandex.praktikum.data.Courier;
import org.junit.jupiter.params.provider.Arguments;
import ru.yandex.praktikum.tests.BaseTest;
import static org.hamcrest.Matchers.equalTo;
import java.util.stream.Stream;

@Epic("Авторизация курьера")
@Feature("Авторизация курьера")
@Story("Негативные сценарии")
public class LoginCourierNegativeTest extends BaseTest {

    private static CourierClient courierClient;

    @BeforeAll
    static void setUpTest() {
        courierClient = new CourierClient();
    }

    static Stream<Arguments> getTestData() {
        return Stream.of(
                Arguments.of(new Courier(null, "password123", null), 400, "Недостаточно данных для входа"),
                Arguments.of(new Courier("nonExistentLogin", "password", null), 404, "Учетная запись не найдена"),
                Arguments.of(new Courier("loginForLoginTest", "wrongPassword", null), 404, "Учетная запись не найдена")
        );
    }

    @ParameterizedTest(name = "Ошибка при логине: {2}")
    @MethodSource("getTestData")
    @DisplayName("Курьер не может авторизоваться с невалидными данными")
    @Description("Система возвращает ожидаемый статус-код с соответствующим сообщением при попытке авторизации с невалидными данными")
    void loginWithInvalidDataReturnsError(Courier courier, int expectedStatusCode, String expectedErrorMessage) {
        Response response = courierClient.login(courier);
        response.then().assertThat()
                .statusCode(expectedStatusCode)
                .and()
                .body("message", equalTo(expectedErrorMessage));
    }
}
