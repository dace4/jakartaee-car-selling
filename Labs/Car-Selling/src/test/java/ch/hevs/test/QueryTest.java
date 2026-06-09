package ch.hevs.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import ch.hevs.businessobject.Account;
import ch.hevs.businessobject.Buyer;
import ch.hevs.businessobject.Car;
import ch.hevs.businessobject.CarBrand;
import ch.hevs.businessobject.Color;
import ch.hevs.businessobject.ModelYear;
import ch.hevs.businessobject.Seller;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class QueryTest {

    @Test
    public void test() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String cmd;
            while (true) {
                System.out.println("Write a query [or 'populate' or 'quit']: ");
                cmd = reader.readLine();

                if ("populate".equals(cmd)) {
                    populate();
                } else if ("quit".equals(cmd)) {
                    System.out.println("The End");
                    break;
                } else {
                    executeRequest(cmd);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void executeRequest(String cmd) {
        List<?> result = null;
        EntityTransaction tx = null;
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("carSellingPU_unitTest");
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            result = em.createQuery(cmd).getResultList();
            System.out.println("Submitted query: " + cmd);
            Iterator<?> it = result.iterator();
            while (it.hasNext()) {
                System.out.println(it.next());
            }
            tx.commit();

        } catch (Exception e) {
            System.err.println(e.getMessage());
            try {
                if (tx != null && tx.isActive()) {
                    tx.rollback();
                }
            } catch (IllegalStateException e1) {
                e1.printStackTrace();
            } catch (SecurityException e1) {
                e1.printStackTrace();
            }
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }

    public static void populate() {
        EntityTransaction tx = null;
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("carSellingPU_unitTest");
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            long carCount = em.createQuery("SELECT COUNT(c) FROM Car c", Long.class).getSingleResult();
            if (carCount > 0) {
                tx.rollback();
                System.out.println("Database already contains test data. Run CreateSchemaTest before populate to reset it.");
                return;
            }

            CarBrand bmw = new CarBrand();
            bmw.setName("BMW");
            em.persist(bmw);

            CarBrand audi = new CarBrand();
            audi.setName("Audi");
            em.persist(audi);

            CarBrand mercedes = new CarBrand();
            mercedes.setName("Mercedes");
            em.persist(mercedes);

            Color black = new Color();
            black.setColorName("Black");
            em.persist(black);

            Color blue = new Color();
            blue.setColorName("Blue");
            em.persist(blue);

            ModelYear year2024 = new ModelYear();
            year2024.setYear(2024);
            em.persist(year2024);

            ModelYear year2025 = new ModelYear();
            year2025.setYear(2025);
            em.persist(year2025);

            Account sellerAccount = new Account();
            sellerAccount.setFirstname("John");
            sellerAccount.setLastname("Doe");
            sellerAccount.setPhoneNumber("+41765239065");
            sellerAccount.setEmail("john.doe@gmail.com");
            sellerAccount.setBalance(5000);

            Seller seller = new Seller();
            seller.setAccount(sellerAccount);
            sellerAccount.setSeller(seller);
            em.persist(sellerAccount);

            Account buyerAccount = new Account();
            buyerAccount.setFirstname("Alice");
            buyerAccount.setLastname("McScotty");
            buyerAccount.setPhoneNumber("+41790052354");
            buyerAccount.setEmail("alice.mcscotty@gmx.com");
            buyerAccount.setBalance(60000);

            Buyer buyer = new Buyer();
            buyer.setAccount(buyerAccount);
            buyerAccount.setBuyer(buyer);
            em.persist(buyerAccount);

            Car car1 = new Car();
            car1.setCarBrand(mercedes);
            car1.setColor(black);
            car1.setModelYear(year2025);
            car1.setSeller(seller);
            car1.setMiles(5000);
            car1.setPrice(25000f);
            car1.setHp(500);
            em.persist(car1);

            Car car2 = new Car();
            car2.setCarBrand(bmw);
            car2.setColor(blue);
            car2.setModelYear(year2024);
            car2.setSeller(seller);
            car2.setMiles(15000);
            car2.setPrice(18000f);
            car2.setHp(220);
            em.persist(car2);

            tx.commit();
            System.out.println("Database successfully populated with Car Selling data!");

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }
}
