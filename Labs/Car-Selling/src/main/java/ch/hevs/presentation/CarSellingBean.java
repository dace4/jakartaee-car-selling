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
import jakarta.faces.context.FacesContext;
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
    private int selectedBuyerId;
    private int selectedSellerId;
    private int selectedCarBrandId;
    private int selectedColorId;
    private int selectedModelYearId;
    private int newCarMiles;
    private float newCarPrice;
    private int newCarHp;

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

    public String buyCar(int carId) {
        if (selectedBuyerId <= 0) {
            this.message = "Please select a buyer before purchasing a car.";
            return null;
        }

        try {
            carSelling.buyCar(carId, selectedBuyerId);
            refreshData();
            this.message = "Car " + carId + " was purchased successfully.";
        } catch (RuntimeException e) {
            this.message = e.getMessage();
        }

        return null;
    }

    public String addCar() {
        if (selectedSellerId <= 0 || selectedCarBrandId <= 0 || selectedColorId <= 0 || selectedModelYearId <= 0) {
            this.message = "Please select seller, brand, color and year before adding a car.";
            return null;
        }

        try {
            carSelling.addCar(selectedSellerId, selectedCarBrandId, selectedColorId, selectedModelYearId, newCarMiles,
                    newCarPrice, newCarHp);
            refreshData();
            resetNewCarForm();
            this.message = "Car added successfully.";
        } catch (RuntimeException e) {
            this.message = e.getMessage();
        }

        return null;
    }

    public String removeCarAsAdmin(int carId) {
        if (!isAdmin()) {
            this.message = "Only admin users can remove cars from the marketplace.";
            return null;
        }

        try {
            carSelling.removeCarAsAdmin(carId);
            refreshData();
            this.message = "Car " + carId + " was removed by admin.";
        } catch (RuntimeException e) {
            this.message = e.getMessage();
        }

        return null;
    }

    public boolean isAdmin() {
        return FacesContext.getCurrentInstance().getExternalContext().isUserInRole("admin");
    }

    public boolean isBuyer() {
        return FacesContext.getCurrentInstance().getExternalContext().isUserInRole("buyer");
    }

    public boolean isSeller() {
        return FacesContext.getCurrentInstance().getExternalContext().isUserInRole("seller");
    }

    public boolean isBuyerOrAdmin() {
        return isBuyer() || isAdmin();
    }

    public boolean isSellerOrAdmin() {
        return isSeller() || isAdmin();
    }

    private void resetNewCarForm() {
        selectedSellerId = 0;
        selectedCarBrandId = 0;
        selectedColorId = 0;
        selectedModelYearId = 0;
        newCarMiles = 0;
        newCarPrice = 0;
        newCarHp = 0;
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

    public int getSelectedBuyerId() {
        return selectedBuyerId;
    }

    public void setSelectedBuyerId(int selectedBuyerId) {
        this.selectedBuyerId = selectedBuyerId;
    }

    public int getSelectedSellerId() {
        return selectedSellerId;
    }

    public void setSelectedSellerId(int selectedSellerId) {
        this.selectedSellerId = selectedSellerId;
    }

    public int getSelectedCarBrandId() {
        return selectedCarBrandId;
    }

    public void setSelectedCarBrandId(int selectedCarBrandId) {
        this.selectedCarBrandId = selectedCarBrandId;
    }

    public int getSelectedColorId() {
        return selectedColorId;
    }

    public void setSelectedColorId(int selectedColorId) {
        this.selectedColorId = selectedColorId;
    }

    public int getSelectedModelYearId() {
        return selectedModelYearId;
    }

    public void setSelectedModelYearId(int selectedModelYearId) {
        this.selectedModelYearId = selectedModelYearId;
    }

    public int getNewCarMiles() {
        return newCarMiles;
    }

    public void setNewCarMiles(int newCarMiles) {
        this.newCarMiles = newCarMiles;
    }

    public float getNewCarPrice() {
        return newCarPrice;
    }

    public void setNewCarPrice(float newCarPrice) {
        this.newCarPrice = newCarPrice;
    }

    public int getNewCarHp() {
        return newCarHp;
    }

    public void setNewCarHp(int newCarHp) {
        this.newCarHp = newCarHp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
