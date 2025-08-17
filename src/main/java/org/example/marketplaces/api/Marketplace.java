package org.example.marketplaces.api;

public interface Marketplace {
    Product product(String id) throws Exception;
}
