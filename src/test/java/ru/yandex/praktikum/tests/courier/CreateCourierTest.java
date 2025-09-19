package ru.yandex.praktikum.tests.courier;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import ru.yandex.praktikum.clients.CourierClient;
import ru.yandex.praktikum.data.Courier;
import ru.yandex.praktikum.tests.BaseTest;
import static org.hamcrest.Matchers.equalTo;

@Epic("Создание курьера")
@Feature("Создание курьера")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreateCourierTest extends BaseTest {

    private CourierClient courierClient;
    private Integer courierId;

    @BeforeEach
    public void setUpTest() {
        courierClient = new CourierClient();
    }

    @AfterEach
    public void tearDown() {
        if (courierId != null) {
            courierClient.delete(courierId);
        }
    }

    @Test
    @Story("Позитивный сценарий создания курьера")
    @DisplayName("Успешное создание нового курьера")
    @Description("Создание курьера с полным набором валидных данных")
    public void courierCanBeCreatedSuccessfully() {
        Courier courier = new Courier("Alex02", "1234", "Alex");
        Response createResponse = courierClient.create(courier);
        createResponse.then().assertThat().statusCode(201)
                .and()
                .body("ok", equalTo(true));
        Response loginResponse = courierClient.login(courier);
        courierId = loginResponse.then().extract().path("id");
    }

    @Test
    @Story("Негативный сценарий создания дубликата курьера")
    @DisplayName("Система не допускает создание двух курьеров с одинаковыми логинами")
    @Description("При попытке создать курьера с уже существующим логином, система возвращает ожидаемый статус-код с соответствущим сообщением")
    public void cannotCreateTwoIdenticalCouriers() {
        Courier courier = new Courier("Oleg01", "5678", "Oleg");
        courierClient.create(courier);
        Response duplicateCreateResponse = courierClient.create(courier);
        duplicateCreateResponse.then().assertThat().statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        Response loginResponse = courierClient.login(courier);
        courierId = loginResponse.then().extract().path("id");
    }
}
