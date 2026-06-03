package ch.hevs.businessobject;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class CarBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cbid;

    private String name;

    @OneToMany(mappedBy = "carBrand")
    private List<Car> cars;

    public int getCbid() {
        return cbid;
    }

    public void setCbid(int cbid) {
        this.cbid = cbid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}