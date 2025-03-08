@smoke
Feature: as a librarian , i want to retrieve all the users
  As a librarian,
  I want to retrieve all users from the library2.cydeo.com API endpoint
  So that I can display them in my application

Scenario: Retrieve all users from the API endpoint
  Given I logged in library API as "librarian"
  And Accept header should be "application/json"
  #When I send "get" request to "/get_all_users" endpoint
  When I send GET request to "/get_all_users" endpoint
  Then status code should be 200
  And Response content type should be "application/json; charset=utf-8"
  And Each "id" field should not be null
  And Each "name" field should not be null

