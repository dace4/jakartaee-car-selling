package ch.hevs.businessobject;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int aid;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String email;

    @OneToOne(mappedBy = "account")
    private Seller seller;

    @OneToOne(mappedBy = "account")
    private Buyer buyer;

    @OneToMany(mappedBy = "account")
    private List<BankAccount> bankAccounts;
    
    public Account() {
    	
    }

    public Account(int aid, String firstname, String lastname, String phoneNumber, String email,
			 List<BankAccount> bankAccounts) {
		super();
		this.aid = aid;
		this.firstname = firstname;
		this.lastname = lastname;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.bankAccounts = bankAccounts;
	}

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
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}