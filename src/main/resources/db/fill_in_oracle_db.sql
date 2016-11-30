DELETE FROM products;
DELETE FROM sales;

INSERT INTO products (name, price)
  SELECT 'Jeans', 9.50 FROM dual UNION ALL
   SELECT 'T-shirt', 5.00 FROM dual UNION ALL
   SELECT 'Pullover', 39.9 FROM dual UNION ALL
   SELECT 'Scarf', 2.4 FROM dual UNION ALL
   SELECT 'Shorts', 2.50 FROM dual UNION ALL
   SELECT 'Sunglasses', 10.00 FROM dual UNION ALL
   SELECT 'Shoes', 25.00 FROM dual UNION ALL
   SELECT 'Cufflinks', 1.7 FROM dual UNION ALL
   SELECT 'Coat', 52.00 FROM dual UNION ALL
   SELECT 'Boots', 30.50 FROM dual;