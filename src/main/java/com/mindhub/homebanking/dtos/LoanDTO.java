package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Loan;

import java.util.ArrayList;
import java.util.List;

public class LoanDTO {

    private long id;

    private List<Integer> payment = new ArrayList<>();

    private String name;

    private Double maxAmount;

    private Double interest;

   public LoanDTO(){
   }

    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.payment = loan.getPayment();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.interest= loan.getInterest();
    }

    public long getId() {
        return id;
    }

    public List<Integer> getPayment() {
        return payment;
    }

    public void setPayment(List<Integer> payment) {
        this.payment = payment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Double getInterest() {
        return interest;
    }
}
