package ch.semafor.esta.core.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages={"ch.semafor.esta.core.service"})
@EnableJpaRepositories("ch.semafor.esta.core.repository")
public class JpaConfig {
	@Bean
	public DataSource dataSource() {
	  DriverManagerDataSource dataSource = 
	                       new DriverManagerDataSource();
	  dataSource.setDriverClassName("org.postgresql.Driver");
	  dataSource.setUrl("jdbc:postgresql://localhost/Esta");
	  dataSource.setUsername("tar");
	  dataSource.setPassword("");
	  return dataSource;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean 
	                              entityManagerFactory() {
	  LocalContainerEntityManagerFactoryBean em = 
	      new LocalContainerEntityManagerFactoryBean();
	  em.setDataSource(dataSource());
	  em.setPackagesToScan(new String[] { 
	                "ch.semafor.esta.core.domain" });

	  JpaVendorAdapter vendorAdapter = 
	           new HibernateJpaVendorAdapter();
	  em.setJpaVendorAdapter(vendorAdapter);
	  Properties properties = new Properties();
	  properties.setProperty(
	              "hibernate.hbm2ddl.auto", "create");
	  properties.setProperty("hibernate.dialect",
	              "org.hibernate.dialect.PostgreSQLDialect");

	  em.setJpaProperties(properties);

	  return em;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(
				EntityManagerFactory emf) {
	  JpaTransactionManager transactionManager = 
	        new JpaTransactionManager();
	  transactionManager.setEntityManagerFactory(emf);

	  return transactionManager;
	}
	
}
