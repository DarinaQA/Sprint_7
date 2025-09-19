package ru.yandex.praktikum.clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.data.Courier;
import static io.restassured.RestAssured.given;

public class CourierClient {

    private static final String CREATE_COURIER = "/api/v1/courier";
    private static final String LOGIN_COURIER = "/api/v1/courier/login";
    private static final String DELETE_COURIER = "/api/v1/courier/{id}";

    @Step("Создание курьера с логином {courier.login} и паролем {courier.password}")
    public Response create(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(CREATE_COURIER);
    }

    @Step("Авторизация курьера с логином {courier.login} и паролем {courier.password}")
    public Response login(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(LOGIN_COURIER);
    }

    @Step("Удаление курьера по ID: {id}")
    public Response delete(int courierId) {
        return given()
                .header("Content-type", "application/json")
                .pathParam("id", courierId)
                .when()
                .delete(DELETE_COURIER);
    }
}