package org.example.springdatajpa;

import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DatabaseConfiguration {

    public DatabaseConfiguration(DataSource dataSource) throws SQLException {
//        Server.createTcpServer("-tcpPort" ,"9092", "-tcpAllowOthers").start();
//        Server.startWebServer(dataSource.getConnection());
    }
}
