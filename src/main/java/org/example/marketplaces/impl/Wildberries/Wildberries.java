package org.example.marketplaces.impl.Wildberries;

import org.example.marketplaces.api.Marketplace;
import org.example.marketplaces.api.Product;
import org.example.marketplaces.impl.Wildberries.WildberriesProduct;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Wildberries implements Marketplace {

    private final HttpClient httpClient;

    public Wildberries(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Product product(String id) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://card.wb.ru/cards/v2/detail?curr=rub&dest=-1255987&nm=" + id))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return new WildberriesProduct(response.body());
    }
}
