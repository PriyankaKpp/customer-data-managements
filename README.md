# Customer Service (minimal)

This repository contains a minimal Spring Boot microservice for managing
customer data with ~20 attributes, a simple Bearer token auth filter,
OpenAPI spec and tests.

Run:

```bash
mvn spring-boot:run
```

Configuration:
- `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD` for production DB (free SQL database URL)
- `API_BEARER_TOKEN` to set the bearer token for requests

OpenAPI spec: `openapi.yaml`

Tests:

```bash
mvn test
mvn org.pitest:pitest-maven:mutationCoverage
```
