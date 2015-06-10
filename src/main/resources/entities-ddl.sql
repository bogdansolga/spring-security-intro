BEGIN TRANSACTION;

DROP TABLE IF EXISTS Product;
DROP TABLE IF EXISTS Role;
DROP TABLE IF EXISTS User;

CREATE TABLE User
(
  id            INT NOT NULL,
  userName      VARCHAR(32) NOT NULL UNIQUE,
  firstName     VARCHAR(32) NOT NULL,
  lastName      VARCHAR(32) NOT NULL,
  password      VARCHAR(60) NOT NULL,
  isActive      BOOLEAN NOT NULL DEFAULT TRUE,

  PRIMARY KEY (id)
);

CREATE TABLE Role
(
  id            SMALLINT NOT NULL,
  userId        INT NOT NULL,
  authority     VARCHAR(15) NOT NULL UNIQUE,

  PRIMARY KEY (id),

  FOREIGN KEY (userId) REFERENCES User(id)
);

CREATE TABLE Product
(
  id            INT NOT NULL,
  name          VARCHAR(30) NOT NULL UNIQUE,
  description   VARCHAR(30) NOT NULL UNIQUE,

  PRIMARY KEY (id),
);

------------------------------------------------------------------------------------------------------------------------

INSERT INTO User (id, userName, firstName, lastName, password, isActive)
VALUES (1, 'tom', 'Thomas', 'Möedl', '$2a$10$dzeuq/7ra19pUOSmoE1FWOjQ5bcZ8biaGJFVvlHY85eYALrWRw.02', TRUE); -- admin
INSERT INTO User (id, userName, firstName, lastName, password, isActive)
VALUES (2, 'markus', 'Markus', 'Buchberger', '$2a$10$AT7tNx5JKvWH.YiQvRj4h.7hEN/Sqvoik0hpAIU0y1YesclE0Q.3W', TRUE); -- manager

INSERT INTO Role (id, userId, authority) VALUES (1, 1, 'ROLE_ADMIN');
INSERT INTO Role (id, userId, authority) VALUES (2, 2, 'ROLE_MANAGER');

INSERT INTO Product (id, name, description) VALUES (1, 'Samsung Galaxy S5', 'The latest Galaxy phone');
INSERT INTO Product (id, name, description) VALUES (2, 'Apple iPhone 6', 'The latest iSomething');

COMMIT;
