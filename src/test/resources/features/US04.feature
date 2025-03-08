
Feature: As a Librarian , I want to create new user

  Scenario: Create a new user API
    Given I logged in library API as "librarian"
    And Accept header should be "application/json"
    And Request Content Type header is "application/x-www-form-urlencoded"
    And I create a random "user" as request body
    #When I send "post" request to "/add_user" endpoint
    When I send POST request to "/add_user" endpoint
    Then status code should be 200
    And Response content type should be "application/json; charset=utf-8"
    And the field value for "message" path should be equal to "The user has been created."
    And "user_id" field should not be null
   @ui @db
Scenario: Create a new user in all layers
    Given I logged in library API as "librarian"
    And Accept header should be "application/json"
    And Request Content Type header is "application/x-www-form-urlencoded"
    And I create a random "user" as request body
    #When I send "post" request to "/add_user" endpoint
    When I send POST request to "/add_user" endpoint
    Then status code should be 200
    And Response content type should be "application/json; charset=utf-8"
    And the field value for "message" path should be equal to "The user has been created."
    And "user_id" field should not be null
    And created user information should be match with database
    And created user should be able to login in Library UI
    And created user name should be appear in Dashboard Page