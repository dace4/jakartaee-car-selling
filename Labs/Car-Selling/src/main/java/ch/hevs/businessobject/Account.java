package ch.hevs.businessobject;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int aid;
    private String firstname;
    private String lastname;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "phoneNumber", column = @Column(name = "phoneNumber")),
            @AttributeOverride(name = "email", column = @Column(name = "email")) })
    private ContactInfo contactInfo = new ContactInfo();
    private int balance;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private Seller seller;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private Buyer buyer;
    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhoneNumber() {
        return contactInfo.getPhoneNumber();
    }

    public void setPhoneNumber(String phoneNumber) {
        contactInfo.setPhoneNumber(phoneNumber);
    }

    public String getEmail() {
        return contactInfo.getEmail();
    }

    public void setEmail(String email) {
        contactInfo.setEmail(email);
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    @Override
    public String toString() {
        return "Account{" +
                "aid=" + aid +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", balance=" + balance +
                '}';
    }
}
