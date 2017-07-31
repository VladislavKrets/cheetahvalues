package online.omnia.values;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpMethodsUtils {
    public String getToken(String baseUrl, String clientId, String clientCredentials){
        try {
            HttpPost httpPost = new HttpPost(baseUrl + "oauth/access_token");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("grant_type", "client_credentials"));
            nameValuePairs.add(new BasicNameValuePair("client_id", clientId));
            nameValuePairs.add(new BasicNameValuePair("client_secret", clientCredentials));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            return getAnswer(httpPost);
        } catch (IOException e) {
            System.out.println("Input output exception during getting access token:");
            System.out.println(e.getMessage());
        }
        return "";
    }
    public String getMethod(String url, Map<String, String> headers){

        HttpGet httpGet = new HttpGet(url);
        try {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue());
            }
            return getAnswer(httpGet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getAnswer(HttpRequestBase http) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(http);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder answerBuilder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            answerBuilder.append(line);
        }
        EntityUtils.consume(response.getEntity());
        reader.close();
        response.close();
        httpClient.close();
        return answerBuilder.toString();
    }

    public String postMethod(String url, List<NameValuePair> nameValuePairs, Map<String, String> headers) throws IOException {
        try {
            HttpPost httpPost = new HttpPost(url);
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            return getAnswer(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
