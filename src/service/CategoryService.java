package service;

import model.Category;
import utils.request.builder.OnRequestFailListener;
import utils.request.builder.OnRequestSuccessListener;
import utils.request.builder.RequestBuilder;

import static utils.Constants.*;

public class CategoryService {

    public static void getAllCategories(OnRequestSuccessListener successListener, OnRequestFailListener failListener) {
        RequestBuilder.builder()
                .setUrl(BASE_URL + EXTENDED_URL_CATEGORY)
                .setRequestMethod("GET")
                .addRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_CONTENT_TYPE_JSON)
                .setOnResponseSuccessListener(successListener)
                .setOnResponseFailListener(failListener)
                .build();
    }
}
