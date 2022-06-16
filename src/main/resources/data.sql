INSERT INTO product (id, quantity, price, description, image, name, category, is_discontinued, is_featured) VALUES (
    1000,
    10,
    20.00,
    'A nice pair of headphones',
    'https://i.insider.com/54eb437f6bb3f7697f85da71?width=1000&format=jpeg&auto=webp',
    'Headphones',
    'electronics',
    false,
    false
),
(
    1001,
    5,
    45.00,
    'A nice TeeShirt',
    'https://d3o2e4jr3mxnm3.cloudfront.net/Mens-Jake-Guitar-Vintage-Crusher-Tee_68382_1_lg.png',
    'TeeShirt',
    'clothing',
    false,
    false
),
(
    1002,
    20,
    2.50,
    'A reusable shopping bag',
    'https://images.ctfassets.net/5gvckmvm9289/3BlDoZxSSjqAvv1jBJP7TH/65f9a95484117730ace42abf64e89572/Noissue-x-Creatsy-Tote-Bag-Mockup-Bundle-_4_-2.png',
    'Shopping Bag',
    'accessories',
    false,
    false
),
(
    1003,
    20,
    10.00,
    'A fancy cap for a fancy person',
    'https://d3o2e4jr3mxnm3.cloudfront.net/Rocket-Vintage-Chill-Cap_66374_1_lg.png',
    'Baseball Cap',
    'clothing',
    false,
    false
),
(
    1004,
    3,
    80.00,
    'A nice coat',
    'https://www.pngarts.com/files/3/Women-Jacket-PNG-High-Quality-Image.png',
    'Coat',
    'clothing',
    false,
    true
);

INSERT INTO users (id, email, password, first_name, last_name, admin) VALUES (
    1000,
    'testuser@gmail.com',
    'password',
    'Test',
    'User',
     false),(
       1001,
         'admin@gmail.com',
         'password',
         'Admin',
         'testAdmin',
          true
);
