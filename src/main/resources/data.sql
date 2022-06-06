create sequence PRODUCT_ID_SEQUENCE
  start WITH 1 increment BY 100 minvalue 1;

create sequence PRICE_ID_SEQUENCE
  start WITH 1 increment BY 100 minvalue 1;

insert into PRODUCTS
(product_Id, short_name, long_name, version) values
(35455, 'Black TShirt', 'Black TShirt from hell', 0),
(35454, 'Red TShirt', 'Red TShirt from hell', 0);

insert into PRICES
(price_Id, product_Id, brand_Id, start_Date, end_Date, priority, currency, money) values
(1, 35455, 1, '2020-06-14 00.00.00', '2020-12-31 23.59.59', 0, 'EUR', 35.50),
(2, 35455, 1, '2020-06-14 15.00.00', '2020-06-14 18.30.00', 1, 'EUR', 25.45),
(3, 35455, 1, '2020-06-15 00.00.00', '2020-06-15 11.00.00', 1, 'EUR', 30.50),
(4, 35455, 1, '2020-06-15 16.00.00', '2020-12-31 23.59.59', 1, 'EUR', 38.95),

(5, 35454, 1, '2020-06-14 00.00.00', '2020-12-31 23.59.59', 1, 'EUR', 35.50),
(6, 35454, 1, '2020-06-14 00.00.00', '2021-12-31 23.59.59', 0, 'EUR', 45.50),
(7, 35454, 1, '2020-06-14 15.00.00', '2020-06-14 18.30.00', 1, 'EUR', 25.45),
(8, 35454, 1, '2020-06-15 00.00.00', '2020-06-15 11.00.00', 1, 'EUR', 30.50),
(9, 35454, 1, '2020-06-15 16.00.00', '2020-12-31 23.59.59', 1, 'EUR', 38.95);

