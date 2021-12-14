package com.emlakjet.task.mi.model;

import java.io.Serializable;

public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    private String description;
    private String invoiceId;
    private long id;
    private float amount;
    private long userId;
    private boolean isApproved;

    public Invoice(){

    }

    public Invoice(String description, String invoiceId, long id, float amount, long userId) {
        this.description = description;
        this.invoiceId = invoiceId;
        this.id = id;
        this.amount = amount;
        this.userId = userId;
    }

    public Invoice(String description, String invoiceId, float amount) {
        this.description = description;
        this.invoiceId = invoiceId;
        this.amount = amount;
        this.userId = userId;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

}
