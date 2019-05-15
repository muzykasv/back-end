package com.brainacad;

import com.github.fge.jsonschema.core.report.ProcessingReport;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Assert;
import org.junit.Test;

import java.awt.geom.Point2D;
import java.io.IOException;
import org.joda.time.format.DateTimeFormatter;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.brainacad.JsonUtils.*;


public class RestTest{

    private static final String URL="https://reqres.in/";

    @Test//GET метод
    public void checkGetResponseStatusCode() throws IOException {
        String endpoint="/api/users";

        //Выполняем REST GET запрос с нашими параметрами
        // и сохраняем результат в переменную response.
        HttpResponse response = HttpClientHelper.get(URL+endpoint,"page=2");

        //получаем статус код из ответа
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + statusCode);
        Assert.assertEquals("Response status code should be 200", 200, statusCode);
    }

    @Test//GET метод
    public void checkGetResponseBodyNotNull() throws IOException {
        String endpoint="/api/users";

        //Выполняем REST GET запрос с нашими параметрами
        // и сохраняем результат в переменную response.
        HttpResponse response = HttpClientHelper.get(URL+endpoint,"page=2");

        //Конвертируем входящий поток тела ответа в строку
        String body=HttpClientHelper.getBodyFromResponse(response);
        System.out.println(body);
        Assert.assertNotEquals("Body shouldn't be null", null, body);
    }

    @Test//POST метод
    public void checkPostResponseStatusCode() throws IOException {
        String endpoint="/api/users";


        //создаём тело запроса
        String requestBody="{\"name\": \"morpheus\",\"job\": \"leader\"}";

        //Выполняем REST POST запрос с нашими параметрами
        // и сохраняем результат в переменную response.
        HttpResponse response = HttpClientHelper.post(URL+endpoint,requestBody);

        //получаем статус код из ответа
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + statusCode);
        Assert.assertEquals("Response status code should be 201", 201, statusCode);
    }

    @Test//POST метод
    public void checkPostResponseBodyNotNull() throws IOException {
        String endpoint="/api/users";

               //создаём тело запроса
        String requestBody="{\"name\": \"morpheus\",\"job\": \"leader\"}";

        //Выполняем REST POST запрос с нашими параметрами
        // и сохраняем результат в переменную response.
        HttpResponse response = HttpClientHelper.post(URL+endpoint,requestBody);

        //Конвертируем входящий поток тела ответа в строку
        String body=HttpClientHelper.getBodyFromResponse(response);
        System.out.println(body);
        Assert.assertNotEquals("Body shouldn't be null", null, body);
    }

    //TODO: напишите по тесткейсу на каждый вариант запроса на сайте https://reqres.in
    //TODO: в тескейсах проверьте Result Code и несколько параметров из JSON ответа (если он есть)

    @Test//GET метод
    public void ListUser() throws IOException {
        String endpoint = "/api/users";
        //Выполняем REST GET запрос с нашими параметрами
        // и сохраняем результат в переменную response.
        HttpResponse response = HttpClientHelper.get(URL + endpoint, "page=2");

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + statusCode);
        Assert.assertEquals("Response status code should be 200", 200, statusCode);

        String body = HttpClientHelper.getBodyFromResponse(response);
        String JsonPath = "$.data[*].first_name";
        List ListUser = listFromJSONByPath(body, JsonPath);
        List ExpectedUser = Arrays.asList("Eve", "Charles", "Tracey");
        Assert.assertEquals("Error message", ExpectedUser, ListUser);
        System.out.println(body);
    }

    @Test//GET метод
    public void SingleUser() throws IOException {
        String endpoint = "/api/users/2";
        //Выполняем REST GET запрос с нашими параметрами
        // и сохраняем результат в переменную response.
        HttpResponse response = HttpClientHelper.get(URL + endpoint, "page=2");

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + statusCode);
        Assert.assertEquals("Response status code should be 200", 200, statusCode);

        String body = HttpClientHelper.getBodyFromResponse(response);
        String JsonPath = "$.data.last_name";
        String SingleUser = stringFromJSONByPath(body, JsonPath);
        String ExpectedUser = "Weaver";
        Assert.assertEquals("Error message", ExpectedUser, SingleUser);
        System.out.println(body);
    }

    @Test//GET метод
    public void SingleUserNotFound() throws IOException {
        String endpoint = "/api/users/23";
        //Выполняем REST GET запрос с нашими параметрами
        // и сохраняем результат в переменную response.
        HttpResponse response = HttpClientHelper.get(URL + endpoint, "page=2");

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + statusCode);
        Assert.assertEquals("Response status code should be 404", 404, statusCode);
    }

    @Test//POST метод
    public void Create() throws IOException {
        String endpoint = "/api/users";

        //создаём тело запроса
        String requestBody ="{\"name\": \"morpheus\",\"job\": \"leader\"}";
        //Выполняем REST POST запрос с нашими параметрами
        // и сохраняем результат в переменную response.
        HttpResponse response = HttpClientHelper.post(URL+endpoint,requestBody);

        //Конвертируем входящий поток тела ответа в строку
        String body = HttpClientHelper.getBodyFromResponse(response);
        String Create = stringFromJSONByPath(body, "$..createAt");

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + statusCode);
        Assert.assertEquals("Response status code should be 201",201, statusCode);

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss:SSS'Z'").withZoneUTC();
        DateTime dt = formatter.parseDateTime(Create);
        dt.plusMinutes(-15).isBeforeNow();

        Assert.assertTrue("Post Created BUG", dt.plusMinutes(-1).isBeforeNow());
        System.out.println(dt);
    }

    @Test//GET метод
    public void ListResource() throws IOException {
        String endpoint = "/api/unknown";
        //Выполняем REST GET запрос с нашими параметрами
        // и сохраняем результат в переменную response.
        HttpResponse response = HttpClientHelper.get(URL + endpoint, "page=1");

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + statusCode);
        Assert.assertEquals("Response status code should be 200", 200, statusCode);

        String body = HttpClientHelper.getBodyFromResponse(response);
        String JsonPath = "$.data[*].name";
        List ListResource = listFromJSONByPath(body, JsonPath);
        List ExpectedUser = Arrays.asList("cerulean","fuchsia rose","true red");
        Assert.assertEquals("Error message", ExpectedUser, ListResource);
        System.out.println(body);
    }

    @Test//GET метод
    public void SingleResource() throws IOException {
        String endpoint = "/api/unknown/2";
        //Выполняем REST GET запрос с нашими параметрами
        // и сохраняем результат в переменную response.
        HttpResponse response = HttpClientHelper.get(URL + endpoint, "page=2");

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + statusCode);
        Assert.assertEquals("Response status code should be 200", 200, statusCode);

        String body = HttpClientHelper.getBodyFromResponse(response);
        String JsonPath = "$.data.name";
        String SingleResource = stringFromJSONByPath(body, JsonPath);
        String ExpectedUser = "fuchsia rose";
        Assert.assertEquals("Error message", ExpectedUser, SingleResource);
        System.out.println(body);
    }

    @Test//GET метод
    public void SingleRrsourceNotFound() throws IOException {
        String endpoint = "/api/unknown/23";
        //Выполняем REST GET запрос с нашими параметрами
        // и сохраняем результат в переменную response.
        HttpResponse response = HttpClientHelper.get(URL + endpoint, "");

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + statusCode);
        Assert.assertEquals("Response status code should be 404", 404, statusCode);
    }

    @Test//json test
    public void ListUserWithSchema() throws Exception {
        String endpoint = "/api/users";
        //Выполняем REST GET запрос с нашими параметрами
        // и сохраняем результат в переменную response.
        HttpResponse response = HttpClientHelper.get(URL + endpoint, "");
        String body = HttpClientHelper.getBodyFromResponse(response);

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + statusCode);
        Assert.assertEquals("Response status code should be 200", 200, statusCode);

        ProcessingReport result = MyJsonValidator.validateJson(body, "schemas/schema1.json");
        Assert.assertTrue(result.toString(),result.isSuccess());
    }
}

