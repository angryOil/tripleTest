create database triple_test;


use triple_test;

create table user
(
    uuid              binary(16) primary key,
    id                varchar(25) unique,
    pw                varchar(25)           not null,
    first_review_flag boolean default false not null
);


create table point
(
    uuid    binary(16) primary key,
    user_id  varchar(25) unique,
    mileage int default 0,
    foreign key (uuid) references user (uuid)
);

create table place
(
    uuid         binary(16) primary key,
    location     varchar(50),
    name         varchar(30),
    special_flag boolean default false,
    unique (location, name)
);




create table review
(
    uuid               binary(16) primary key,
    user_id            binary(16) not null,
    place_id           binary(16) not null,
    content            varchar(500) not null ,
    delete_flag        boolean default false,
    foreign key (user_id) references user (uuid),
    foreign key (place_id) references place (uuid)
);

create table photo
(
    uuid      binary(16) primary key,
    review_id binary(16) not null
);


create table point_log
(
    uuid       binary(16) primary key,
    point_id   binary(16) not null,
    review_id  binary(16) not null,
    place_id   binary(16) not null,
    action     varchar(6) not null,
    point_apply tinyint(1) not null,
    foreign key (point_id) references point (uuid),
    foreign key (review_id) references review (uuid),
    foreign key (place_id) references place (uuid)
);
