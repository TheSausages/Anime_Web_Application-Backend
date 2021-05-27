drop table if exists Users;

create table Users (
    id int not null auto_increment Primary Key,
    name Varchar(200) not null,
    password Varchar(200),
    roles Varchar(200)
);