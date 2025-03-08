
Feature: As a Librarian, I want to create a new book
  Scenario: create a new book API
    Given I logged in library API as "librarian"
    And Accept header should be "application/json"
    And Request Content Type header is "application/x-www-form-urlencoded"
    And I create a random "book" as request body
   # When I send "post" request to "/add_book" endpoint
    When I send POST request to "/add_book" endpoint
    Then status code should be 200
    And Response content type should be "application/json; charset=utf-8"
    And the field value for "message" path should be equal to "The book has been created."
    And "book_id" field should not be null
  @ui @db
  Scenario: create a new book in all layers
    Given I logged in library API as "librarian"
    And Accept header should be "application/json"
    And Request Content Type header is "application/x-www-form-urlencoded"
    And I create a random "book" as request body
    And I logged in Library UI as "librarian"
    And I navigate to "Books" page
    #When I send "post" request to "/add_book" endpoint
    When I send POST request to "/add_book" endpoint
    Then status code should be 200
    And Response content type should be "application/json; charset=utf-8"
    And the field value for "message" path should be equal to "The book has been created."
    And "book_id" field should not be null
    And UI,Database and API created book information must match