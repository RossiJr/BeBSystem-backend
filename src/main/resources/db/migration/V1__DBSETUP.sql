CREATE TABLE tb_address
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY ,
    street varchar(127),
    number       varchar(10),
    complement   varchar(127),
    neighborhood varchar(127),
    city         varchar(127),
    state        varchar(127),
    country      varchar(127),
    zip_code     varchar(50),
    address_type varchar(20)
);

CREATE TABLE tb_user
(
    id       UUID NOT NULL PRIMARY KEY,
    email varchar(255) NOT NULL UNIQUE,
    password varchar(255) NOT NULL,
    created_at TIMESTAMP
);

CREATE TABLE tb_accommodation
(
    id           UUID NOT NULL PRIMARY KEY,
    name         VARCHAR(127) NOT NULL,
    email        VARCHAR(255),
    phone_number VARCHAR(127),
    status       VARCHAR(50),
    created_at   TIMESTAMP,
    created_by   UUID constraint fk_tb_accommodation_tb_user_id
        references tb_user,
    CONSTRAINT UN_tbaccommodation_name UNIQUE (id, name)
);

CREATE TABLE tb_customer
(
    id           UUID NOT NULL PRIMARY KEY,
    first_name   varchar(127),
    last_name    varchar(127),
    email        varchar(255) NOT NULL,
    phone_number varchar(127),
    address_id   BIGINT constraint fk_tb_customer_tb_address_id
        references tb_address,
    created_at   TIMESTAMP
);

CREATE TABLE tb_reservation
(
    id                  UUID NOT NULL PRIMARY KEY,
    customer_id         UUID constraint fk_tb_reservation_tb_customer_id NOT NULL
        references tb_customer,
    status              varchar(50),
    number_of_guests    INTEGER,
    check_in            TIMESTAMP,
    check_out           TIMESTAMP,
    total_cost          DOUBLE PRECISION,
    created_at          TIMESTAMP,
    payed_at            TIMESTAMP,
    additional_info     TEXT,
    additional_requests TEXT,
    reservation_code    varchar(255),
    reservation_company VARCHAR(255)
);

CREATE TABLE tb_room
(
    id               UUID NOT NULL PRIMARY KEY,
    name             varchar(127) NOT NULL,
    accommodation_id UUID constraint fk_tb_room_tb_accommodation_id
        references tb_accommodation,
    capacity         INTEGER,
    category         VARCHAR(50),
    status           VARCHAR(50),
    created_at timestamp,
    CONSTRAINT UN_tbroom_name UNIQUE (id, name)
);

CREATE TABLE tb_mm_reservation_room
(
    reservation_id UUID NOT NULL constraint fk_tbmmresroo_on_reservation
        references tb_reservation,
    room_id        UUID NOT NULL constraint fk_tbmmresroo_on_room
        references tb_room
);

CREATE TABLE TB_ROLE (
                         ID BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                         NAME VARCHAR(127) NOT NULL UNIQUE,
                         DESCRIPTION VARCHAR(255),
                         CREATED_AT TIMESTAMP
);

CREATE TABLE TB_MM_USER_ROLE (
                                 ID BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                                 USER_ID UUID NOT NULL CONSTRAINT fk_tb_mm_user_role_tb_user_id REFERENCES TB_USER,
                                 ROLE_ID BIGINT NOT NULL CONSTRAINT fk_tb_mm_user_role_tb_role_id REFERENCES TB_ROLE,
                                 ASSIGNED_AT TIMESTAMP
);
