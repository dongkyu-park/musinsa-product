drop table if exists product;

create table product (
    id bigint auto_increment,
    category varchar(20),
    brand varchar(50),
    price integer not null,
    created_at datetime not null,
    updated_at datetime not null,
    is_deleted bit comment '0: 삭제되지 않음, 1: 삭제됨',
    primary key (id)
);
