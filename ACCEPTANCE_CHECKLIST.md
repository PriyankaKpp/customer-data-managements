# Acceptance Checklist — Customer Data Microservice

- [ ] Service runs locally with `mvn spring-boot:run`.
- [ ] OpenAPI spec present and accurate: `openapi.yaml`.
- [ ] CRUD endpoints: POST/GET/PUT/DELETE for `Customer` implemented.
- [ ] Endpoints secured by Bearer token (env `API_BEARER_TOKEN`).
- [ ] Unit tests present and passing (`mvn test`).
- [ ] Mutation testing configured (PIT) and report generated.
- [ ] Basic CI steps documented in README.
- [ ] Database connection configurable (supports `freesqldatabase.com`).
- [ ] README contains run/test instructions and config hints.
