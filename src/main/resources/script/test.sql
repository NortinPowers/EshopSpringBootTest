create table order_product
(
    order_id   varchar(50) not null
        constraint order_product_orders_id_fk
            references orders
            on update cascade on delete cascade,
    product_id bigint      not null
        constraint order_product_products_id_fk
            references products
            on update cascade on delete cascade,
    constraint order_product_pk
        primary key (order_id, product_id)
);

alter table order_product
    owner to postgres;


create table orders
(
    id      bigserial
        constraint orders_pk
            primary key,
    name    varchar(50) not null,
    date    date        not null,
    user_id integer     not null
        constraint orders_users_id_fk
            references users
            on update cascade on delete cascade
);

alter table orders
    owner to postgres;

