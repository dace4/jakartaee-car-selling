package ch.hevs.service;

import java.io.Serializable;
import java.util.List;

import ch.hevs.businessobject.Account;
import ch.hevs.businessobject.Buyer;
import ch.hevs.businessobject.Car;
import ch.hevs.businessobject.CarBrand;
import ch.hevs.businessobject.Color;
import ch.hevs.businessobject.ModelYear;
import ch.hevs.businessobject.Seller;
import ch.hevs.exception.BankException;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@RequestScoped
public class CarSelling implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "carSellingPU")
    private EntityManager em;

    @Inject
    private HttpServletRequest request;

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
    public void addCar(int sellerId, int carBrandId, int colorId, int modelYearId, int miles, float price, int hp) {
        Seller seller = em.find(Seller.class, sellerId);
        CarBrand carBrand = em.find(CarBrand.class, carBrandId);
        Color color = em.find(Color.class, colorId);
        ModelYear modelYear = em.find(ModelYear.class, modelYearId);

        if (seller == null || carBrand == null || color == null || modelYear == null) {
            throw new IllegalArgumentException("Please select valid car data before saving.");
        }

        Car car = new Car();
        car.setSeller(seller);
        car.setCarBrand(carBrand);
        car.setColor(color);
        car.setModelYear(modelYear);
        car.setMiles(miles);
        car.setPrice(price);
        car.setHp(hp);
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
        // Buying a car must be atomic: either money moves and the car is removed,
        // or nothing changes if validation fails.
        Car car = em.find(Car.class, carId);
        if (car == null) {
            throw new IllegalArgumentException("Car with ID " + carId + " not found.");
        }

        Seller seller = car.getSeller();
        if (seller == null) {
            throw new IllegalStateException("This vehicle does not have an assigned seller.");
        }

        Buyer buyer = em.find(Buyer.class, buyerId);
        if (buyer == null) {
            throw new IllegalArgumentException("Buyer with ID " + buyerId + " not found.");
        }

        if (buyer.getAccount().getAid() == seller.getAccount().getAid()) {
            throw new IllegalArgumentException("Operation canceled: You cannot buy your own vehicle.");
        }

        Account buyerAccount = buyer.getAccount();
        Account sellerAccount = seller.getAccount();

        int carPrice = (int) car.getPrice();
        if (buyerAccount.getBalance() < carPrice) {
            throw new BankException("Insufficient balance! Car costs " + carPrice
                    + ", but buyer balance is " + buyerAccount.getBalance());
        }

        buyerAccount.setBalance(buyerAccount.getBalance() - carPrice);
        sellerAccount.setBalance(sellerAccount.getBalance() + carPrice);

        // A successful purchase removes the sold car from the marketplace.
        em.remove(car);
    }

    @Transactional
    public void removeCarAsAdmin(int carId) {
        if (request == null || !request.isUserInRole("admin")) {
            throw new SecurityException("Only users with the admin role can remove cars.");
        }

        Car car = em.find(Car.class, carId);
        if (car == null) {
            throw new IllegalArgumentException("Car with ID " + carId + " not found.");
        }

        em.remove(car);
    }
}
