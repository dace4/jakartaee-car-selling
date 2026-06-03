package ch.hevs.businessobject;

import jakarta.persistence.*;

@Entity
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int baid;

    @ManyToOne
    @JoinColumn(name = "AID")
    private Account account;

    private int saldo;

    public int getBaid() {
        return baid;
    }

    public void setBaid(int baid) {
        this.baid = baid;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
}