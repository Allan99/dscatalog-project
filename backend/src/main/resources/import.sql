-- ROLES
INSERT INTO tb_roles (id, authority) VALUES (1, 'ROLE_ADMIN');
INSERT INTO tb_roles (id, authority) VALUES (2, 'ROLE_CLIENT');

-- USERS
INSERT INTO tb_users (id, first_name, last_name, email, password) VALUES (1, 'Allan', 'Pereira', 'allan@email.com', '$2a$10$abcdefghijklmnopqrstuv'), (2, 'Maria', 'Silva', 'maria@email.com', '$2a$10$abcdefghijklmnopqrstuv'), (3, 'João', 'Souza', 'joao@email.com', '$2a$10$abcdefghijklmnopqrstuv');

-- USERS_ROLES
INSERT INTO tb_users_roles (user_id, role_id) VALUES (1, 1), (1, 2), (2, 2), (3, 2);

-- CATEGORIES
INSERT INTO tb_categories (id, name) VALUES (1, 'Eletrônicos'), (2, 'Livros'), (3, 'Games'), (4, 'Informática');

-- PRODUCTS
INSERT INTO tb_products (id, name, description, price, img_url) VALUES (1, 'Notebook Dell Inspiron', 'Notebook Dell Inspiron 15, Intel i5, 16GB RAM', 3999.90, 'https://images.example.com/notebook.jpg'), (2, 'Mouse Logitech G203', 'Mouse Gamer RGB Logitech G203', 149.90, 'https://images.example.com/mouse.jpg'), (3, 'Teclado Mecânico Redragon', 'Teclado Mecânico Switch Brown', 299.90, 'https://images.example.com/teclado.jpg'), (4, 'The Pragmatic Programmer', 'Livro sobre boas práticas de desenvolvimento', 99.90, 'https://images.example.com/pragmatic.jpg'), (5, 'PlayStation 5', 'Console Sony PlayStation 5', 4499.90, 'https://images.example.com/ps5.jpg');

-- PRODUCTS_CATEGORIES
INSERT INTO tb_products_categories (product_id, category_id) VALUES (1, 1), (1, 4), (2, 1), (2, 4), (3, 1), (3, 4), (4, 2), (5, 3);