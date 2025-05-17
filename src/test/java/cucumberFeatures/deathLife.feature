Feature: Buy Product from site

    Background:
      Given I Landed on Ecommerce Page

         @Regression
         Scenario Outline: Test to Submit Order
           Given Login with username <username> and password <password>
           When click product to cart
           Then checkout and submit the order

      Examples:
            |username         |password   |
            |anshika@gmail.com|Iamking@000|
