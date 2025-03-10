INSERT INTO users (id, username, password, role)
VALUES (1, 'admin', '$2a$10$7cFCeIZOvWKzLQjF9g5VJu4LZu00JdLKBfYaBB5pBBe1g.ZdjJI.6', 'ADMIN');

INSERT INTO users (id, username, password, role)
VALUES (2, 'tom', '$2a$10$zVqDa4iQIZelR.cbbT5RfO.8jYvlcd2An4vvXMmWQvc5lbAGlNkde', 'CUSTOMER');

INSERT INTO users (id, username, password, role)
VALUES (3, 'john', '$2a$10$XyFue.ngNoKGlcb35MECpO52pkcGi8wsPdoVVHVWFKXEGgGfyClZy', 'CUSTOMER');

INSERT INTO users (id, username, password, role)
VALUES (4, 'ahmet', '$2a$10$WytNr156.O4TXdOy7LLi8uU3xTFT3mZ6nkjkr7uKFW5Pp9lhAfALW', 'CUSTOMER');


-- assets
INSERT INTO assets (id, customer_id, asset_name, size, usable_size)
VALUES (1, 2, 'TRY', 1000, 1000);

INSERT INTO assets (id, customer_id, asset_name, size, usable_size)
VALUES (2, 2, 'MSFT', 1000, 1000);

INSERT INTO assets (id, customer_id, asset_name, size, usable_size)
VALUES (3, 2, 'APPL', 1000, 1000);

INSERT INTO assets (id, customer_id, asset_name, size, usable_size)
VALUES (4, 2, 'AMZN', 1000, 1000);


INSERT INTO assets (id, customer_id, asset_name, size, usable_size)
VALUES (5, 3, 'TRY', 1000, 1000);

INSERT INTO assets (id, customer_id, asset_name, size, usable_size)
VALUES (6, 3, 'MSFT', 1000, 1000);

INSERT INTO assets (id, customer_id, asset_name, size, usable_size)
VALUES (7, 3, 'APPL', 1000, 1000);

INSERT INTO assets (id, customer_id, asset_name, size, usable_size)
VALUES (8, 3, 'AMZN', 1000, 1000);


INSERT INTO assets (id, customer_id, asset_name, size, usable_size)
VALUES (9, 4, 'TRY', 1000, 1000);

INSERT INTO assets (id, customer_id, asset_name, size, usable_size)
VALUES (10, 4, 'MSFT', 1000, 1000);

INSERT INTO assets (id, customer_id, asset_name, size, usable_size)
VALUES (11, 4, 'APPL', 1000, 1000);

INSERT INTO assets (id, customer_id, asset_name, size, usable_size)
VALUES (12, 4, 'AMZN', 1000, 1000);

