CREATE TABLE product (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255),
  description VARCHAR(255),
  quantity INT NOT NULL
);