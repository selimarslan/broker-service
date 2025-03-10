CREATE TABLE users (
    id INT PRIMARY KEY,
    username VARCHAR(50),
    password VARCHAR(100),
    role VARCHAR(50)
);

CREATE TABLE assets (
    id INT PRIMARY KEY,
    customer_Id INT,
    asset_Name VARCHAR(50),
    size INT,
    usable_Size INT
);

CREATE TABLE orders (
    id INT PRIMARY KEY,
    customer_Id INT,
    asset_Name VARCHAR(50),
    order_Side VARCHAR(50),
    size INT,
    price DECIMAL(10, 2),
    status VARCHAR(50),
    created_Date TIMESTAMP
);
