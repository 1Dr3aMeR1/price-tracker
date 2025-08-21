package org.example.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Offer {
    private int id;
    private int productId;
    private String marketplace;
    private String url;
    private String article;
    private BigDecimal lastPrice;
    private LocalDateTime lastChecked;
    private String status;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getMarketplace() {
        return marketplace;
    }
    public void setMarketplace(String marketplace) {
        this.marketplace = marketplace;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getArticle() {
        return article;
    }
    public void setArticle(String article) {
        this.article = article;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }
    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }

    public LocalDateTime getLastChecked() {
        return lastChecked;
    }
    public void setLastChecked(LocalDateTime lastChecked) {
        this.lastChecked = lastChecked;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}