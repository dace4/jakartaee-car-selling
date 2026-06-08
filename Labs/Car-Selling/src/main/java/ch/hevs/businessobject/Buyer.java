package ch.hevs.businessobject;

import jakarta.persistence.*;

@Entity
public class Buyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bid;

    @OneToOne
    @JoinColumn(name = "AID")
    private Account account;

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
