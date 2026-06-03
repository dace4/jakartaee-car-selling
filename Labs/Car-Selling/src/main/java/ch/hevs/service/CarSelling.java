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
import jakarta.persistence.PersistenceContextType;
import jakarta.transaction.Transactional;

@RequestScoped
public class CarSelling implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "carSellingPU", type = PersistenceContextType.TRANSACTION)
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

    public Car getCar(int cid) {
        return em.createQuery("FROM Car c WHERE c.cid = :cid", Car.class)
                 .setParameter("cid", cid)
                 .getSingleResult();
    }

    public Account getAccount(int aid) {
        return em.createQuery("FROM Account a WHERE a.aid = :aid", Account.class)
                 .setParameter("aid", aid)
                 .getSingleResult();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void addCar(Car car) {
        em.persist(car);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void addAccount(Account account) {
        em.persist(account);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void addSeller(Seller seller) {
        em.persist(seller);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void addBuyer(Buyer buyer) {
        em.persist(buyer);
    }
}