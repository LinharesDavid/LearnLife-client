package service;

import model.User;
import popup.PopupView;
import utils.Log;
import utils.request.builder.OnRequestFailListener;
import utils.request.builder.OnRequestSuccessListener;
import utils.request.builder.RequestBuilder;

import static utils.Constants.*;

public class UserService {

    public static void getAllUsers(OnRequestSuccessListener successListener, OnRequestFailListener failListener) {
        RequestBuilder.builder()
                .setUrl(BASE_URL + EXTENDED_URL_USERS)
                .setRequestMethod("GET")
                .addRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_CONTENT_TYPE_JSON)
                .setOnResponseSuccessListener(successListener)
                .setOnResponseFailListener(failListener)
                .build();
    }

    public static void deleteUser(String userId, OnRequestSuccessListener successListener, OnRequestFailListener failListener) {
        RequestBuilder.builder()
                .setUrl(BASE_URL + EXTENDED_URL_USERS + userId)
                .setRequestMethod("DELETE")
                .addRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_CONTENT_TYPE_JSON)
                .setOnResponseSuccessListener(successListener)
                .setOnResponseFailListener(failListener)
                .build();
    }

    public static void addUser(String email, String pwd, String firstname, String lastname, int role, OnRequestSuccessListener successListener, OnRequestFailListener failListener){
        RequestBuilder.builder()
                .setUrl(BASE_URL + EXTENDED_URL_USERS)
                .setRequestMethod("POST")
                .addRequestBodyParameter(BODY_PARAMETER_EMAIL, email)
                .addRequestBodyParameter(BODY_PARAMETER_PASSWORD, pwd)
                .addRequestBodyParameter(BODY_PARAMETER_FIRSTNAME, firstname)
                .addRequestBodyParameter(BODY_PARAMETER_LASTNAME, lastname)
                .addRequestBodyParameter(BODY_PARAMETER_ROLE, role)
                .setOnResponseSuccessListener(successListener)
                .setOnResponseFailListener(failListener)
                .build();
    }
}
