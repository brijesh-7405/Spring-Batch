CREATE TABLE IF NOT EXISTS products
(
    productId int primary key,
    title varchar(255),
    description varchar(255),
    price varchar(10),
    discount varchar(2),
    discounted_price varchar(10)
);