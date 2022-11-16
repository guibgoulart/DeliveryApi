create table delivery (
    id bigint not null auto_increment,
    customer_id bigint not null,
    cost decimal(10,2) not null,
    status varchar(20) not null,
    request_date datetime not null,
    delivered_date datetime,

    recipient_name varchar(60) not null,
    recipient_address varchar(255) not null,

    primary key(id)
);

alter table delivery add constraint fk_delivery_customer
foreign key (customer_id) references customer (id);