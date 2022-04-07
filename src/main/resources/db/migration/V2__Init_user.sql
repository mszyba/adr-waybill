INSERT INTO role (id, description, role) VALUES
(1, 'administrator', 'ROLE_ADMIN'),
(2, 'user', 'ROLE_USER');


INSERT INTO users
    (id, email, enabled, first_name, is_super_admin, last_name, password, company_id)
VALUES
    (1, 'a@a.a', true, 'Michał', false, 'Szyba', '{bcrypt}$2a$10$ARin0tmpGEy0kapIqeMcJ./yPJzOfJiLLaCsPHIBK3ZhaGyvnzIty', null),
    (2, 'm.szyba@gmail.com', true, 'Admin', false, 'Szyba', '{bcrypt}$2a$10$ARin0tmpGEy0kapIqeMcJ./yPJzOfJiLLaCsPHIBK3ZhaGyvnzIty', null);

INSERT INTO users_roles (users_id, roles_id) VALUES (1, 2), (2, 1);
