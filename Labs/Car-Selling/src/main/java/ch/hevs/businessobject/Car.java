package ch.hevs.businessobject;

import jakarta.persistence.*;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cid;

    @ManyToOne
    @JoinColumn(name = "CBID")
    private CarBrand carBrand;

    @ManyToOne
    @JoinColumn(name = "COID")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "MID")
    private ModelYear modelYear;

    @ManyToOne
    @JoinColumn(name = "SID")
    private Seller seller;

    private int miles;
    private float price;
    private int hp;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public CarBrand getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(CarBrand carBrand) {
        this.carBrand = carBrand;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ModelYear getModelYear() {
        return modelYear;
    }

    public void setModelYear(ModelYear modelYear) {
        this.modelYear = modelYear;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public int getMiles() {
        return miles;
    }

    public void setMiles(int miles) {
        this.miles = miles;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}