Feature: My feature file

  Scenario: Verify that GET request return status code 200
    Given I have server by url "https://reqres.in/"
    When I send GET request on endpoint "/api/users" and parameters "page=2"
    Then I get response status code 200


  Scenario: Verify that GET response Body Not Null
    Given I have server by url "https://reqres.in/"
    When I send GET request on endpoint "/api/users" and parameters "page=2"
    Then I get response body should not be null


  Scenario: Verify that POST response status code
    Given I have server by url "https://reqres.in/"
    When I send POST request on endpoint "/api/users" and parameters "page=2"
    Then I get response status code 201


  Scenario: Verify that POST response body not null
    Given I have server by url "https://reqres.in/"
    When I send POST request on endpoint "/api/users" and parameters "page=2"
    Then I get response body should not be null


  Scenario: Verify that GET response list user
    Given I have server by url "https://reqres.in/"
    When I send GET request on endpoint "/api/users" and parameters "page=2"
    Then I get from body by JSONPath "$.data..first_name" list names
      | Eve   |
      | Charles |
      | Tracey  |

  Scenario: Verify that GET response single user
    Given I have server by url "https://reqres.in/"
    When I send GET request on endpoint "/api/users" and parameters "page=2"
  Then I get from body by JSONPath "$.data[0].last_name" single name "Holt"


  Scenario: Verify that GET response single user not found
    Given I have server by url "https://reqres.in/"
    When I send GET request on endpoint "/api/users/23" and parameters "page=2"
    Then I get response status code 404






