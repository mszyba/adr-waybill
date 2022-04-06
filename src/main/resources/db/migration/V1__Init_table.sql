create table if not exists companies
(
    id bigint auto_increment
    primary key,
    vat_number varchar(255) null,
    address varchar(255) null,
    city varchar(255) null,
    company_name varchar(255) null,
    country varchar(255) null,
    email varchar(255) null,
    is_active bit not null,
    phone varchar(255) null,
    postcode varchar(255) null
    );

create table if not exists customers
(
    id bigint auto_increment
    primary key,
    vat_number varchar(255) null,
    address varchar(255) null,
    city varchar(255) null,
    country varchar(255) null,
    customer_name varchar(255) null,
    email varchar(255) null,
    is_active bit not null,
    phone varchar(255) null,
    postcode varchar(255) null
    );

create table if not exists role
(
    id int auto_increment
    primary key,
    description varchar(255) null,
    role varchar(255) null
    );

create table if not exists uns
(
    id bigint auto_increment
    primary key,
    un_adr_tank_code varchar(255) null,
    un_adr_tank_specialp varchar(255) null,
    un_class varchar(255) null,
    un_classification_code varchar(255) null,
    un_hazard_id_no varchar(255) null,
    un_labels varchar(255) null,
    un_limited_quantity_code varchar(255) null,
    un_limited_quantity_vol varchar(255) null,
    un_name_and_description varchar(255) null,
    un_number varchar(255) null,
    un_packaging_instructions varchar(255) null,
    un_packaging_mixedpp varchar(255) null,
    un_packaging_specialpp varchar(255) null,
    un_packing_group varchar(255) null,
    un_portable_tanks_instructions varchar(255) null,
    un_portable_tanks_specialp varchar(255) null,
    un_specialpfcbulk varchar(255) null,
    un_specialpfcloading varchar(255) null,
    un_specialpfcoperation varchar(255) null,
    un_specialpfcpackages varchar(255) null,
    un_transport_category varchar(255) null,
    un_vehicle_for_tank_carriage varchar(255) null
    );

create table if not exists users
(
    id bigint auto_increment
    primary key,
    email varchar(255) not null,
    enabled bit not null,
    first_name varchar(20) null,
    is_super_admin bit not null,
    last_name varchar(255) null,
    password varchar(255) not null,
    company_id bigint null,
    constraint UK_6dotkott2kjsp8vw4d0m25fb7
    unique (email),
    constraint FKin8gn4o1hpiwe6qe4ey7ykwq7
    foreign key (company_id) references companies (id)
    );

create table if not exists users_roles
(
    users_id bigint not null,
    roles_id int not null,
    primary key (users_id, roles_id),
    constraint FK15d410tj6juko0sq9k4km60xq
    foreign key (roles_id) references role (id),
    constraint FKml90kef4w2jy7oxyqv742tsfc
    foreign key (users_id) references users (id)
    );

create table if not exists waybills
(
    id bigint auto_increment
    primary key,
    company_address varchar(255) null,
    company_city varchar(255) null,
    company_country varchar(255) null,
    company_id bigint null,
    company_name varchar(255) null,
    company_postcode varchar(255) null,
    customer_address varchar(255) null,
    customer_city varchar(255) null,
    customer_country varchar(255) null,
    customer_id bigint null,
    customer_name varchar(255) null,
    customer_postcode varchar(255) null,
    description_delivery varchar(255) null,
    driver_document varchar(255) null,
    driver_name varchar(255) null,
    point_class0 int null,
    point_class1 int null,
    point_class2 int null,
    point_class3 int null,
    point_class4 int null,
    points int null,
    transportation varchar(255) null,
    truck_description varchar(255) null,
    truck_number varchar(255) null,
    type_activities varchar(255) null
    );

create table if not exists shipped_items
(
    id bigint auto_increment
    primary key,
    packaging_code varchar(255) null,
    packaging_description varchar(255) null,
    packaging_id bigint null,
    points int null,
    quantity int null,
    un_class varchar(255) null,
    un_id bigint null,
    un_labels varchar(255) null,
    un_name_and_description varchar(255) null,
    un_number varchar(255) null,
    un_packing_group varchar(255) null,
    volume int null,
    waybill_id bigint null,
    constraint FKai4eha5e2o1sp2y2utwh6lku
    foreign key (waybill_id) references waybills (id)
    );

