package ch.hevs.businessobject;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sid;

    @OneToOne
    @JoinColumn(name = "AID")
    private Account account;

    @OneToMany(mappedBy = "seller")
    private List<Car> cars;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "sid=" + sid +
                ", account=" + account +
                '}';
    }
}
