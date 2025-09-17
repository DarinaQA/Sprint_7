package ru.yandex.praktikum.clients;

import io.restassured.response.Response;
import ru.yandex.praktikum.data.Order;
import static io.restassured.RestAssured.given;

public class OrderClient {
    private static final String CREATE_ORDER = "/api/v1/orders";

    public Response create(Order order) {
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(CREATE_ORDER);
    }

    public Response getList() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(CREATE_ORDER);
    }
}