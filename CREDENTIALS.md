# Default Test Credentials

Seeded automatically on startup if `usertable` is empty.

| Role   | Email              | Password   | ID      | Phone       | Extra |
|--------|--------------------|------------|---------|-------------|-------|
| Admin  | admin@example.com  | admin123   | ADMIN1  | 9999999999  |       |
| Student| student@example.com| student123 | STUD1   | 8888888888  | Batch: BATCH1 |

Seeding logic: `com.startup.DefaultDataLoader` (ServletContextListener) with retry (5 attempts, 2s interval). Creates Admin, Batch (BATCH1), Student if tables empty.

Disable seeding: set env var `SEED_DISABLE=true` OR remove listener entry.

Security (before production):
1. Change password immediately.
2. Implement password hashing (BCrypt) – current storage is plain text (demo only!).
3. Use strong unique admin email.
4. Set `HIBERNATE_HBM2DDL=validate` in prod.

