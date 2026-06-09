package ch.hevs.businessobject;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int coid;

    private String colorName;

    @OneToMany(mappedBy = "color")
    private List<Car> cars;

    public int getCoid() {
        return coid;
    }

    public void setCoid(int coid) {
        this.coid = coid;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    @Override
    public String toString() {
        return "Color{" +
                "coid=" + coid +
                ", colorName='" + colorName + '\'' +
                '}';
    }
}
