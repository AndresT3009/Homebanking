package com.mindhub.homebanking.dtos;
import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {
    private long id;
    private String name;
    private long loan;
    private Double amount;
    private int payments;

    public ClientLoanDTO(){
    }

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.name = clientLoan.getLoan().getName();
        this.loan = clientLoan.getLoan().getId();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
    }

    public long getId() {
        return id;
    }

    public long getLoan() {
        return loan;
    }

    public Double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ClientLoan{" +
                "id=" + id +
                ", name=" + name +
                ", loan=" + loan +
                ", amount=" + amount +
                ", payments=" + payments +
                '}';
    }
}
