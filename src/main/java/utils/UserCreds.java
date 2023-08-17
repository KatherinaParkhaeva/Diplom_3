package utils;

import io.qameta.allure.Step;

public class UserCreds {
    private final String email;
    private final String password;

    public UserCreds(String login, String password) {
        this.email = login;
        this.password = password;
    }

    @Step("получение учетных данных пользователя")
    public static UserCreds credsFrom(User user) {
        return new UserCreds(user.getEmail(), user.getPassword());
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}