package org.example;

import org.example.marketplaces.api.Marketplace;
import org.example.marketplaces.impl.Wildberries.Wildberries;

import java.net.http.HttpClient;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        Marketplace wildberries = new Wildberries(client);
        System.out.println(wildberries.product("258013489").price());
    }
}

