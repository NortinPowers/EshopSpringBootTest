create table product_type
(
    id   bigint generated by default as identity
        constraint product_type_pk
            primary key,
    type varchar(30) not null
);

alter table product_type
    owner to postgres;

insert into product_type (type)
values ('phone'),
       ('tv');

create table products
(
    id              bigserial
        constraint products_pk
            primary key,
    name            varchar(50) not null,
    price           numeric     not null,
    product_type_id bigint      not null
        constraint products_product_type_id_fk
            references product_type
            on update cascade on delete cascade,
    info            varchar(150)
);

alter table products
    owner to postgres;

insert into products (name, price, product_type_id, info)
values ('Apple iphone 14', 999.99, 1, 'stylish ios phone'),
       ('Samsung S22', 799.99, 1, 'android  phone'),
       ('Xiaomi 13', 650, 1, 'android phone with miua'),
       ('Samsung S23', 950, 1, 'android flagman phone'),
       ('Xiaomi 13pro', 850, 1, 'global android phone with miua'),
       ('LG 55NAN', 459.99, 2, 'lg TV 55d'),
       ('LG 49S', 320, 2, 'lg TV 49d'),
       ('Sony KD-55', 540, 2, 'sony TV 55d');

create table users
(
    id       bigserial
        constraint users_pk
            primary key,
    login    varchar(30) not null,
    password varchar(30) not null,
    name     varchar(30) not null,
    surname  varchar(30) not null,
    email    varchar(50) not null
        constraint users_pk2
            unique,
    birthday date        not null
);

alter table users
    owner to postgres;

create table public.carts
(
    id         bigserial
        constraint carts_pk
            primary key,
    user_id    bigint            not null
        constraint carts_users_id_fk
            references public.users
            on update cascade on delete cascade,
    product_id bigint            not null
        constraint carts_products_id_fk
            references public.products
            on update cascade on delete cascade,
    cart       boolean,
    favorite   boolean,
    count      integer default 1 not null
);

alter table public.carts
    owner to postgres;

create table public.orders
(
    id      varchar(50) not null
        constraint orders_pk
            primary key,
    date    date        not null,
    user_id bigint      not null
        constraint orders_users_id_fk
            references public.users
            on update cascade on delete cascade
);

alter table public.orders
    owner to postgres;

create table public.order_configurations
(
    id         bigserial
        constraint order_configurations_pk
            primary key,
    order_id   varchar(50) not null
        constraint order_configurations_orders_id_fk
            references public.orders
            on update cascade on delete cascade,
    product_id bigint      not null
        constraint order_configurations_products_id_fk
            references public.products
            on update cascade on delete cascade
);

alter table public.order_configurations
    owner to postgres;

create table images
(
    id              bigint generated by default as identity
        constraint images_pk
            primary key,
    product_type_id bigint      not null
        constraint images_product_type_id_fk
            references product_type
            on update cascade on delete cascade,
    product_id      bigint      not null
        constraint images_products_id_fk
            references products
            on update cascade on delete cascade,
    image_path      varchar(60) not null
);

alter table images
    owner to postgres;

insert into images (product_type_id, product_id, image_path)
values (1, 1, '/img/phone/Apple iphone 14.jpg'),
       (1, 2, '/img/phone/Samsung S22.jpg'),
       (1, 3, '/img/phone/Xiaomi 13.jpg'),
       (1, 4, '/img/phone/Samsung S23.jpg'),
       (1, 5, '/img/phone/Xiaomi 13pro.jpg'),
       (1, 6, '/img/tv/LG 55NAN.jpg'),
       (1, 7, '/img/tv/LG 49S.jpg'),
       (1, 8, '/img/tv/Sony KD-55.jpg');