CREATE TABLE IF NOT EXISTS price_history (
    id SERIAL PRIMARY KEY,
    offer_id INT NOT NULL REFERENCES offers(id) ON DELETE CASCADE,
    price NUMERIC(12,2) NOT NULL,
    checked_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_price_history_offer_checked
    ON price_history(offer_id, checked_at DESC);