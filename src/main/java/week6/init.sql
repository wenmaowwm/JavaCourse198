create table item_info
(
    id         bigint auto_increment,
    item_id    bigint      not null,
    item_name  varchar(20) not null,
    cate_id    int         not null,
    cate_name  varchar(20) not null,
    brand_id   int         not null,
    brand_name varchar(20) not null,
    constraint item_info_id_uindex
        unique (id),
    constraint item_info_item_id_uindex
        unique (item_id)
);

alter table item_info
    add primary key (item_id);

-- auto-generated definition
create table user_info
(
    user_id   bigint        not null,
    user_name varchar(20)   null,
    age       int           null,
    gender    int default 0 null comment '0:woman, 1:man',
    constraint user_info_user_id_uindex
        unique (user_id)
);

alter table user_info
    add primary key (user_id);

-- auto-generated definition
create table order_info
(
    id         int auto_increment,
    order_id   bigint        not null,
    order_time bigint        not null,
    buyer_id   bigint        not null,
    item_id    bigint        not null,
    item_cnt   int default 1 not null,
    constraint order_info_id_uindex
        unique (id),
    constraint order_info_order_id_uindex
        unique (order_id)
);

alter table order_info
    add primary key (order_id);

