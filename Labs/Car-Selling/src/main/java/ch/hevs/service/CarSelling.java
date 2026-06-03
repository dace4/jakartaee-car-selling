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
}