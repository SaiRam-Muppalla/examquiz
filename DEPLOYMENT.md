# Deployment Guide

## Local (Docker Compose)
```
cd Online-Exam-System-Java
docker compose up --build
```
App will be at http://localhost:8080/

Database: MySQL 8 (root/root) with schema `OES_Project`.

Hibernate will auto-create/update tables (HBM2DDL=update).

## Environment Variables
| Variable | Default | Description |
|----------|---------|-------------|
| DB_HOST | db | MySQL host |
| DB_PORT | 3306 | MySQL port |
| DB_NAME | OES_Project | Database name |
| DB_USER | root | DB username |
| DB_PASS | root | DB password |
| HIBERNATE_HBM2DDL | update | hbm2ddl strategy |
| HIBERNATE_SHOW_SQL | true | Show SQL logs |

## Production Notes
- Use an external managed MySQL instance.
- Set strong credentials via env vars.
- Consider changing `HBM2DDL` to `validate`.
- Add a reverse proxy (Nginx) with TLS in front of Tomcat.

## GitHub Actions CI
A workflow (`.github/workflows/build.yml`) builds the WAR and a Docker image on pushes & PRs to `main`.

## Publishing Image to GHCR
Add these steps to the workflow (before build) replacing placeholders:
```
			- name: Log in to GHCR
				run: echo "${{ secrets.GITHUB_TOKEN }}" | docker login ghcr.io -u ${{ github.actor }} --password-stdin
			- name: Build & Push
				run: |
					docker build -t ghcr.io/<owner>/oes-app:latest .
					docker push ghcr.io/<owner>/oes-app:latest
```

## Production Compose
`docker-compose.prod.yml` expects an existing image (override image ref if needed):
```
DB_USER=appuser DB_PASS='StrongPassword' docker compose -f docker-compose.prod.yml up -d
```

## Hardening Checklist
- Set `HIBERNATE_HBM2DDL=validate`
- Use dedicated DB user with least privileges
- Enable TLS termination (Nginx / Traefik)
- Configure logging rotation
- Add container healthcheck (future enhancement)

## Build WAR manually
```
cd Online-Exam-System-Java/OES
mvn clean package
```
Generated WAR: `OES/target/OES.war`

Deploy WAR to any Tomcat 9+ server (drop into webapps directory).
