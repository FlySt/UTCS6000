package com.ncjk.utcs.common.listener;



import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;



/**
 * Created by swl on 2016/12/26.
 */
public class OjdbcDriverRegistrationListener implements ServletContextListener {
    private Logger logger = Logger.getLogger(OjdbcDriverRegistrationListener.class);
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                logger.info(String.format("deregistering jdbc driver: %s", driver));
            } catch (SQLException e) {
                logger.info(String.format("Error deregistering driver %s", driver), e);
            }

        }
    }
}
