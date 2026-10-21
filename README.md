# CS 320: Software Testing, Automation, and Quality Assurance

This repo has the Contact service and JUnit 5 unit tests I wrote for CS 320 at Southern New Hampshire University. My main goal was to make sure every requirement has a test that proves it works. Together, these files show my work in software testing, automation, and quality assurance.

## Project Overview

In the class scenario I was a software engineer at Grand Strand Systems, a company that builds and tests back-end services. I built the services for a customer's mobile app. There were three services (Contact, Task, and Appointment), each with its own rules and its own tests. This repo showcases the **Contact service** from Project One, along with my Project Two Summary and Reflections Report (`Project Two/CS_320_Project_Two.docx`), which covers the testing of all three services.

| Service | Purpose | Key requirements |
|---|---|---|
| **Contact** | Store and manage contacts | Unique ID (max 10 chars, not updatable); first and last name (max 10 chars); phone (exactly 10 digits); address (max 30 chars) |

Every field is required, so null values are rejected. Bad input throws an `IllegalArgumentException`, so an invalid object can never be created. The service keeps its objects in a `HashMap` using the ID as the key. That makes it easy to keep IDs unique and to look things up quickly, and there is no database.

## Repository Structure

```
.
├── Project One/
│   ├── Contact.java
│   ├── ContactService.java
│   ├── ContactTest.java
│   └── ContactServiceTest.java
├── Project Two/
│   └── CS_320_Project_Two.docx
└── README.md
```

### Project One: Contact Service

| File | Description |
|---|---|
| `Contact.java` | The contact object, with validation for every field |
| `ContactService.java` | Adds, updates, and deletes contacts, and keeps IDs unique |
| `ContactTest.java` | Unit tests for the `Contact` class |
| `ContactServiceTest.java` | Unit tests for the `ContactService` class |

### Project Two: Summary and Reflections Report

| File | Description |
|---|---|
| `CS_320_Project_Two.docx` | Summary and reflections report covering the testing of all three services |

## Testing Approach

- **Tests based on the requirements.** I wrote the tests from the requirements instead of from my own code, so I wouldn't just test what I already assumed.
- **Equivalence partitioning and boundary value analysis.** For each limit I test a valid value, the exact limit (like 10 characters), one past the limit (11 characters), and null.
- **Negative testing.** I used `assertThrows` with the exact exception type, so a test only passes when the code fails the right way.
- **Checking the object afterward.** After a rejected update, the tests make sure the object didn't change.
- **Defensive copying.** `java.util.Date` can be changed after it is created, so `Appointment` makes a copy when it stores the date and when it returns it. A test changes both and checks that the stored date stays the same.
- **Independent tests.** `@BeforeEach` makes a new service before every test, so one test can't affect another.

**Code coverage:** measured with JaCoCo through Eclipse (EclEmma). Coverage was around 80% for each of the three services. A high percentage only shows the code ran, so I also wrote tests that assert on results and would fail if a boundary changed (for example, `> 10` becoming `>= 10`).

## Technologies

- Java
- JUnit 5 (Jupiter)
- JaCoCo / EclEmma for code coverage
- Eclipse IDE

## Running the Tests

1. Import the project into Eclipse (or another Java IDE) with JUnit 5 on the build path.
2. Right-click a test class or the whole project and choose **Run As → JUnit Test**.
3. For coverage, choose **Coverage As → JUnit Test** and open the Coverage view.

## Project Two Summary and Reflections Report

The full report is in [`Project Two/CS_320_Project_Two.docx`](Project%20Two/CS_320_Project_Two.docx). It explains how my tests line up with the requirements, why I believe they are technically sound and efficient, and reflects on the techniques I used and the ones I did not.

- **Used:** black-box unit testing from the requirements, equivalence partitioning, boundary value analysis, negative testing, and state-based checking, plus a little white-box testing to make sure each condition ran.
- **Not used:** integration, system, and acceptance testing, automated regression in a pipeline, and performance, security, mutation, fuzz, and mock-based testing. Most did not apply because Project One had no database, network, or user interface.

## Reflection

### How can I ensure that my code, program, or software is functional and secure?

I tie every requirement to at least one test I can point to. For the ten-character ID limit, one test accepts exactly ten characters and another rejects eleven. For the phone number, I test a ten-character string with a hyphen in it, which proves the code checks for digits and not just length. I use `assertThrows` with `IllegalArgumentException` so a test only passes when the right exception is thrown, and I check that an object stays correct after a rejected update. Coverage helps me find code my tests never ran, but I do not treat a high percentage as proof that the code is correct.

On the security side, the same habits apply. Every field is validated and null values are rejected, so an invalid object can never be created. The ID is final with no setter, so the key the service depends on cannot be changed. In `Appointment`, defensive copying of the `Date` stops outside code from changing an appointment without anyone noticing, and a test proves it. A gap I found looking back is that I tested the non-updatable ID directly with reflection for `Contact` but not for `Task` or `Appointment`.

### How do I interpret user needs and incorporate them into a program?

I turn each requirement into something specific I can check, then write the tests from the requirements instead of from how I wrote the code. That way the tests measure what the customer asked for and not what I assumed. If I had to test my own code, I think bias would be a real problem, because I would probably test the cases I already thought about and miss the ones I did not. To reduce that, I would have someone else review my code, have a second person suggest edge cases, and write the test list from the requirements before writing the code.

### How do I approach designing software?

I keep the design simple, with each rule written once. In `Appointment`, `validateDate` and `validateDescription` are used by both the constructor and the setters, and `Contact`'s constructor calls its own setters. I use named constants, comments that match the requirements, and a test for each rule to avoid technical debt. I also pay attention to how the pieces depend on each other. For example, `TaskService.updateTask` checks both values before changing anything, so a bad description can't leave a task half updated, and I wrote a test to prove it.

I can still see some debt in my own work. The length limits in `Task` are typed as plain numbers in a few places, while `Appointment` uses named constants. If I did this again, I would switch `Task` to constants, run the tests automatically on every commit with a minimum coverage level, ask for code reviews, and add integration tests to check that the services work together.

## Author

James Frost
Southern New Hampshire University, CS 320


