package ch.hevs.presentation;

import java.io.Serializable;
import java.util.List;

import ch.hevs.businessobject.Account;
import ch.hevs.businessobject.Buyer;
import ch.hevs.businessobject.Car;
import ch.hevs.businessobject.CarBrand;
import ch.hevs.businessobject.Color;
import ch.hevs.businessobject.ModelYear;
import ch.hevs.businessobject.Seller;
import ch.hevs.service.CarSelling;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@SessionScoped
@Named("carSellingBean")
public class CarSellingBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Car> cars;
    private List<Seller> sellers;
    private List<Buyer> buyers;
    private List<Account> accounts;
    private List<CarBrand> carBrands;
    private List<Color> colors;
    private List<ModelYear> modelYears;

    private String message;

    @Inject
    private CarSelling carSelling;

    @PostConstruct
    public void initialize() {
        refreshData();
    }

    public void refreshData() {
        this.cars = carSelling.getCars();
        this.sellers = carSelling.getSellers();
        this.buyers = carSelling.getBuyers();
        this.accounts = carSelling.getAccounts();
        this.carBrands = carSelling.getCarBrands();
        this.colors = carSelling.getColors();
        this.modelYears = carSelling.getModelYears();
    }

    public String refresh() {
        refreshData();
        this.message = "Data refreshed successfully.";
        return null;
    }

    public List<Car> getCars() {
        return cars;
    }

    public List<Seller> getSellers() {
        return sellers;
    }

    public List<Buyer> getBuyers() {
        return buyers;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public List<CarBrand> getCarBrands() {
        return carBrands;
    }

    public List<Color> getColors() {
        return colors;
    }

    public List<ModelYear> getModelYears() {
        return modelYears;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}