insert into PRODUCTS
(product_Id, short_name, long_name) values
(35455, 'Black TShirt', 'Black TShirt from hell');

insert into PRICES
(price_Id, product_Id, brand_Id, price_List_Id, start_Date, end_Date, priority, currency, money) values
(1, 35455, 1, 1, '2020-06-14 00.00.00', '2020-12-31 23.59.59', 1, 'EUR', 35.50),
(2, 35455, 1, 1, '2020-06-14 00.00.00', '2021-12-31 23.59.59', 0, 'EUR', 45.50),
(3, 35455, 1, 2, '2020-06-14 15.00.00', '2020-06-14 18.30.00', 1, 'EUR', 25.45),
(4, 35455, 1, 3, '2020-06-15 00.00.00', '2020-06-15 11.00.00', 1, 'EUR', 30.50),
(5, 35455, 1, 4, '2020-06-15 16.00.00', '2020-12-31 23.59.59', 1, 'EUR', 38.95);