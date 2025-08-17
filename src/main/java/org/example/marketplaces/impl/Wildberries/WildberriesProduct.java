package org.example.marketplaces.impl.Wildberries;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.marketplaces.api.Product;

class WildberriesProduct implements Product {

    private final String json;
    private final ObjectMapper mapper = new ObjectMapper();

    public WildberriesProduct(String json) {
        this.json = json;
    }

    @Override
    public double price() throws JsonProcessingException {
        final JsonNode root = mapper.readTree(json);

        return root
                .get("data")
                .get("products")
                .get(0)
                .get("sizes")
                .get(0)
                .get("price")
                .get("product")
                .asLong() / 100.0;
    }
}
