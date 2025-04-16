
      --     +---------------------+
      --     |   ADDRESS_BOOK      |
      --     |---------------------|
      --     | first_name (PK)     |
      --     | last_name           |
      --     | address             |
      --     | city                |
      --     | state               |
      --     | zip                 |
      --     | phone               |
      --     | email               |
      --     | type                |
      --     +---------------------+
      --                 ^
      --                 |
      --                 |
      -- +---------------------------------+
      -- |           ADDRESS_TYPE          |
      -- |---------------------------------|
      -- | id (PK)                         |
      -- | first_name (FK) -> ADDRESS_BOOK |
      -- | contact_type                    |
      -- +---------------------------------+



create database address_book;

use address_book;

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




UPDATE address_book SET zip='100211' WHERE first_name='Rishav';
-- +----------+---------+---------------+----+-----+------+----------+-----------------+
-- |first_name|last_name|address        |city|state|zip   |phone     |email            |
-- +----------+---------+---------------+----+-----+------+----------+-----------------+
-- |Anmol     |Dhiman   |32, West Avenue|SYD |NSW  |100211|8319832222|test@test.com    |
-- |Rishav    |Thakur   |42, East Avenue|SYD |NSW  |100211|8219764722|test@testmail.com|
-- +----------+---------+---------------+----+-----+------+----------+-----------------+




DELETE FROM address_book WHERE first_name='Anmol';
-- +----------+---------+---------------+----+-----+------+----------+-----------------+
-- |first_name|last_name|address        |city|state|zip   |phone     |email            |
-- +----------+---------+---------------+----+-----+------+----------+-----------------+
-- |Rishav    |Thakur   |42, East Avenue|SYD |NSW  |100210|8219764722|test@testmail.com|
-- +----------+---------+---------------+----+-----+------+----------+-----------------+



SELECT * FROM address_book WHERE city='SYD';
-- +----------+---------+---------------+----+-----+------+----------+-----------------+
-- |first_name|last_name|address        |city|state|zip   |phone     |email            |
-- +----------+---------+---------------+----+-----+------+----------+-----------------+
-- |Rishav    |Thakur   |42, East Avenue|SYD |NSW  |100210|8219764722|test@testmail.com|
-- |Anmol     |Dhiman   |32, West Avenue|SYD |NSW  |100211|8319832222|test@test.com    |
-- +----------+---------+---------------+----+-----+------+----------+-----------------+




SELECT COUNT(city) FROM address_book GROUP BY city;
-- +-----------+
-- |COUNT(city)|
-- +-----------+
-- |2          |
-- +-----------+




SELECT * FROM address_book WHERE city='SYD' ORDER BY first_name DESC;
-- +----------+---------+---------------+----+-----+------+----------+-----------------+
-- |first_name|last_name|address        |city|state|zip   |phone     |email            |
-- +----------+---------+---------------+----+-----+------+----------+-----------------+
-- |Rishav    |Thakur   |42, East Avenue|SYD |NSW  |100210|8219764722|test@testmail.com|
-- |Anmol     |Dhiman   |32, West Avenue|SYD |NSW  |100211|8319832222|test@test.com    |
-- +----------+---------+---------------+----+-----+------+----------+-----------------+


ALTER TABLE address_book ADD COLUMN type VARCHAR(20);

UPDATE address_book SET type='friend' WHERE state='NSW';
-- +----------+---------+---------------+----+-----+------+----------+-----------------+------+
-- |first_name|last_name|address        |city|state|zip   |phone     |email            |type  |
-- +----------+---------+---------------+----+-----+------+----------+-----------------+------+
-- |Rishav    |Thakur   |42, East Avenue|SYD |NSW  |100210|8219764722|test@testmail.com|friend|
-- |Anmol     |Dhiman   |32, West Avenue|SYD |NSW  |100211|8319832222|test@test.com    |friend|
-- +----------+---------+---------------+----+-----+------+----------+-----------------+------+


SELECT type, COUNT(type) FROM address_book GROUP BY type;

-- +------+-----------+
-- |type  |COUNT(type)|
-- +------+-----------+
-- |friend|2          |
-- +------+-----------+

CREATE TABLE address_type(
     id int NOT NULL UNIQUE AUTO_INCREMENT,
     first_name VARCHAR(10),
     contact_type VARCHAR(20),
     primary key (id),
     foreign key (first_name) REFERENCES address_book(first_name)
 );
 
 INSERT INTO address_type VALUES
     (1, 'Anmol', 'Family'),
     (2, 'Anmol', 'Friend'),
     (3, 'Rishav', 'Friend');
 
 -- +--+----------+------------+
 -- |id|first_name|contact_type|
 -- +--+----------+------------+
 -- |1 |Anmol     |Family      |
 -- |2 |Anmol     |Friend      |
 -- |3 |Rishav    |Friend      |
 -- +--+----------+------------+
 
 SELECT * FROM address_book ab LEFT JOIN address_type at ON ab.first_name = at.first_name;
 
 -- +----------+---------+---------------+----+-----+------+----------+-----------------+------+--+----------+------------+
 -- |first_name|last_name|address        |city|state|zip   |phone     |email            |type  |id|first_name|contact_type|
 -- +----------+---------+---------------+----+-----+------+----------+-----------------+------+--+----------+------------+
 -- |Rishav    |Thakur   |42, East Avenue|SYD |NSW  |100210|8219764722|test@testmail.com|friend|3 |Rishav    |Friend      |
 -- |Anmol     |Dhiman   |32, West Avenue|SYD |NSW  |100211|8319832222|test@test.com    |friend|1 |Anmol     |Family      |
 -- |Anmol     |Dhiman   |32, West Avenue|SYD |NSW  |100211|8319832222|test@test.com    |friend|2 |Anmol     |Friend      |
 -- +----------+---------+---------------+----+-----+------+----------+-----------------+------+--+----------+------------+