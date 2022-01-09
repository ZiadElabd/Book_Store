package software.project.backend.Database;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

public class Source {

    private static DataSource source = null;

    private Source(){}

    @Bean @Primary
    public static DataSource getDataSource(){
        if (source == null){
            source = DataSourceBuilder
                    .create()
                    .username("root")
                    .password("root")
                    .url("jdbc:mysql://localhost:3306/online_store")
                    .driverClassName("com.mysql.cj.jdbc.Driver")
                    .build();
        }
        return source;
    }
}
