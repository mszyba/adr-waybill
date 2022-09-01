
-- auto-generated definition
drop table if exists customers_companies;

create table if not exists customers_companies
(
    company_id int not null,
    customer_id int not null,
    constraint customers_companies primary key (company_id, customer_id),
    constraint FK_company
        foreign key (company_id) references companies (id),
    constraint FK_customer
        foreign key (customer_id) references customers (id)
);

insert into customers_companies(company_id, customer_id) VALUES (1, 1);
insert into customers_companies(company_id, customer_id) VALUES (1, 2);
insert into customers_companies(company_id, customer_id) VALUES (1, 3);
insert into customers_companies(company_id, customer_id) VALUES (1, 4);
insert into customers_companies(company_id, customer_id) VALUES (1, 5);
insert into customers_companies(company_id, customer_id) VALUES (2, 6);
insert into customers_companies(company_id, customer_id) VALUES (2, 7);
insert into customers_companies(company_id, customer_id) VALUES (2, 8);
insert into customers_companies(company_id, customer_id) VALUES (2, 9);
insert into customers_companies(company_id, customer_id) VALUES (2, 10);