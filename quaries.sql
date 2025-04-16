-- # UC1 Ability to create a Address Book Service DB
create database address_book;

use address_book;

-- # UC2 - create a Table with first and last names, address, city, state, zip, phone number and email as its attributes

CREATE TABLE address_book (
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    address VARCHAR(100),
    city VARCHAR(50),
    state VARCHAR(20),
    zip VARCHAR(6),
    phone VARCHAR(10),
    email VARCHAR(50)
);


-- # UC3 - insert new Contacts to Address Book

INSERT INTO address_book VALUES
    ('Anmol',
      'Dhiman',
      '32, West Avenue',
      'SYD',
      'NSW',
      '100211',
      '8319832222',
      'test@test.com'),
     ('Rishav',
      'Thakur',
      '42, East Avenue',
      'SYD',
      'NSW',
      '100210',
      '8219764722',
      'test@testmail.com');

select * from address_book;    

-- +----------+---------+---------------+----+-----+------+----------+-----------------+
-- |first_name|last_name|address        |city|state|zip   |phone     |email            |
-- +----------+---------+---------------+----+-----+------+----------+-----------------+
-- |Anmol     |Dhiman   |32, West Avenue|SYD |NSW  |100211|8319832222|test@test.com    |
-- |Rishav    |Thakur   |42, East Avenue|SYD |NSW  |100210|8219764722|test@testmail.com|
-- +----------+---------+---------------+----+-----+------+----------+-----------------+


-- # UC4 - edit existing contact person using their name

UPDATE address_book SET zip='100211' WHERE first_name='Rishav';
-- +----------+---------+---------------+----+-----+------+----------+-----------------+
-- |first_name|last_name|address        |city|state|zip   |phone     |email            |
-- +----------+---------+---------------+----+-----+------+----------+-----------------+
-- |Anmol     |Dhiman   |32, West Avenue|SYD |NSW  |100211|8319832222|test@test.com    |
-- |Rishav    |Thakur   |42, East Avenue|SYD |NSW  |100211|8219764722|test@testmail.com|
-- +----------+---------+---------------+----+-----+------+----------+-----------------+


-- # UC5 - delete a person using person's name

DELETE FROM address_book WHERE first_name='Anmol';
-- +----------+---------+---------------+----+-----+------+----------+-----------------+
-- |first_name|last_name|address        |city|state|zip   |phone     |email            |
-- +----------+---------+---------------+----+-----+------+----------+-----------------+
-- |Rishav    |Thakur   |42, East Avenue|SYD |NSW  |100210|8219764722|test@testmail.com|
-- +----------+---------+---------------+----+-----+------+----------+-----------------+


-- # UC6 - Retrieve Person belonging to a City or State

SELECT * FROM address_book WHERE city='SYD';
-- +----------+---------+---------------+----+-----+------+----------+-----------------+
-- |first_name|last_name|address        |city|state|zip   |phone     |email            |
-- +----------+---------+---------------+----+-----+------+----------+-----------------+
-- |Rishav    |Thakur   |42, East Avenue|SYD |NSW  |100210|8219764722|test@testmail.com|
-- |Anmol     |Dhiman   |32, West Avenue|SYD |NSW  |100211|8319832222|test@test.com    |
-- +----------+---------+---------------+----+-----+------+----------+-----------------+


-- # UC7 - understand the size of address book by City and State

SELECT COUNT(city) FROM address_book GROUP BY city;
-- +-----------+
-- |COUNT(city)|
-- +-----------+
-- |2          |
-- +-----------+


-- # UC8 - retrieve entries sorted alphabetically by Person’s name for a given city

SELECT * FROM address_book WHERE city='SYD' ORDER BY first_name DESC;
-- +----------+---------+---------------+----+-----+------+----------+-----------------+
-- |first_name|last_name|address        |city|state|zip   |phone     |email            |
-- +----------+---------+---------------+----+-----+------+----------+-----------------+
-- |Rishav    |Thakur   |42, East Avenue|SYD |NSW  |100210|8219764722|test@testmail.com|
-- |Anmol     |Dhiman   |32, West Avenue|SYD |NSW  |100211|8319832222|test@test.com    |
-- +----------+---------+---------------+----+-----+------+----------+-----------------+


-- # UC9 - identify each Address Book with name and Type.

ALTER TABLE address_book ADD COLUMN type VARCHAR(20);

UPDATE address_book SET type='friend' WHERE state='NSW';
-- +----------+---------+---------------+----+-----+------+----------+-----------------+------+
-- |first_name|last_name|address        |city|state|zip   |phone     |email            |type  |
-- +----------+---------+---------------+----+-----+------+----------+-----------------+------+
-- |Rishav    |Thakur   |42, East Avenue|SYD |NSW  |100210|8219764722|test@testmail.com|friend|
-- |Anmol     |Dhiman   |32, West Avenue|SYD |NSW  |100211|8319832222|test@test.com    |friend|
-- +----------+---------+---------------+----+-----+------+----------+-----------------+------+


-- # UC10 - get number of contact persons

SELECT type, COUNT(type) FROM address_book GROUP BY type;

-- +------+-----------+
-- |type  |COUNT(type)|
-- +------+-----------+
-- |friend|2          |
-- +------+-----------+



-- # UC11 - add person to both Friend and Family

INSERT INTO address_type VALUES
    (1, 'Anmol', 'Family'),
    (2, 'Anmol', 'Friend'),
    (3, 'Rishav', 'Friend');

