package service;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import utils.Log;
import utils.request.builder.OnRequestFailListener;
import utils.request.builder.OnRequestSuccessListener;
import utils.request.builder.RequestBuilder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;

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

    public static void addUser(String email, String pwd, String firstname, String lastname, int role, JSONArray tagd, JSONArray badges, OnRequestSuccessListener successListener, OnRequestFailListener failListener){
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

    public void editUser(String email, String pwd, String firstname, String lastname, int role, OnRequestSuccessListener successListener, OnRequestFailListener failListener) {
        RequestBuilder.builder()
                .setUrl(BASE_URL + EXTENDED_URL_USERS)
                .setRequestMethod("PUT")
                .addRequestBodyParameter(BODY_PARAMETER_EMAIL, email)
                .addRequestBodyParameter(BODY_PARAMETER_PASSWORD, pwd)
                .addRequestBodyParameter(BODY_PARAMETER_FIRSTNAME, firstname)
                .addRequestBodyParameter(BODY_PARAMETER_LASTNAME, lastname)
                .addRequestBodyParameter(BODY_PARAMETER_ROLE, role)
                .build();
    }

    public static void getUser(String id, OnRequestSuccessListener successListener, OnRequestFailListener failListener) {
        RequestBuilder.builder()
                .setUrl(BASE_URL + EXTENDED_URL_USERS + id)
                .setRequestMethod("GET")
                .addRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_CONTENT_TYPE_JSON)
                .setOnResponseSuccessListener(successListener)
                .setOnResponseFailListener(failListener)
                .build();
    }

    public static void setUserImage(String userId, File image){
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost uploadFile = new HttpPost(BASE_URL + EXTENDED_URL_USERS + userId + "/thumbnail");
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();

            // This attaches the file to the POST:
            builder.addBinaryBody(
                    "image",
                    new FileInputStream(image),
                    ContentType.APPLICATION_OCTET_STREAM,
                    image.getName()
            );

            HttpEntity multipart = builder.build();
            uploadFile.setEntity(multipart);
            CloseableHttpResponse response = httpClient.execute(uploadFile);
            HttpEntity responseEntity = response.getEntity();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
