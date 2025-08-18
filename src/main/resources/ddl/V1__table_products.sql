CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    name TEXT NOT NULL,
    desired_price NUMERIC(12,2),
    current_min_price NUMERIC(12,2),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);