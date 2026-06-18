-- ROLES
INSERT INTO tb_roles (authority) VALUES ('ROLE_ADMIN');
INSERT INTO tb_roles (authority) VALUES ('ROLE_CLIENT');

-- USERS
INSERT INTO tb_users (first_name, last_name, email, password) VALUES ('Allan', 'Pereira', 'allan@email.com', '$2a$10$abcdefghijklmnopqrstuv'), ('Maria', 'Silva', 'maria@email.com', '$2a$10$abcdefghijklmnopqrstuv'), ('João', 'Souza', 'joao@email.com', '$2a$10$abcdefghijklmnopqrstuv');

-- USERS_ROLES
INSERT INTO tb_users_roles (user_id, role_id) VALUES (1, 1), (1, 2), (2, 2), (3, 2);

-- CATEGORIES
INSERT INTO tb_categories (name) VALUES ('Eletrônicos'), ('Livros'), ('Games'), ('Informática');

-- PRODUCTS
INSERT INTO tb_products (name, description, price, img_url) VALUES ('Notebook Dell Inspiron', 'Notebook Dell Inspiron 15, Intel i5, 16GB RAM', 3999.90, 'https://images.example.com/notebook.jpg'), ('Mouse Logitech G203', 'Mouse Gamer RGB Logitech G203', 149.90, 'https://images.example.com/mouse.jpg'), ('Teclado Mecânico Redragon', 'Teclado Mecânico Switch Brown', 299.90, 'https://images.example.com/teclado.jpg'), ('The Pragmatic Programmer', 'Livro sobre boas práticas de desenvolvimento', 99.90, 'https://images.example.com/pragmatic.jpg'), ('PlayStation 5', 'Console Sony PlayStation 5', 4499.90, 'https://images.example.com/ps5.jpg');

-- PRODUCTS_CATEGORIES
INSERT INTO tb_products_categories (product_id, category_id) VALUES (1, 1), (1, 4), (2, 1), (2, 4), (3, 1), (3, 4), (4, 2), (5, 3);