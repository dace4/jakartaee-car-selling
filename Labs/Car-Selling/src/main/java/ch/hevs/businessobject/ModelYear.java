package ch.hevs.businessobject;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class ModelYear {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mid;

    private int year;

    @OneToMany(mappedBy = "modelYear")
    private List<Car> cars;

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}