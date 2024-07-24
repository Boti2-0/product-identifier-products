-- Create table ProductInfo
CREATE TABLE product_info (
                              id BIGSERIAL PRIMARY KEY,
                              name VARCHAR(255) NOT NULL,
                              category VARCHAR(255),
                              average_sale DOUBLE PRECISION,
                              gravity_range VARCHAR(255),
                              upsell_available BOOLEAN,
                              affiliate_link VARCHAR(255),
                              google_ads_available BOOLEAN,
                              bing_ads_available BOOLEAN,
                              facebook_ads_available BOOLEAN
);

-- Create table for Google Keywords associated with ProductInfo
CREATE TABLE product_info_google_keywords (
                                              product_info_id BIGINT NOT NULL,
                                              keyword VARCHAR(255),
                                              FOREIGN KEY (product_info_id) REFERENCES product_info(id) ON DELETE CASCADE
);

-- Create table for Bing Keywords associated with ProductInfo
CREATE TABLE product_info_bing_keywords (
                                            product_info_id BIGINT NOT NULL,
                                            keyword VARCHAR(255),
                                            FOREIGN KEY (product_info_id) REFERENCES product_info(id) ON DELETE CASCADE
);

-- Create table for Facebook Keywords associated with ProductInfo
CREATE TABLE product_info_facebook_keywords (
                                                product_info_id BIGINT NOT NULL,
                                                keyword VARCHAR(255),
                                                FOREIGN KEY (product_info_id) REFERENCES product_info(id) ON DELETE CASCADE
);

-- Alter table Product to add foreign key to ProductInfo
ALTER TABLE product
    ADD COLUMN product_info_id BIGINT,
ADD CONSTRAINT fk_product_info
FOREIGN KEY (product_info_id) REFERENCES product_info(id);

-- Drop the google_ads_available column from Product table
ALTER TABLE product
DROP COLUMN google_ads_available;