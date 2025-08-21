package org.example.parser;

import org.example.parser.dto.OfferInfo;

public interface MarketplaceParser {
    boolean supports(String url);          // подходит ли парсер под эту ссылку
    OfferInfo parseByUrl(String url) throws Exception; // главный метод
}