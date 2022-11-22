package com.mindhub.homebanking.models;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    @OneToMany(mappedBy="client", fetch= FetchType.EAGER)
    Set<Account> accounts = new HashSet<>();
    @OneToMany(mappedBy="client", fetch= FetchType.EAGER)
    Set<ClientLoan> clientLoans = new HashSet<>();
    @OneToMany(mappedBy="client", fetch= FetchType.EAGER)
    Set<Card> cards = new HashSet<>();

    private String firstname;
    private String lastname;
    private String email;
    private String password;

    public Client() {
    }

    public Client(String firstname, String lastname, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
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
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public Set<Account> getAccounts(){return accounts;}
    public void addAccount(Account account){
        account.setClient(this);
        accounts.add(account);
    }

    public Set<Card> getCards() {return cards;}
    public void addCards(Card card) {
        card.setClient(this);
        cards.add(card);
    }
    public Set<ClientLoan> getLoan() {return clientLoans;}

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
