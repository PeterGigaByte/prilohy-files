package fei.stuba.bp.rigo.preteky.online;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "onlineEntityManagerFactory",
        transactionManagerRef = "onlineTransactionManager",
        basePackages = { "fei.stuba.bp.rigo.preteky.online.repo" }
)
public class OnlineDbsConfiguration {
    @Bean(name = "online")
    @ConfigurationProperties(prefix = "spring.online")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "onlineEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
    barEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("online") DataSource dataSource
    ) {
        return
                builder
                        .dataSource(dataSource)
                        .packages("fei.stuba.bp.rigo.preteky.models.sql")
                        .persistenceUnit("online")
                        .build();
    }
    @Bean(name = "onlineTransactionManager")
    public PlatformTransactionManager barTransactionManager(
            @Qualifier("onlineEntityManagerFactory") EntityManagerFactory
                    barEntityManagerFactory
    ) {
        return new JpaTransactionManager(barEntityManagerFactory);
    }
}

