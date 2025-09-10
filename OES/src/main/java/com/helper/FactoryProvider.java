package com.helper;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import java.util.Properties;

public class FactoryProvider {
	public static SessionFactory factory;

	public static SessionFactory getFactory() {
		if (factory == null) {
			// Allow overriding DB connection via environment variables for container deployment
			String dbHost = System.getenv("DB_HOST");
			if (dbHost != null && !dbHost.isEmpty()) {
				String dbPort = envOrDefault("DB_PORT", "3306");
				String dbName = envOrDefault("DB_NAME", "OES_Project");
				String dbUser = envOrDefault("DB_USER", "root");
				String dbPass = envOrDefault("DB_PASS", "root");
				Properties props = new Properties();
				props.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
				props.put(Environment.URL, "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
				props.put(Environment.USER, dbUser);
				props.put(Environment.PASS, dbPass);
				props.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
				props.put(Environment.HBM2DDL_AUTO, envOrDefault("HIBERNATE_HBM2DDL", "update"));
				props.put(Environment.SHOW_SQL, envOrDefault("HIBERNATE_SHOW_SQL", "true"));
				props.put(Environment.FORMAT_SQL, "true");
				Configuration cfg = new Configuration();
				cfg.setProperties(props)
					.addAnnotatedClass(com.entity.User.class)
					.addAnnotatedClass(com.entity.Batch.class)
					.addAnnotatedClass(com.entity.Student.class)
					.addAnnotatedClass(com.entity.Exam.class)
					.addAnnotatedClass(com.entity.Question.class)
					.addAnnotatedClass(com.entity.Enroll.class)
					.addAnnotatedClass(com.entity.Answer.class)
					.addAnnotatedClass(com.entity.Result.class)
					.addAnnotatedClass(com.entity.Notice.class);
				factory = cfg.buildSessionFactory();
			} else {
				factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
			}
		}
		return factory;
	}

	private static String envOrDefault(String key, String def) {
		String v = System.getenv(key);
		return (v == null || v.isEmpty()) ? def : v;
	}
	public void closeFactory() {
		if (factory.isOpen()) {
			factory.close();
		}
	}
}
