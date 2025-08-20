package org.example.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Product {
    private int id;
    private int userId;
    private String name;
    private BigDecimal desiredPrice;
    private BigDecimal currentMinPrice;
    private LocalDateTime createdAt;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getDesiredPrice() {
        return desiredPrice;
    }
    public void setDesiredPrice(BigDecimal desiredPrice) {
        this.desiredPrice = desiredPrice;
    }

    public BigDecimal getCurrentMinPrice() {
        return currentMinPrice;
    }
    public void setCurrentMinPrice(BigDecimal currentMinPrice) {
        this.currentMinPrice = currentMinPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}