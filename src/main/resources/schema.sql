CREATE TABLE products (
  id    SERIAL PRIMARY KEY NOT NULL,
  name  VARCHAR            NOT NULL,
  price REAL               NOT NULL
);
CREATE UNIQUE INDEX products_unique_name_idx
  ON products (name);


CREATE TABLE sales (
  id                 SERIAL PRIMARY KEY NOT NULL,
  cost               REAL               NOT NULL,
  cost_with_discount REAL               NOT NULL DEFAULT 0,
  date               TIMESTAMP          NOT NULL DEFAULT now()
);

CREATE TABLE order_items (
  id         SERIAL PRIMARY KEY NOT NULL,
  sale_id    INTEGER,
  product_id INTEGER,
  quantity   INTEGER            NOT NULL,
  sum        REAL DEFAULT 0,
  FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE,
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