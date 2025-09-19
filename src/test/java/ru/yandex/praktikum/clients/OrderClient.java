package ru.yandex.praktikum.clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.data.Order;
import static io.restassured.RestAssured.given;

public class OrderClient {
    private static final String CREATE_ORDER = "/api/v1/orders";

    @Step("Создание заказа")
    public Response create(Order order) {
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(CREATE_ORDER);
    }

    @Step("Получение списка заказов")
    public Response getList() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(CREATE_ORDER);
    }
}