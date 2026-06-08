package ch.hevs.test;

import ch.hevs.businessobject.Account;
import ch.hevs.businessobject.Buyer;
import ch.hevs.businessobject.Car;
import ch.hevs.businessobject.CarBrand;
import ch.hevs.businessobject.Color;
import ch.hevs.businessobject.ModelYear;
import ch.hevs.businessobject.Seller;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.Test;

public class PopulateDB {
	
	@Test
	public void populate() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("carSellingPU_unitTest"); // Check your persistence.xml unit name
        EntityManager em = emf.createEntityManager();
        
        try {
            em.getTransaction().begin();

            CarBrand bmw = new CarBrand();
            bmw.setName("BMW");
            em.persist(bmw);

            CarBrand audi = new CarBrand();
            audi.setName("Audi");
            em.persist(audi);
            
            CarBrand merc = new CarBrand();
            merc.setName("Mercedes");
            em.persist(merc);
            
            Color blackColor = new Color();
            blackColor.setColorName("Black");
            em.persist(blackColor);
            
            Color blue = new Color();
            blue.setColorName("Blue");
            em.persist(blue);
            
            ModelYear newestModelYear = new ModelYear();
            newestModelYear.setYear(2026);
            em.persist(newestModelYear);

            // 3. Create a Seller (Inherits from Account)
            Account accountSellerAccount = new Account();
            accountSellerAccount.setFirstname("John");
            accountSellerAccount.setLastname("Doe");
            accountSellerAccount.setPhoneNumber("+41765239065");
            accountSellerAccount.setEmail("john.doe@gmail.com");
            em.persist(accountSellerAccount);
            
            Seller seller1 = new Seller();
            seller1.setAccount(accountSellerAccount);
            em.persist(seller1);

            // 4. Create Cars and link them
            Car car1 = new Car();
            car1.setCarBrand(merc);
            car1.setColor(blackColor);
            car1.setModelYear(newestModelYear);
            car1.setSeller(seller1);
            car1.setMiles(5000);
            car1.setPrice(25000f);
            car1.setHp(500);
            em.persist(car1);
            

            // 5. Create a Buyer (Inherits from Account)
            Account buyerAccount = new Account();
            buyerAccount.setFirstname("Alice");
            buyerAccount.setLastname("McScotty");
            buyerAccount.setPhoneNumber("+41790052354");
            buyerAccount.setEmail("alice.mcscotty@gmx.com");
            em.persist(buyerAccount);
            
            Buyer buyer1 = new Buyer();
            buyer1.setAccount(buyerAccount);
            em.persist(buyer1);
            

            em.getTransaction().commit();
            System.out.println("Database successfully populated with Car Selling data!");
            
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}