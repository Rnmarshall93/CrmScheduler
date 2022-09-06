package com.CrmScheduler;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.CrmScheduler.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;


public class HibernateConf {
    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                InputStream secretFile = new FileInputStream("secrets");
                Properties secretProps = new Properties();
                secretProps.load(secretFile);

                final String hostString = secretProps.getProperty("hostString");
                final String dbUser = secretProps.getProperty("dbUser");
                final String dbPass = secretProps.getProperty("dbPass");

                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, hostString);
                settings.put(Environment.USER, dbUser);
                settings.put(Environment.PASS, dbPass);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");


                settings.put(Environment.SHOW_SQL, "false");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                //for db testing only
                //settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(CrmUser.class);
                configuration.addAnnotatedClass(Customer.class);
                configuration.addAnnotatedClass(FirstLevelDivision.class);
                configuration.addAnnotatedClass(Country.class);
                configuration.addAnnotatedClass(Appointment.class);
                configuration.addAnnotatedClass(Contact.class);
               // java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}