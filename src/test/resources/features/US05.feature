
Feature: As a user, I want to view my own user information using the API
  so i can see all the information that is stored about me
  Scenario Outline: Decode User
    Given I logged in library with credentials "<email>" and "<password>"
    And Request Content Type header is "application/x-www-form-urlencoded"
    And I send "token" information as a request body
    #When I send "post" request to "/decode" endpoint
    When I send POST request to "/decode" endpoint
    Then status code should be 200
    And Response content type should be "application/json; charset=utf-8"
    And the field value for "user_group_id" path should be equal to "<user_group_id>"
    And the field value for "email" path should be equal to "<email>"
    And "full_name" field should not be null
    And "id" field should not be null
    Examples:
      | email               | password    | user_group_id |
      | student5@library    | libraryUser | 3             |
      | librarian10@library | libraryUser | 2             |
      | student10@library   | libraryUser | 3             |
      | librarian13@library | libraryUser | 2             |


