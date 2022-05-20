

INSERT INTO product (id, quantity, price,sale_price, description, image, name,is_sale) VALUES (
    1,
    10,
    20.00,
    15.00,
    'A nice pair of headphones',
    'https://i.insider.com/54eb437f6bb3f7697f85da71?width=1000&format=jpeg&auto=webp',
    'Headphones',
    false
),
(
    2,
    5,
    45.00,
    32.00,
    'A nice TeeShirt',
    'https://d3o2e4jr3mxnm3.cloudfront.net/Mens-Jake-Guitar-Vintage-Crusher-Tee_68382_1_lg.png',
    'TeeShirt',
    false
),
(
    3,
    20,
    2.50,
    2.10,
    'A reusable shopping bag',
    'https://images.ctfassets.net/5gvckmvm9289/3BlDoZxSSjqAvv1jBJP7TH/65f9a95484117730ace42abf64e89572/Noissue-x-Creatsy-Tote-Bag-Mockup-Bundle-_4_-2.png',
    'Shopping Bag',
    false
),
(
    4,
    20,
    10.00,
    7.80,
    'A fancy cap for a fancy person',
    'https://d3o2e4jr3mxnm3.cloudfront.net/Rocket-Vintage-Chill-Cap_66374_1_lg.png',
    'Baseball Cap',
    false
),
(
    5,
    3,
    80.00,
    70.00,
    'A nice coat',
    'https://www.pngarts.com/files/3/Women-Jacket-PNG-High-Quality-Image.png',
    'Coat',
    true
);
--Copy Pasting the salt doesn't work
INSERT INTO users (id, email, password, first_name, last_name, salt) VALUES (
    1,
    'testuser@gmail.com',
    '*eÓôéHÏÑ_£XÔ', --    'password',
    'Test',
    'User',
    'NotSoRandomSalt?'
);
