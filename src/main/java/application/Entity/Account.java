package application.Entity;


import javax.persistence.*;
import java.util.Date;

/**
 * Created by Vlad on 20-Mar-17.
 */
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String type;

    private double balance;

    private Date date;

    @ManyToOne
    private Client owner;

    public Account() {
    }

    public Account(long id, String type, double balance, Date date, Client owner) {
        this.id = id;
        this.type = type;
        this.balance = balance;
        this.date = date;
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }
}
