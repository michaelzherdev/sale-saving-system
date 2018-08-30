DELETE FROM products;
DELETE FROM sales;
DELETE FROM order_items;
DELETE FROM discounts;

INSERT INTO products (name, price)
VALUES ('Jeans', 9.50),
  ('T-shirt', 5.00),
  ('Pullover', 39.9),
  ('Scarf', 2.4),
  ('Shorts', 2.50),
  ('Sunglasses', 10.00),
  ('Shoes', 25.00),
  ('Cufflinks', 1.7),
  ('Coat', 52.00),
  ('Boots', 30.50);