package org.rmysj.api.config.mybatis;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableTransactionManagement
@Lazy
public class DataBaseConfiguration implements EnvironmentAware {

	private RelaxedPropertyResolver propertyResolver;

	private static Logger log = Logger.getLogger(DataBaseConfiguration.class);

	private Environment env;

	@Override
	public void setEnvironment(Environment env) {
		this.env = env;
		this.propertyResolver = new RelaxedPropertyResolver(env, "jdbc.");
	}

	@Bean("dataSource")
	public DataSource dataSource() {
		log.debug("Configruing DataSource");
		if (propertyResolver.getProperty("url") == null
				&& propertyResolver.getProperty("databaseName") == null) {
			log.error("Your database conncetion pool configuration is incorrct ! The application "
					+ "cannot start . Please check your jdbc");
			Arrays.toString(env.getActiveProfiles());
			throw new ApplicationContextException(
					"DataBase connection pool is not configured correctly");
		}
		HikariConfig config = new HikariConfig();
		config.setDataSourceClassName(propertyResolver
				.getProperty("dataSourceClassName"));
		if (propertyResolver.getProperty("url") == null
				|| "".equals(propertyResolver.getProperty("url"))) {
			config.addDataSourceProperty("databaseName",
					propertyResolver.getProperty("databaseName"));
			config.addDataSourceProperty("serverName",
					propertyResolver.getProperty("serverName"));
		} else {
			config.addDataSourceProperty("url",
					propertyResolver.getProperty("url"));
		}
		config.setUsername(propertyResolver.getProperty("username"));
		config.setPassword(propertyResolver.getProperty("password"));
		config.setMinimumIdle(Integer.valueOf(propertyResolver.getProperty("minimum")));
		//池中最大链接数量
		config.setMaximumPoolSize(Integer.valueOf(propertyResolver.getProperty("maximun")));
		config.setConnectionTimeout(Long.valueOf(propertyResolver.getProperty("connTimeout")));
		config.setIdleTimeout(Long.valueOf(propertyResolver.getProperty("idleTimeout")));
		config.setMaxLifetime(Long.valueOf(propertyResolver.getProperty("maxLifetime")));
		config.setLeakDetectionThreshold(Long.valueOf(propertyResolver.getProperty("leakDetectionThreshold")));
		if ("com.mysql.jdbc.jdbc2.optional.MysqlDataSource"
				.equals(propertyResolver.getProperty("dataSourceName"))) {
			config.addDataSourceProperty("cachePrepStmts",
					propertyResolver.getProperty("cachePrepStmts"));
			config.addDataSourceProperty("prepStmtCacheSize",
					propertyResolver.getProperty("prepStmtsCacheSize"));
			config.addDataSourceProperty("prepStmtCacheSqlLimit",
					propertyResolver.getProperty("prepStmtCacheSqlLimit"));
			config.addDataSourceProperty("userServerPrepStmts",
					propertyResolver.getProperty("userServerPrepStmts"));
		}
		return new HikariDataSource(config);
	}

}
