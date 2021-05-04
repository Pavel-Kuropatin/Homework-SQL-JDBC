create table users(
    id bigserial not null
        constraint users_pk
            primary key,
    name varchar(100) not null,
    surname varchar(200) not null,
    birth_date date,
    login varchar(200),
    weight real
);

alter table users owner to postgres;

create unique index users_id_uindex
    on users (id);

create index users_name_index
    on users (name);

create index users_name_surname_index
    on users (name, surname);

create unique index users_login_uindex
    on users (login);

create table location(
    id bigserial not null
        constraint location_pk
            primary key,
    country varchar(100) not null,
    city varchar(100),
    latitude double precision not null,
    longitude double precision not null
);

alter table location owner to postgres;

create table dealer(
    id bigserial not null
        constraint dealer_pk
            primary key,
    name varchar(100) not null,
    open_date date not null,
    location_description varchar(200),
    location_id bigint
        constraint dealer_location_id_fk
            references location,
    created timestamp(6) not null,
    changed timestamp(6) not null,
    open_hour integer default 9 not null,
    close_hour integer default 19 not null
);

alter table dealer owner to postgres;

create table cars(
    id bigserial not null
        constraint cars_pk
            primary key,
    owner bigint
        constraint cars_users_id_fk
            references users,
    name varchar(100) not null,
    model varchar(100) not null,
    production_date date not null,
    price integer default 100000 not null,
    dealer_id bigint
        constraint cars_dealer_id_fk
            references dealer
);

alter table cars owner to postgres;

create index dealer_created_index
    on dealer (created);

create index dealer_name_index
    on dealer (name);

create unique index dealer_name_uindex
    on dealer (name);

create index dealer_open_hour_close_hour_index
    on dealer (open_hour, close_hour);

create index location_city_index
    on location (city);

create index location_country_index
    on location (country);

create table address(
    id bigserial not null
        constraint address_pk
            primary key,
    user_id bigint not null
        constraint address_users_id_fk
            references users,
    location_id bigint
        constraint address_location_id_fk
            references location,
    type varchar(50) default 'HOME'::character varying not null
);

alter table address owner to postgres;

create index address_type_index
    on address (type);