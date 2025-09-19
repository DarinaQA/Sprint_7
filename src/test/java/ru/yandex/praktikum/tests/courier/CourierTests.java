package ru.yandex.praktikum.tests.courier;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.yandex.praktikum.clients.CourierClient;
import ru.yandex.praktikum.data.Courier;
import org.junit.jupiter.params.provider.Arguments;
import ru.yandex.praktikum.tests.BaseTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import java.util.stream.Stream;

@Epic("Создание курьера")
@Feature("Создание курьера")
@Story("Негативные сценарии")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CourierTests extends BaseTest {

    private CourierClient courierClient;

    @BeforeAll
    public void init() {
        courierClient = new CourierClient();
    }

    static Stream<Arguments> courierTestData() {
        return Stream.of(
                Arguments.of(new Courier(null, "password123", "firstname"), 400, "Недостаточно данных для создания учетной записи"),
                Arguments.of(new Courier("login123", null, "firstname"), 400, "Недостаточно данных для создания учетной записи")
        );
    }

    @ParameterizedTest(name = "Тест-кейс: {2}")
    @MethodSource("courierTestData")
    @DisplayName("Создание курьера с недостаточным набором данных")
    @Description("При отсутствии обязательных полей система возвращает ожидаемый статус-код с соответствущим сообщением")
    public void createCourierWithoutRequiredField(Courier courier, int expectedStatusCode, String expectedErrorMessage) {
        Response response = courierClient.create(courier);
        assertThat(response.statusCode(), equalTo(expectedStatusCode));
        assertThat(response.jsonPath().getString("message"), equalTo(expectedErrorMessage));
    }
}
