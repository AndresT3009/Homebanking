package com.mindhub.homebanking.dtos;
import com.mindhub.homebanking.models.Client;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {

    private long id;
    Set<AccountDTO> accounts = new HashSet<>();
    Set<ClientLoanDTO> clientLoans;
    Set<CardDTO> cards = new HashSet<>();
    private String firstname;
    private String lastname;
    private String email;

    private String password;

    public ClientDTO() {
    }
    public ClientDTO(Client client) {
        this.id=client.getId();
        this.firstname= client.getFirstname();
        this.lastname= client.getLastname();
        this.email= client.getEmail();
        this.password= client.getPassword();
        this.accounts=client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
        this.clientLoans=client.getLoan().stream().map(clientLoan -> new ClientLoanDTO(clientLoan)).collect(Collectors.toSet());
        //this.clientLoans=client.getLoan().stream().map(loan -> new ClientLoanDTO(loan)).collect(Collectors.toSet());
        this.cards=client.getCards().stream().map(card -> new CardDTO(card)).collect(Collectors.toSet());
    }

    public long getId() {return id;}

        public String getFirstname() {
            return firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {return password;}

    public Set<ClientLoanDTO> getClientLoans(){return clientLoans;}

        public Set<CardDTO> getCards(){return cards;}

        public Set<AccountDTO> getAccounts(){return accounts;}

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
