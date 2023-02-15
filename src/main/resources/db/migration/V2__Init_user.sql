INSERT INTO role (description, role) VALUES ('administrator', 'ROLE_ADMIN');
INSERT INTO role (description, role) VALUES ('user', 'ROLE_USER');

insert into companies (vat_number, address, city, company_name, country, email, is_active, phone, postcode) values ('23-596-6612', '8355 Declaration Street', 'Gaoqiao', 'Photolist', 'China', 'dcamings0@samsung.com', true, '+86-356-870-0641', null);
insert into companies (vat_number, address, city, company_name, country, email, is_active, phone, postcode) values ('44-769-2881', '36114 Lillian Pass', 'Arras', 'Youopia', 'France', 'dcleynaert1@goodreads.com', true, '+33-461-465-9369', '62070 CEDEX 9');
insert into companies (vat_number, address, city, company_name, country, email, is_active, phone, postcode) values ('00-227-4744', '5346 3rd Hill', 'Alabug', 'Buzzster', 'Philippines', 'dgergus2@tripadvisor.com', true, '+63-246-604-6145', '4507');

INSERT INTO users (email, enabled, first_name, is_super_admin, last_name, password, company_id) VALUES ('a@a.a', true, 'Michał', false, 'Szyba', '{bcrypt}$2a$10$ARin0tmpGEy0kapIqeMcJ./yPJzOfJiLLaCsPHIBK3ZhaGyvnzIty', 1);
INSERT INTO users (email, enabled, first_name, is_super_admin, last_name, password, company_id) VALUES ('b@b.b', true, 'Michał', false, 'Szyba', '{bcrypt}$2a$10$ARin0tmpGEy0kapIqeMcJ./yPJzOfJiLLaCsPHIBK3ZhaGyvnzIty', 2);
INSERT INTO users (email, enabled, first_name, is_super_admin, last_name, password, company_id) VALUES ('m.szyba@gmail.com', true, 'Admin', false, 'Szyba', '{bcrypt}$2a$10$ARin0tmpGEy0kapIqeMcJ./yPJzOfJiLLaCsPHIBK3ZhaGyvnzIty', 3);

INSERT INTO users_roles (users_id, roles_id) VALUES (2, 1);
INSERT INTO users_roles (users_id, roles_id) VALUES (3, 1);
INSERT INTO users_roles (users_id, roles_id) VALUES (1, 2);