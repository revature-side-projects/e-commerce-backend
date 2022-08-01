INSERT INTO products (product_id, quantity, price, description, image, "name") VALUES (
    1,
    10,
    20.00,
    'A nice pair of headphones',
    'https://i.insider.com/54eb437f6bb3f7697f85da71?width=1000&format=jpeg&auto=webp',
    'Headphones'
),
(
    2,
    5,
    45.00,
    'A nice TeeShirt',
    'https://d3o2e4jr3mxnm3.cloudfront.net/Mens-Jake-Guitar-Vintage-Crusher-Tee_68382_1_lg.png',
    'TeeShirt'
),
(
    3,
    20,
    2.50,
    'A reusable shopping bag',
    'https://images.ctfassets.net/5gvckmvm9289/3BlDoZxSSjqAvv1jBJP7TH/65f9a95484117730ace42abf64e89572/Noissue-x-Creatsy-Tote-Bag-Mockup-Bundle-_4_-2.png',
    'Shopping Bag'
),
(
    4,
    20,
    10.00,
    'A fancy cap for a fancy person',
    'https://revazon-image-bucket.s3.amazonaws.com/cap.png',
    'Baseball Cap'
),
(
    5,
    3,
    80.00,
    'A nice coat',
    'https://www.pngarts.com/files/3/Women-Jacket-PNG-High-Quality-Image.png',
    'Coat'
);


INSERT INTO users (email, password, first_name, last_name, role)
VALUES (
           'admin@gmail.com',
           'auth0|62e0f8d6a6c5ffa1e877de65',
           'testAdmin',
           'User',
           'ADMIN'
       );

INSERT INTO purchases (quantity, order_placed, product_id, user_id)
VALUES (
           1,
           CURRENT_TIMESTAMP,
           1,
           1
       );


INSERT INTO addresses (address_id, street, secondary, city, zip, state)
VALUES (
           1,
           '844 california street',
           '',
           'Los Angeles',
           '39999',
           'CA'
       );

INSERT INTO users_addresses (address_id, user_id)
VALUES (
           1,
           1
       );

INSERT INTO reviews (review_id, posted, review, stars, title, updated, product_id, user_id)
VALUES (
           1,
        '2022-07-31 22:38:11.28',
           'this pair of headphones is fire',
           5,
           'great stuff man',
           '2022-07-31 22:38:11.28',
           1,
           1
       );

INSERT INTO reviews (review_id,posted, review, stars, title,updated, product_id, user_id)
VALUES (
           2,
           '2022-07-31 22:38:11.28',
           'A nice TeeShirt',
           4,
           'great stuff man',
           '2022-07-31 22:38:11.28',
           2,
           1
       );
