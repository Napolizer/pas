package org.pl.user.module.adapter.data.config;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@DataSourceDefinition(
        name = "java:app/jdbc/tksUserModulePU",
        className = "org.postgresql.ds.PGSimpleDataSource",
        user = "docker",
        password = "docker",
        serverName = "${ENV=DB_HOST:localhost}",
        portNumber = 5432,
        databaseName = "tksUserModule",
        initialPoolSize = 1,
        minPoolSize = 0,
        maxPoolSize = 1,
        maxIdleTime = 10)

@Stateless
public class DataSourceConfig {
    @PersistenceContext(unitName = "tksUserModulePU")
    private EntityManager em;
}
