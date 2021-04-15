create database if not exists mystical;
use mystical;
create table if not exists login_view
(
    id       bigint auto_increment
        primary key,
    username varchar(32)   not null,
    status   int default 0 not null,
    token    varchar(255)  null
);

create table  if not exists  user_info
(
    id           bigint auto_increment
        primary key,
    username     varchar(32)              unique                                     not null,
    password     varchar(40)                                                  not null,
    head_img_url varchar(500) default 'http://p8.qhimg.com/bdr/__85/t012b51fd84316f0502.jpg' null
);
insert into user_info (username, password, head_img_url) values ('ycc','123456','http://p8.qhimg.com/bdr/__85/t012b51fd84316f0502.jpg');
create table  if not exists  user_role
(
    id       bigint auto_increment
        primary key,
    username varchar(32)                   not null,
    rolename varchar(32) default 'GENERAL' not null
);

