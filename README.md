# CS 320: Software Testing, Automation, and Quality Assurance

This repo has the Java services and JUnit 5 unit tests I wrote for CS 320 at Southern New Hampshire University. My main goal was to make sure every requirement has a test that proves it works.

## Project Overview

In the class scenario I was a software engineer at Grand Strand Systems, a company that builds and tests back-end services. I built the services for a customer's mobile app. There are three services, and each one has its own rules and its own tests:

| Service | Purpose | Key requirements |
|---|---|---|
| **Contact** | Store and manage contacts | Unique ID (max 10 chars, not updatable); first and last name (max 10 chars); phone (exactly 10 digits); address (max 30 chars) |
| **Task** | Store and manage tasks | Unique ID (max 10 chars, not updatable); name (max 20 chars); description (max 50 chars) |
| **Appointment** | Schedule and cancel appointments | Unique ID (max 10 chars, not updatable); date (required, cannot be in the past); description (max 50 chars) |

Every field is required, so null values are rejected. Bad input throws an `IllegalArgumentException`, so an invalid object can never be created. Each service keeps its objects in a `HashMap` using the ID as the key. That makes it easy to keep IDs unique and to look things up quickly, and there is no database.

## Repository Structure

```
.
├── ContactService/
│   ├── Contact.java
│   ├── ContactService.java
│   ├── ContactTest.java
│   └── ContactServiceTest.java
├── TaskService/
│   ├── Task.java
│   ├── TaskService.java
│   ├── TaskTest.java
│   └── TaskServiceTest.java
├── AppointmentService/
│   ├── Appointment.java
│   ├── AppointmentService.java
│   ├── AppointmentTest.java
│   └── AppointmentServiceTest.java
└── README.md
```

## Testing Approach

- **Tests based on the requirements.** I wrote the tests from the requirements instead of from my own code, so I wouldn't just test what I already assumed.
- **Equivalence partitioning and boundary value analysis.** For each limit I test a valid value, the exact limit (like 10 characters), one past the limit (11 characters), and null.
- **Negative testing.** I used `assertThrows` with the exact exception type, so a test only passes when the code fails the right way.
- **Checking the object afterward.** After a rejected update, the tests make sure the object didn't change. `testFailedUpdateLeavesTaskUnchanged` is one example.
- **Defensive copying.** `java.util.Date` can be changed after it is created, so `Appointment` makes a copy when it stores the date and when it returns it. A test changes both and checks that the stored date stays the same.
- **Independent tests.** `@BeforeEach` makes a new service before every test, so one test can't affect another.

**Code coverage:** measured with JaCoCo through Eclipse (EclEmma).

## Technologies

- Java
- JUnit 5 (Jupiter)
- JaCoCo / EclEmma for code coverage
- Eclipse IDE

## Running the Tests

1. Import the project into Eclipse (or another Java IDE) with JUnit 5 on the build path.
2. Right-click a test class or the whole project and choose **Run As → JUnit Test**.
3. For coverage, choose **Coverage As → JUnit Test** and open the Coverage view.

## Reflection

This project showed me that testing is about how you think as much as the tools you use. I learned to be careful about how the pieces of the code depend on each other, to watch out for my own bias when testing my own code, and to not cut corners so technical debt doesn't build up. One thing I would fix is that the length limits in `Task` are typed as plain numbers in a few places, while `Appointment` uses named constants. If I did this again, I would switch `Task` to constants and add integration tests to check that the services work together.

## Author

James Frost
Southern New Hampshire University, CS 320

