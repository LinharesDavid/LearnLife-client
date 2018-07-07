package utils.request.builder;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RequestBuilder {

    private String url;
    private Map<String, String> requestBodyParameters = new HashMap<>();
    private Map<String, String> requestProperties = new HashMap<>();
    private String requestMethod;
    private OnRequestFailListener onResponseFailListener;
    private OnRequestSuccessListener onResponseSuccessListener;

    public static RequestBuilder builder() {
        return new RequestBuilder();
    }

    public void build() {
        try {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            requestBodyParameters.forEach(jsonObjectBuilder::add);
            JsonObject jsonObject = jsonObjectBuilder.build();
            URL url = new URL(this.url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(requestMethod);
            con.setDoOutput(true);
            con.setDoInput(true);
            requestProperties.forEach(con::addRequestProperty);
            con.setConnectTimeout(1000);
            con.setReadTimeout(1000);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(jsonObject.toString());
            wr.flush();
            StringBuffer content;
            int errCode;
            if ((errCode = con.getResponseCode()) != 200) {
                content = readResponse(con.getErrorStream());
                onResponseFailListener.onRequestFail(errCode, content == null ? "unknown error" : content.toString());

            } else {
                content = readResponse(con.getInputStream());
                onResponseSuccessListener.onRequestSuccess(content == null ? "Error while reading response" : content.toString());
            }
            con.disconnect();

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private StringBuffer readResponse(InputStream is) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            return content;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }

    public RequestBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public RequestBuilder addRequestBodyParameter(String key, String value) {
        this.requestBodyParameters.put(key, value);
        return this;
    }

    public RequestBuilder addRequestProperty(String key, String value) {
        this.requestProperties.put(key, value);
        return this;
    }

    public RequestBuilder setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
        return this;
    }

    public RequestBuilder setOnResponseFailListener(OnRequestFailListener onResponseFailListener) {
        this.onResponseFailListener = onResponseFailListener;
        return this;
    }

    public RequestBuilder setOnResponseSuccessListener(OnRequestSuccessListener onResponseSuccessListener) {
        this.onResponseSuccessListener = onResponseSuccessListener;
        return this;
    }
}
