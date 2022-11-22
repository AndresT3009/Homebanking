package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class AccountDTO {
    private long id;
    Set<TransactionDTO> transactions = new HashSet<>();
    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private String accountType;

    public AccountDTO() {
    }

    public AccountDTO(Account account) {
        this.accountType= account.getAccountType();
        this.id=account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactions=account.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
    }

    public long getId() {
        return id;
    }

    public String getNumber() {return number;}

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountType() {return accountType;}

    public Set<TransactionDTO> getTransactions(){return transactions;}

    @Override
    public String toString() {
        return "AccountDTO{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", creationDate=" + creationDate +
                ", balance=" + balance +
                ", accountType='" + accountType + '\'' +
                '}';
    }
}
