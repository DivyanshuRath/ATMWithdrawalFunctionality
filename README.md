# Atm Implementation

# Objective

## Problem Statement

- Write compiling code for "Cash Withdrawal" function, from an ATM which based on user specified amount,
  dispenses bank-notes.

Ensure that the code takes care of the following:

- Minimum number of bank-notes are used while dispensing the amount
- Availability of various denominations in the ATM machine is maintained
- Code should be flexible to take care of any bank denomination as long as it is multiple of 10
- Code should support parallel Cash Withdrawals i.e. two or more customer's should be able to withdraw money
- Takes care of exceptional situations
- Write appropriate unit test cases too
- State any assumptions made
- Write the compiling code using choice of your IDE (Eclipse, IntelliJ)
- Provide Unit Test Cases using JUNIT (if you are not conversant with JUNIT, just list down unit test cases)

## NFRs

- Duration of this exercise is 90 minutes. Please manage your time accordingly,
- Make any necessary assumption, and clearly state the assumptions made.
- Do not seek any help online or through any other source.

## Evaluation Criteria

- **Code Completeness/ Correctness**
- **Code Structure**: Modularity, usage of 00 principles and design patterns, size of classes, and functions, n-path
  complexity of functions.
- **Code Quality**: class/function/variable naming conventions, package/class structure, log messages - please do not
  provide comments unless necessary
- **Choice of data structures**
- **Efficiency of code** (leverage multi-threading wherever it makes sense)
- **Code test Cases and follow TDD** (if know)

# Solution

## Implementation

1. Code starts with the `Assignment` class which sets up the blueprint for the ATM.
2. It calls userCashWithdrawal method inside `ATMMachine` class parallely for 2 slots and can be increased as required.
3. In userCashWithdrawal method, we are setting loggers on the basis of the return from withdrawCash method. 
4. In withdrawCash method of `ATMCash` class,  we are writing the logic for the cash withdrawal keeping in mind the denominations and its count
5. Each thread runs parallely to give the results in the logs.

## How to Run ?

### Pre-requisites:

Tested with:
- Java 18 

### Steps:
1. Clone the repository
2. Run `gradle clean build` to build the project
3. Run `gradle bootRun` to run the project

### Assumptions:

1. ATM can have any number of denominations, but all denominations should be multiple of 10.
2. ATM can have multiple denominations with different currencies.





