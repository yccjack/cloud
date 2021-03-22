//package com.mystical.cloud.auth.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.PostConstruct;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.Statement;
//
///**
// * @Description:
// * @author: MysticalYcc
// * @Date: 2021/3/22
// */
//@Configuration
//@Slf4j
//public class DataSourceHelper {
//
//
//    @Value("${datasource_driver}")
//    private String driver; // com.mysql.cj.jdbc.Driver
//    @Value("${datasource_url}")
//    private String url; // jdbc:mysql://localhost:3306/pybbs?useSSL=false&characterEncoding=utf8
//    @Value("${datasource_username}")
//    private String username; // root
//    @Value("${datasource_password}")
//    private String password; // password
//
//    @PostConstruct
//    public void init() {
//        try {
//            Class.forName(driver);
//            URI uri = new URI(url.replace("jdbc:", ""));
//            String host = uri.getHost();
//            int port = uri.getPort();
//            String path = uri.getPath();
//            Connection connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port, username, password);
//            Statement statement = connection.createStatement();
//            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS `" + path.replace("/", "") + "` DEFAULT CHARACTER SET = `utf8` COLLATE `utf8_general_ci`;");
//            statement.close();
//            connection.close();
//        } catch (URISyntaxException | ClassNotFoundException | SQLException e) {
//            log.error(e.getMessage());
//        }
//    }
//}
