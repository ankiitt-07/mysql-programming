-- +----------------+         +------------------+
-- |   DEPARTMENT   |         |     CONTACT      |
-- |----------------|         |------------------|
-- | dept_id (PK)   |         | contact_id (PK)  |
-- | dept_name      |         | phone            |
-- +----------------+         | email            |
--          ^                 | address (DEFAULT)|
--          |                 | employee_id (FK) |
--          |                 +------------------+
--          |                          ^
--          |                          |
--          |                 +------------------+
--          |                 |   EMPLOYEE       |
--          |                 |------------------|
--          +----------------<| id (PK)          |
--                            | name             |
--                            | start_date       |
--                            | gender           |
--                            | dept_id (FK)     |
--                            +------------------+
--                                      |
--                                      v
--                            +------------------+
--                            |     PAYROLL      |
--                            |------------------|
--                            | payroll_id (PK)  |
--                            | basic_pay        |
--                            | deductions       |
--                            | taxable_pay      |
--                            | income_tax       |
--                            | net_pay          |
--                            | salary           |
--                            | employee_id (FK) |
--                            +------------------+



CREATE DATABASE payroll_service;
USE payroll_service;



CREATE TABLE employee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    gender VARCHAR(10),
    start_date DATE,
    dept_id INT,
    FOREIGN KEY (dept_id) REFERENCES department(dept_id)
);



CREATE TABLE payroll (
    payroll_id INT AUTO_INCREMENT PRIMARY KEY,
    basic_pay DECIMAL(10,2),
    deductions DECIMAL(10,2),
    taxable_pay DECIMAL(10,2),
    income_tax DECIMAL(10,2),
    net_pay DECIMAL(10,2),
    salary DECIMAL(10,2),
    employee_id INT,
    FOREIGN KEY (employee_id) REFERENCES employee(id)
);



SELECT 
    e.id, e.name, e.gender, e.start_date,
    p.salary, p.basic_pay, p.net_pay
FROM employee e
JOIN payroll p ON e.id = p.employee_id;



SELECT p.salary FROM employee e
JOIN payroll p ON e.id = p.employee_id
WHERE e.name = 'Anmol';

SELECT * FROM employee
WHERE start_date BETWEEN '2018-01-01' AND CURDATE();



UPDATE employee SET gender = 'M' WHERE name IN ('Anmol', 'Rishav');



SELECT e.gender,
       SUM(p.salary) AS total_salary,
       AVG(p.salary) AS avg_salary,
       MIN(p.salary) AS min_salary,
       MAX(p.salary) AS max_salary,
       COUNT(*) AS employee_count
FROM employee e
JOIN payroll p ON e.id = p.employee_id
GROUP BY e.gender;



CREATE TABLE department (
    dept_id INT AUTO_INCREMENT PRIMARY KEY,
    dept_name VARCHAR(100) NOT NULL
);

CREATE TABLE contact (
    contact_id INT AUTO_INCREMENT PRIMARY KEY,
    phone VARCHAR(15),
    email VARCHAR(100),
    address VARCHAR(255) DEFAULT 'N/A',
    employee_id INT,
    FOREIGN KEY (employee_id) REFERENCES employee(id)
);



INSERT INTO department (dept_name) VALUES ('Sales and Marketing');

INSERT INTO employee (name, gender, start_date, dept_id)
VALUES ('Terissa', 'F', '2022-05-01', 1);

INSERT INTO contact (phone, email, address, employee_id)
VALUES ('1234567890', 'terissa@example.com', '123 Elm St', LAST_INSERT_ID());

INSERT INTO payroll (basic_pay, deductions, taxable_pay, income_tax, net_pay, salary, employee_id)
VALUES (80000, 5000, 75000, 10000, 65000, 85000, LAST_INSERT_ID());