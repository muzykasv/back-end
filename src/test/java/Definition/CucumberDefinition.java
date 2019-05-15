package Definition;

import com.brainacad.HttpClientHelper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.http.HttpResponse;
import org.junit.Assert;

import java.io.IOException;
import java.util.List;

import static com.brainacad.JsonUtils.listFromJSONByPath;
import static com.brainacad.JsonUtils.stringFromJSONByPath;

public class CucumberDefinition {

    private static String URL;
    private static HttpResponse response;

    @Given("I have server by url {string}")
    public void i_have_server_by_url(String url) {
        URL = url;   // Write code here that turns the phrase above into concrete actions
    }


    @When("I send GET request on endpoint {string} and parameters {string}")
    public void iSendGETRequestOnEndpointAndParameters(String endpoint, String parameters) throws IOException {
        response = HttpClientHelper.get(URL + endpoint, parameters); // 1 TC
    }

    @When("I send POST request on endpoint {string} and parameters {string}")
    public void iSendPOSTRequestOnEndpointAndParameters(String endpoint, String parameters) throws IOException {
        // Write code here that turns the phrase above into concrete actions
        response = HttpClientHelper.post(URL + endpoint, parameters);  // 3 TC
    }


    @Then("I get response status code {int}")
    public void i_get_response_status_code(int responseCode) {
        // Write code here that turns the phrase above into concrete actions
        // throw new PendingException(); - заглушка
        Assert.assertEquals(
                String.format("Response status code should be %s", responseCode), //"Response status code should be " + responseCode
                response.getStatusLine().getStatusCode(), responseCode); // 1 and 3 TC
    }


    @Then("I get response body should not be null")
    public void i_get_response_body_should_not_be_null() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        String body = HttpClientHelper.getBodyFromResponse(response);
        Assert.assertNotEquals("Body should not be null", null, body);   //2, 4, 7 TC
    }

    @Then("I get from body by JSONPath {string} list names")
    public void iGetFromBodyByJSONPathListNames(String jsonPath, List<String> expectedNames) throws IOException {
        String body = HttpClientHelper.getBodyFromResponse(response);
        List<String> first_names = listFromJSONByPath(body,jsonPath);
        Assert.assertEquals("First name should be: " + expectedNames, expectedNames, first_names);
    }


    @Then("I get from body by JSONPath {string} single name {string}")
    public void iGetFromBodyByJSONPathSingleName(String jsonPath, String expectedNames) throws IOException {
        String body = HttpClientHelper.getBodyFromResponse(response);
       String last_name = stringFromJSONByPath(body,jsonPath);
        Assert.assertEquals("Single name should be: " + expectedNames, expectedNames, last_name);
    }
}



