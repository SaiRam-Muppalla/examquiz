package com.startup;

import java.sql.Date;
import java.time.LocalDate;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.entity.User;
import com.entity.Batch;
import com.entity.Student;
import com.helper.FactoryProvider;

@WebListener
public class DefaultDataLoader implements ServletContextListener {

	private static final String ADMIN_EMAIL = "admin@example.com";
	private static final String ADMIN_PASS = "admin123";
	private static final String DEMO_BATCH_ID = "BATCH1";
	private static final String DEMO_STUDENT_ID = "STUD1";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String disable = System.getenv("SEED_DISABLE");
		if (disable != null && disable.equalsIgnoreCase("true")) {
			System.out.println("[Seed] Seeding disabled via SEED_DISABLE env var.");
			return;
		}
		seedAllWithRetry(5, 2000);
	}

	private void seedAllWithRetry(int attempts, long sleepMs) {
		int attempt = 1;
		while (attempt <= attempts) {
			try (Session session = FactoryProvider.getFactory().openSession()) {
				Transaction tx = session.beginTransaction();
				boolean changed = false;
				Long userCount = (Long) session.createQuery("select count(u.id) from User u").uniqueResult();
				if (userCount == null) userCount = 0L;
				if (userCount == 0) {
					User admin = new User();
					admin.setId("ADMIN1");
					admin.setUsername("Admin");
					admin.setEmail(ADMIN_EMAIL);
					admin.setPhone_no("9999999999");
					admin.setPassword(ADMIN_PASS);
					admin.setCreated_Date(Date.valueOf(LocalDate.now()));
					session.save(admin);
					System.out.println("[Seed] Admin user created");
					changed = true;
				}
				Long batchCount = (Long) session.createQuery("select count(b.batchid) from Batch b").uniqueResult();
				if (batchCount == null) batchCount = 0L;
				if (batchCount == 0) {
					Batch batch = new Batch();
					batch.setBatchid(DEMO_BATCH_ID);
					batch.setBatchname("Demo Batch");
					batch.setAddedby("ADMIN1");
					batch.setAddeddate(Date.valueOf(LocalDate.now()));
					session.save(batch);
					System.out.println("[Seed] Demo batch created");
					changed = true;
				}
				Long studCount = (Long) session.createQuery("select count(s.studentid) from Student s").uniqueResult();
				if (studCount == null) studCount = 0L;
				if (studCount == 0) {
					Student student = new Student();
					student.setStudentid(DEMO_STUDENT_ID);
					student.setFirstname("John");
					student.setMiddlename("Q");
					student.setLastname("Doe");
					student.setStudentemailid("student@example.com");
					student.setStudentaddress("123 Demo Street");
					student.setStudentpassword("student123");
					student.setPhoneno("8888888888");
					student.setRollno("R001");
					student.setStudentaddedby("ADMIN1");
					student.setStudentgender("M");
					student.setStudentdob(Date.valueOf(LocalDate.now().minusYears(20)));
					student.setStudentbatch(DEMO_BATCH_ID);
					student.setStudentstatus("ACTIVE");
					student.setStudentaddedon(Date.valueOf(LocalDate.now()));
					session.save(student);
					System.out.println("[Seed] Demo student created");
					changed = true;
				}
				if (changed) {
					tx.commit();
					System.out.println("[Seed] Seeding complete.");
				} else {
					tx.rollback();
					System.out.println("[Seed] Data already present – nothing to seed.");
				}
				return;
			} catch (Exception e) {
				System.err.println("[Seed] Attempt " + attempt + " failed: " + e.getMessage());
				if (attempt == attempts) {
					System.err.println("[Seed] Giving up after " + attempts + " attempts.");
				} else {
					try { Thread.sleep(sleepMs); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
				}
			}
			attempt++;
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// no-op
	}
}

