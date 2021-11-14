create table sharding_db.order_info
(
    id         int auto_increment,
    order_id   bigint        not null,
    buyer_id   bigint        not null,
    item_id    bigint        not null,
    item_cnt   int default 1 not null,
    constraint order_info_id_uindex
        unique (id),
    constraint order_info_order_id_uindex
        unique (order_id)
);

insert 时有bug：
insert into order_info(order_id,buyer_id,item_id) values(1,1,2),(1,2,2),(1,3,2),(1,4,2),(1,5,2);
ERROR 1064 (42000): You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near '.shardingsphere.sharding.rewrite.token.pojo.TableToken@632921af(order_id,buyer_i' at line 1

跟版本有关，最新版本解压时，lib中的jar包总因为长度问题无法完全解压
insert into sharding_db.order_info(order_id, buyer_id, item_id) values(1, 1, 2),(1, 2, 2),(1, 3, 2),(1, 4, 2),(1, 5, 2);
insert into sharding_db.order_info(order_id, buyer_id, item_id) values(0, 1, 2),(1, 1, 2),(2, 1, 2),(3, 1, 2),(4, 1, 2),(5, 1, 2),(6, 1, 2),(7, 1, 2);
insert into sharding_db.order_info(order_id, buyer_id, item_id) values(8, 1, 2),(9, 1, 2),(10, 1, 2),(11, 1, 2),(12, 1, 2),(13, 1, 2),(14, 1, 2),(15, 1, 2);

select * from order_info;