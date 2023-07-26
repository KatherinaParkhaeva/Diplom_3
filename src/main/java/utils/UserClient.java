package utils;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

import static config.Constants.MAIN_PAGE_URL;
import static io.restassured.RestAssured.given;

public class UserClient {
    public static final String REGISTER_PATH = "/api/auth/register";
    public static final String LOGIN_PATH = "/api/auth/login";
    public static final String USER_PATH = "/api/auth/user";
    private User user;
    private String accessToken;
    private static UserCreds userCreds;

    public UserClient() {
        RestAssured.baseURI = MAIN_PAGE_URL;
    }

    @Step("создание пользователя")
    public ValidatableResponse createUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(REGISTER_PATH)
                .then();
    }

    @Step("авторизация пользователя")
    public ValidatableResponse loginUser(UserCreds userCreds) {
        return given()
                .header("Content-type", "application/json")
                .body(userCreds)
                .when()
                .post(LOGIN_PATH)
                .then();
    }

    @Step("удаление пользователя")
    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .auth().oauth2(accessToken)
                .delete(USER_PATH)
                .then();
    }
}