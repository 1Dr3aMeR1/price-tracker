CREATE TABLE IF NOT EXISTS notifications (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    product_id INT,
    offer_id INT,
    event_type TEXT NOT NULL,
    old_price NUMERIC(12,2),
    new_price NUMERIC(12,2),
    triggered_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
    status TEXT NOT NULL DEFAULT 'pending',
    tries INT NOT NULL DEFAULT 0,
    last_error TEXT
    );

CREATE INDEX IF NOT EXISTS idx_notifications_status
    ON notifications(status);