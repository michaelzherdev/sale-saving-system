DROP DATABASE IF EXISTS salesystem;
CREATE DATABASE salesystem;
\c salesystem;

DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS sales;
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS discounts;

CREATE TABLE products (
  id    SERIAL PRIMARY KEY NOT NULL,
  name  CHARACTER(50)      NOT NULL,
  price REAL               NOT NULL
);
CREATE UNIQUE INDEX products_unique_name_idx
  ON products (name);


CREATE TABLE sales (
  id                 SERIAL PRIMARY KEY NOT NULL,
  cost               REAL               NOT NULL,
  cost_with_discount REAL,
  date               TIMESTAMP          NOT NULL DEFAULT now()
);

CREATE TABLE order_items (
  id         SERIAL PRIMARY KEY NOT NULL,
  sale_id    INTEGER,
  product_id INTEGER,
  quantity   INTEGER            NOT NULL,
  sum        REAL               NOT NULL DEFAULT 0,
  FOREIGN KEY (product_id) REFERENCES products (id),
  FOREIGN KEY (sale_id) REFERENCES sales (id) ON DELETE CASCADE
);

CREATE TABLE discounts (
  id         SERIAL PRIMARY KEY NOT NULL,
  time_start TIMESTAMP          NOT NULL DEFAULT now(),
  time_end   TIMESTAMP,
  amount     INTEGER            NOT NULL DEFAULT 0,
  product_id INTEGER,
  FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
);

INSERT INTO products (name, price)
VALUES ('Jeans', 9.50),
  ('T-shirt', 5.00),
  ('Pullover', 39.99),
  ('Scarf', 2.40),
  ('Shorts', 2.50),
  ('Sunglasses', 10.00),
  ('Shoes', 25.00),
  ('Cufflinks', 1.65),
  ('Coat', 52.00),
  ('Boots', 30.50);