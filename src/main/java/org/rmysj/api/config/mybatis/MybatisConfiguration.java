package org.rmysj.api.config.mybatis;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@ConditionalOnClass({ EnableTransactionManagement.class, EntityManager.class })
//@AutoConfigureAfter({ DataBaseConfiguration.class })
@MapperScan(basePackages="org.rmysj.api.api.**.dao")
public class MybatisConfiguration implements EnvironmentAware {

	private static Logger logger = Logger.getLogger(MybatisConfiguration.class);

	private RelaxedPropertyResolver propertyResolver;
//	private RelaxedPropertyResolver propertyResolver2;

//	private Environment env;


	@Override
	public void setEnvironment(Environment environment) {
		this.propertyResolver = new RelaxedPropertyResolver(environment,
				"mybatis.");
//		this.propertyResolver2 = new RelaxedPropertyResolver(environment, "jdbc.");
//		this.env = environment;
	}

//	@Bean
//	public DataSource dataSource() {
//		logger.debug("Configruing DataSource");
//		if (propertyResolver2.getProperty("url") == null
//				&& propertyResolver2.getProperty("databaseName") == null) {
//			logger.error("Your database conncetion pool configuration is incorrct ! The application "
//					+ "cannot start . Please check your jdbc");
//			Arrays.toString(env.getActiveProfiles());
//			throw new ApplicationContextException(
//					"DataBase connection pool is not configured correctly");
//		}
//		HikariConfig config = new HikariConfig();
//		config.setDataSourceClassName(propertyResolver2
//				.getProperty("dataSourceClassName"));
//		if (propertyResolver2.getProperty("url") == null
//				|| "".equals(propertyResolver2.getProperty("url"))) {
//			config.addDataSourceProperty("databaseName",
//					propertyResolver2.getProperty("databaseName"));
//			config.addDataSourceProperty("serverName",
//					propertyResolver2.getProperty("serverName"));
//		} else {
//			config.addDataSourceProperty("url",
//					propertyResolver2.getProperty("url"));
//		}
//		config.setUsername(propertyResolver2.getProperty("username"));
//		config.setPassword(propertyResolver2.getProperty("password"));
//		config.setMinimumIdle(Integer.valueOf(propertyResolver2.getProperty("minimum")));
//		//池中最大链接数量
//		config.setMaximumPoolSize(Integer.valueOf(propertyResolver2.getProperty("maximun")));
//		config.setConnectionTimeout(Long.valueOf(propertyResolver2.getProperty("connTimeout")));
//		config.setIdleTimeout(Long.valueOf(propertyResolver2.getProperty("idleTimeout")));
//		config.setMaxLifetime(Long.valueOf(propertyResolver2.getProperty("maxLifetime")));
//		config.setLeakDetectionThreshold(Long.valueOf(propertyResolver2.getProperty("leakDetectionThreshold")));
//		if ("com.mysql.jdbc.jdbc2.optional.MysqlDataSource"
//				.equals(propertyResolver2.getProperty("dataSourceName"))) {
//			config.addDataSourceProperty("cachePrepStmts",
//					propertyResolver2.getProperty("cachePrepStmts"));
//			config.addDataSourceProperty("prepStmtCacheSize",
//					propertyResolver2.getProperty("prepStmtsCacheSize"));
//			config.addDataSourceProperty("prepStmtCacheSqlLimit",
//					propertyResolver2.getProperty("prepStmtCacheSqlLimit"));
//			config.addDataSourceProperty("userServerPrepStmts",
//					propertyResolver2.getProperty("userServerPrepStmts"));
//		}
//		return new HikariDataSource(config);
//	}

	@Bean
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) {
		try {
			SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
			sessionFactory.setDataSource(dataSource);
			sessionFactory.setTypeAliasesPackage(propertyResolver
					.getProperty("typeAliasesPackage"));
			sessionFactory
					.setMapperLocations(new PathMatchingResourcePatternResolver()
							.getResources(propertyResolver
									.getProperty("mapperLocations")));
			sessionFactory
					.setConfigLocation(new DefaultResourceLoader()
							.getResource(propertyResolver
									.getProperty("configLocation")));

			return sessionFactory.getObject();
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("Could not confiure mybatis session factory");
			return null;
		}
	}

	@Bean
	@ConditionalOnMissingBean
	public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
