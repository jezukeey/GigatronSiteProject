# GigatronSiteProject

A Java/Selenium QA learning project originally created for the CODE by Comtrade final exam.

## Requirements

- JDK 17
- Maven 3.9 or newer

## Compile without interacting with the website

Run from the directory containing `pom.xml`:

```shell
mvn -DskipTests clean test-compile
```

This compiles both page classes and test classes. It does not launch a browser or execute tests.

## Current limitations

This is a learning project, not yet a reliable regression suite.

- Some tests print results instead of asserting them.
- Fixed sleeps and browser cleanup need improvement.
- Website locators and product URLs have not been checked against the current website.
- `CompleteBuyOfItem` contains steps that submit an order on the live website.
  Do not run the full suite with `mvn test` against the live shop.
  Move purchasing scenarios to an authorized test environment before executing them.
- Credentials currently appear in source code. Move them to environment variables;
  if any committed credentials are real and still valid, replace them.

## First repair: predictable Maven configuration

Selenium dependencies now use one pinned version (4.27.0), with JUnit 4.13.2
and pinned compiler/test plugins. Java 17 and UTF-8 are explicit.
These versions establish a reproducible starting point; they are not claimed to be the latest.

Selenium and JUnit currently have compile scope because page classes under
`src/main/java` import them. Moving assertions out of page classes is a later learning step.
