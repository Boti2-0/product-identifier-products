-- Add columns for producer restrictions to the ProductInfo table
ALTER TABLE product_info
    ADD COLUMN google_producer_restrictions TEXT,
    ADD COLUMN bing_producer_restrictions TEXT,
    ADD COLUMN facebook_producer_restrictions TEXT;