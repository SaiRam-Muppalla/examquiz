# examquiz

Online exam system built with JSP, Servlets, Hibernate, and MySQL. Containerized with Docker, includes local and production compose files, CI builds, and default test credentials.

## Core Features

- User Management: Admin/Coach and Student roles, registration, login, profile
- Email notifications (placeholder mail sender)
- Exam Management: create, edit, assign exams
- Question Bank: MCQs per exam
- Exam Taking: timer, auto-submit
- Results and Reports: scoring and result tracking

## Tech Stack

- Frontend: JSP, HTML, CSS, JavaScript
- Backend: Servlets, Hibernate (JPA), MySQL
- Build: Maven (WAR)
- Runtime: Tomcat 9 (Docker)

## Run locally (Docker)

See `DEPLOYMENT.md` for full details. Quick start:

1. `docker compose up -d` (from repo root)
2. Open http://localhost:8080/

Default credentials: see `CREDENTIALS.md`.

## Notes

Containerized, environment-driven configuration, seeded defaults, and CI/CD are included for convenience.
