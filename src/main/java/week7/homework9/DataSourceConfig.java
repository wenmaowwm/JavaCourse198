package week7.homework9;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {
    @Bean(name = "dbMaster")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource dbMaster() {
        return new DruidDataSource();
    }

    @Bean(name = "dbSlave")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource dbSlave() {
        return new DruidDataSource();
    }

    @Primary
    @Bean(name = "dataSourceRouter") // 对应Bean: DataSourceRouter
    public DataSource dataSourceRouter(@Qualifier("dbMaster") DataSource master, @Qualifier("dbSlave") DataSource slave) {
        DataSourceRouter dataSourceRouter = new DataSourceRouter();

        //配置多数据源
        Map<Object, Object> map = new HashMap<>(5);
        map.put("master", master);    // key需要跟ThreadLocal中的值对应
        map.put("slave", slave);

        // master 作为默认数据源
        dataSourceRouter.setDefaultTargetDataSource(master);
        dataSourceRouter.setTargetDataSources(map);
        return dataSourceRouter;
    }
}
