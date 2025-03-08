Feature: As a user, I want to search for specific user by their name or
  email address using the API so I can quickly find the information that i need

  Scenario: Retrieve single user

    Given I logged in library API as "librarian"
    And Accept header should be "application/json"
    And path param should be "id" as a "1"
    #When I send "get" request to "/get_user_by_id/{id}" endpoint
    When I send GET request to "/get_user_by_id/{id}" endpoint
    Then status code should be 200
    And Response content type should be "application/json; charset=utf-8"
    And "id" field should be same as the path pram
    And the following fields should not be null
      | full_name |
      | email     |
      | password  |

