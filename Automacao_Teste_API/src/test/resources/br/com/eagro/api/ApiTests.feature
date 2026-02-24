# language: en
Feature: API Externa - Testes de Contrato (JSONPlaceholder)

  Scenario: Successfully create a new post
    Given the request body is:
      """
      {
        "title": "foo",
        "body": "bar",
        "userId": 1
      }
      """
    When a POST request is sent to "/posts"
    Then the response status code should be 201
    And the response body should contain the property "id"
    And the response body should have the value "foo" for the property "title"
    And the response body should have the value "bar" for the property "body"
    And the response body should have the value 1 for the property "userId"

  Scenario: Update an existing post
    Given the request body is:
      """
      {
        "id": 1,
        "title": "foo",
        "body": "bar",
        "userId": 1
      }
      """
    When a PUT request is sent to "/posts/1"
    Then the response status code should be 200
    And the response body should contain the property "id"
    And the response body should have the value "foo" for the property "title"
    And the response body should have the value "bar" for the property "body"
    And the response body should have the value 1 for the property "userId"

  Scenario: Get an existing post
    When a GET request is sent to "/posts/1"
    Then the response status code should be 200
    And the response body should have the value 1 for the property "id"
    And the response body should have the value 1 for the property "userId"

  Scenario: Attempt to create a post with an invalid body
    Given the request body is:
      """
      {
        "title": "foo",
        "body": "bar",
        "userId": 1,
      }
      """
    When a POST request is sent to "/posts"
    Then the response status code should be 500

  Scenario: Successfully delete an existing post
    When a DELETE request is sent to "/posts/1"
    Then the response status code should be 200

  Scenario: Attempt to access a protected/non-existent endpoint
    When a GET request is sent to "/protected"
    Then the response status code should be 404

  Scenario: Attempt to access an endpoint that simulates an internal error
    When a GET request is sent to "/erro500"
    Then the response status code should be 404

  Scenario: Attempt to access an endpoint that simulates a service unavailable
    When a GET request is sent to "/erro503"
    Then the response status code should be 404
