package ch.hevs.service;

import java.io.Serializable;
import java.util.List;

import ch.hevs.businessobject.Account;
import ch.hevs.businessobject.BankAccount;
import ch.hevs.businessobject.Buyer;
import ch.hevs.businessobject.Car;
import ch.hevs.businessobject.CarBrand;
import ch.hevs.businessobject.Color;
import ch.hevs.businessobject.ModelYear;
import ch.hevs.businessobject.Seller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@RequestScoped
public class CarSelling implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "carSellingPU")
    private EntityManager em;

    public List<Car> getCars() {
        return em.createQuery("FROM Car", Car.class).getResultList();
    }

    public List<Seller> getSellers() {
        return em.createQuery("FROM Seller", Seller.class).getResultList();
    }

    public List<Buyer> getBuyers() {
        return em.createQuery("FROM Buyer", Buyer.class).getResultList();
    }

    public List<Account> getAccounts() {
        return em.createQuery("FROM Account", Account.class).getResultList();
    }

    public List<CarBrand> getCarBrands() {
        return em.createQuery("FROM CarBrand", CarBrand.class).getResultList();
    }

    public List<Color> getColors() {
        return em.createQuery("FROM Color", Color.class).getResultList();
    }

    public List<ModelYear> getModelYears() {
        return em.createQuery("FROM ModelYear", ModelYear.class).getResultList();
    }

    @Transactional
    public void addCar(Car car) {
        em.persist(car);
    }

    @Transactional
    public void addAccount(Account account) {
        em.persist(account);
    }

    @Transactional
    public void addSeller(Seller seller) {
        em.persist(seller);
    }

    @Transactional
    public void addBuyer(Buyer buyer) {
        em.persist(buyer);
    }
    
    @Transactional
    public void buyCar(int carId, int buyerId) {
    	// 1. Retrieve the target vehicle and identify its current seller
        Car car = em.find(Car.class, carId);
        if (car == null) {
            throw new IllegalArgumentException("Car with ID " + carId + " not found.");
        }
        
        Seller seller = car.getSeller();
        if (seller == null) {
            throw new IllegalStateException("This vehicle does not have an assigned seller.");
        }

        // 2. Retrieve the buyer wrapper profile 
        Buyer buyer = em.find(Buyer.class, buyerId);
        if (buyer == null) {
            throw new IllegalArgumentException("Buyer with ID " + buyerId + " not found.");
        }

        // Business Rule Constraint: Do not allow an account to purchase its own listing
        if (buyer.getAccount().getAid() == seller.getAccount().getAid()) {
            throw new IllegalArgumentException("Operation canceled: You cannot buy your own vehicle.");
        }

        // 3. Locate financial bank records matching both users' unique internal Account IDs
        List<BankAccount> buyerAccounts = em.createQuery(
            "FROM BankAccount b WHERE b.account.aid = :aid", BankAccount.class)
            .setParameter("aid", buyer.getAccount().getAid())
            .getResultList();

        List<BankAccount> sellerAccounts = em.createQuery(
            "FROM BankAccount b WHERE b.account.aid = :aid", BankAccount.class)
            .setParameter("aid", seller.getAccount().getAid())
            .getResultList();

        if (buyerAccounts.isEmpty()) {
            throw new ch.hevs.exception.BankException("Transaction denied: Buyer possesses no active bank accounts.");
        }
        if (sellerAccounts.isEmpty()) {
            throw new ch.hevs.exception.BankException("Transaction denied: Seller possesses no active bank accounts.");
        }

        BankAccount buyerBankAccount = buyerAccounts.get(0);
        BankAccount sellerBankAccount = sellerAccounts.get(0);

        // 4. Validate sufficient funds availability
        int carPrice = (int) car.getPrice(); 
        if (buyerBankAccount.getSaldo() < carPrice) {
            throw new ch.hevs.exception.BankException("Insufficient balance! Car costs " 
                + carPrice + ", but buyer balance is " + buyerBankAccount.getSaldo());
        }

        // 5. Financial Execution: Send and Receive money between statements
        buyerBankAccount.setSaldo(buyerBankAccount.getSaldo() - carPrice);
        sellerBankAccount.setSaldo(sellerBankAccount.getSaldo() + carPrice);

        // 6. Complete Sale: Permanently erase the car from the marketplace catalog 
        em.remove(car);
    }
    
}