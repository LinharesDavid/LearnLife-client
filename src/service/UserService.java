package service;

import model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import popup.PopupView;
import utils.Log;
import utils.request.builder.OnRequestFailListener;
import utils.request.builder.OnRequestSuccessListener;
import utils.request.builder.RequestBuilder;

import java.awt.image.*;
import java.io.*;
import java.lang.management.BufferPoolMXBean;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

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

    public static void setUserImage(String userId, BufferedImage image){
        try {
            String attachmentName = "bitmap";
            String attachmentFileName = "bitmap.bmp";
            String crlf = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";

            HttpURLConnection httpUrlConnection = null;
            URL url = new URL(BASE_URL + EXTENDED_URL_USERS);
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setDoOutput(true);

            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
            httpUrlConnection.setRequestProperty("Cache-Control", "no-cache");
            httpUrlConnection.setRequestProperty("Content-Type", "application/json");
            httpUrlConnection.setRequestProperty(
                    "Content-Type", "multipart/form-data;boundary=" + boundary);

            DataOutputStream request = new DataOutputStream(
                    httpUrlConnection.getOutputStream());

            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"" +
                    attachmentName + "\";filename=\"" +
                    attachmentFileName + "\"" + crlf);
            request.writeBytes(crlf);

            ByteBuffer byteBuffer;
            DataBuffer dataBuffer = image.getData().getDataBuffer();
            if (dataBuffer instanceof DataBufferByte) {
                byte[] pixelData = ((DataBufferByte) dataBuffer).getData();
                byteBuffer = ByteBuffer.wrap(pixelData);
            }
            else if (dataBuffer instanceof DataBufferUShort) {
                short[] pixelData = ((DataBufferUShort) dataBuffer).getData();
                byteBuffer = ByteBuffer.allocate(pixelData.length * 2);
                byteBuffer.asShortBuffer().put(ShortBuffer.wrap(pixelData));
            }
            else if (dataBuffer instanceof DataBufferShort) {
                short[] pixelData = ((DataBufferShort) dataBuffer).getData();
                byteBuffer = ByteBuffer.allocate(pixelData.length * 2);
                byteBuffer.asShortBuffer().put(ShortBuffer.wrap(pixelData));
            }
            else if (dataBuffer instanceof DataBufferInt) {
                int[] pixelData = ((DataBufferInt) dataBuffer).getData();
                byteBuffer = ByteBuffer.allocate(pixelData.length * 4);
                byteBuffer.asIntBuffer().put(IntBuffer.wrap(pixelData));
            }
            else {
                throw new IllegalArgumentException("Not implemented for data buffer type: " + dataBuffer.getClass());
            }

            request.write(byteBuffer.array());

            request.writeBytes(crlf);
            request.writeBytes(twoHyphens + boundary + twoHyphens + crlf);

            OutputStreamWriter wr = new OutputStreamWriter(httpUrlConnection.getOutputStream());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(JSON_ENTRY_KEY_ID, userId);
            wr.write(jsonObject.toString());
            wr.flush();

            request.flush();
            request.close();

            InputStream responseStream = new
                    BufferedInputStream(httpUrlConnection.getInputStream());

            BufferedReader responseStreamReader =
                    new BufferedReader(new InputStreamReader(responseStream));

            String line = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ((line = responseStreamReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            responseStreamReader.close();

            String response = stringBuilder.toString();

            Log.i(response);

            responseStream.close();

            httpUrlConnection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
