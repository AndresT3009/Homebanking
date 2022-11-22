package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;

    private TransactionType type;
    private Double amount;
    private String description;
    private LocalDateTime transactionDate;

    public Transaction() {
    }

    public Transaction(TransactionType type, Double amount, String description, LocalDateTime transactionDate) {
        this.id=id;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
    }

    public long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        amount = amount;
    }

    public String getDescription() {return description;}

    public void setDescription(String description) {
        description = description;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        transactionDate = transactionDate;
    }

    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", Type='" + type + '\'' +
                ", Amount='" + amount + '\'' +
                ", Description='" + description + '\'' +
                ", Transaction-Date='" + transactionDate + '\'' +
                '}';
    }
}
