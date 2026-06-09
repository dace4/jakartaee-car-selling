package ch.hevs.test;

import java.util.EnumSet;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.junit.Test;

import ch.hevs.businessobject.Account;
import ch.hevs.businessobject.Buyer;
import ch.hevs.businessobject.Car;
import ch.hevs.businessobject.CarBrand;
import ch.hevs.businessobject.Color;
import ch.hevs.businessobject.ContactInfo;
import ch.hevs.businessobject.ModelYear;
import ch.hevs.businessobject.Seller;

public class CreateSchemaTest {

    @Test
    public void test() {
        MetadataSources metadataSources = new MetadataSources(
                new StandardServiceRegistryBuilder()
                        .applySetting("hibernate.dialect", "org.hibernate.dialect.HSQLDialect")
                        .applySetting("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver")
                        .applySetting("hibernate.connection.url", "jdbc:hsqldb:hsql://localhost/DB")
                        .applySetting("hibernate.connection.username", "sa")
                        .applySetting("hibernate.connection.password", "")
                        .build());

        metadataSources.addAnnotatedClass(Account.class);
        metadataSources.addAnnotatedClass(Buyer.class);
        metadataSources.addAnnotatedClass(Car.class);
        metadataSources.addAnnotatedClass(CarBrand.class);
        metadataSources.addAnnotatedClass(Color.class);
        metadataSources.addAnnotatedClass(ContactInfo.class);
        metadataSources.addAnnotatedClass(ModelYear.class);
        metadataSources.addAnnotatedClass(Seller.class);

        Metadata metadata = metadataSources.buildMetadata();

        SchemaExport schemaExport = new SchemaExport();
        schemaExport.setFormat(true);
        schemaExport.setOutputFile("schema.ddl");
        schemaExport.create(EnumSet.of(TargetType.DATABASE, TargetType.SCRIPT), metadata);
    }
}
