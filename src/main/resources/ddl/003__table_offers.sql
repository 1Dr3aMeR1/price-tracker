CREATE TABLE IF NOT EXISTS offers (
    id SERIAL PRIMARY KEY,
    product_id INT NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    marketplace TEXT NOT NULL,
    url TEXT NOT NULL,
    article TEXT,
    last_price NUMERIC(12,2),
    last_checked TIMESTAMP WITH TIME ZONE,
    raw_response JSONB,
    status TEXT DEFAULT 'active',
    UNIQUE(product_id, marketplace, article),
    UNIQUE(url)
);

CREATE INDEX IF NOT EXISTS idx_offers_last_checked
    ON offers(last_checked);

CREATE INDEX IF NOT EXISTS idx_offers_marketplace_article
    ON offers(marketplace, article);