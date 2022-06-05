create sequence if not exists PRODUCT_ID_SEQUENCE
  start WITH 1 increment BY 1 minvalue 1;

create sequence if not exists PRICE_ID_SEQUENCE
  start WITH 1 increment BY 1 minvalue 1;

insert into PRODUCTS
(product_Id, short_name, long_name, version) values
(354550, 'Black TShirt', 'Black TShirt from hell', 0),
(354540, 'Red TShirt', 'Red TShirt from hell', 0);

insert into PRICES
(price_Id, product_Id, brand_Id, price_List_Id, start_Date, end_Date, priority, currency, money) values
(100, 354550, 1, 1, '2020-06-14 00.00.00', '2020-12-31 23.59.59', 1, 'EUR', 35.50),
(200, 354550, 1, 1, '2020-06-14 00.00.00', '2021-12-31 23.59.59', 0, 'EUR', 45.50),
(300, 354550, 1, 2, '2020-06-14 15.00.00', '2020-06-14 18.30.00', 1, 'EUR', 25.45),
(400, 354550, 1, 3, '2020-06-15 00.00.00', '2020-06-15 11.00.00', 1, 'EUR', 30.50),
(500, 354550, 1, 4, '2020-06-15 16.00.00', '2020-12-31 23.59.59', 1, 'EUR', 38.95),

(600, 354540, 1, 1, '2020-06-14 00.00.00', '2020-12-31 23.59.59', 1, 'EUR', 35.50),
(700, 354540, 1, 1, '2020-06-14 00.00.00', '2021-12-31 23.59.59', 0, 'EUR', 45.50),
(800, 354540, 1, 2, '2020-06-14 15.00.00', '2020-06-14 18.30.00', 1, 'EUR', 25.45),
(900, 354540, 1, 3, '2020-06-15 00.00.00', '2020-06-15 11.00.00', 1, 'EUR', 30.50),
(1000,354540, 1, 4, '2020-06-15 16.00.00', '2020-12-31 23.59.59', 1, 'EUR', 38.95);