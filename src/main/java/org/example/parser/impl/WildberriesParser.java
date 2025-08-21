package org.example.parser.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.parser.MarketplaceParser;
import org.example.parser.dto.OfferInfo;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WildberriesParser implements MarketplaceParser {

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean supports(String url) {
        return url.contains("wildberries.ru");
    }

    @Override
    public OfferInfo parseByUrl(String url) throws Exception {
        String article = extractArticle(url);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://card.wb.ru/cards/v2/detail?curr=rub&dest=-1255987&nm=" + article))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonNode root = mapper.readTree(response.body());

        JsonNode productNode = root.path("data").path("products").get(0);

        OfferInfo info = new OfferInfo();
        info.setMarketplace("Wildberries");
        info.setArticle(article);
        info.setUrl(url);
        info.setPrice(BigDecimal.valueOf(
                productNode.path("sizes").get(0).path("price").path("product").asLong() / 100.0
        ));
        boolean isActive = productNode.path("selling").asBoolean(true);
        info.setStatus(isActive ? "active" : "inactive");

        return info;
    }

    private String extractArticle(String url) {
        String[] parts = url.split("/");
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals("catalog")) {
                return parts[i + 1]; // артикул после catalog
            }
        }
        throw new IllegalArgumentException("Не удалось извлечь артикул: " + url);
    }
}