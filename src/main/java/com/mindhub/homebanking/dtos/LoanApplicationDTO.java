package com.mindhub.homebanking.dtos;

public class LoanApplicationDTO {

    private long id;

    private Double amount;

    private int payments;

    private String accountnumber;

    public LoanApplicationDTO() {
    }

    public LoanApplicationDTO(long id, Double amount, int payments, String accountnumber) {
        this.id = id;
        this.amount = amount;
        this.payments = payments;
        this.accountnumber = accountnumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }
}


