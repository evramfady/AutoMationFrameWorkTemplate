@login
Feature: Swag Labs Login

  As a registered user
  I want to log into Swag Labs
  So that I can browse and purchase products

  Background:
    Given the user is on the login page

  @smoke @positive
  Scenario: Successful login with valid credentials
    When the user logs in with username "standard_user" and password "secret_sauce"
    Then the products page should be displayed

  @negative
  Scenario Outline: Login is rejected for invalid credentials
    When the user logs in with username "<username>" and password "<password>"
    Then the error message "<message>" should be shown

    Examples:
      | username        | password       | message                                                                     |
      | locked_out_user | secret_sauce   | Epic sadface: Sorry, this user has been locked out.                         |
      | standard_user   | wrong_password | Epic sadface: Username and password do not match any user in this service   |
      |                 | secret_sauce   | Epic sadface: Username is required                                          |
      | standard_user   |                | Epic sadface: Password is required                                          |
