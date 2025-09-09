CREATE TABLE IF NOT EXISTS roles (
id_rol int NOT NULL AUTO_INCREMENT,
name varchar(45) NOT NULL,
description varchar(50) NOT NULL,
PRIMARY KEY (id_rol)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS users (
user_id int NOT NULL AUTO_INCREMENT,
name varchar(50) NOT NULL,
last_name varchar(50) NOT NULL,
birthday datetime DEFAULT NULL,
address varchar(100) DEFAULT NULL,
phone varchar(20) DEFAULT NULL,
email varchar(45) NOT NULL,
base_salary decimal(10,0) NOT NULL,
dni varchar(45) NOT NULL,
rol_id int NOT NULL,
password varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
PRIMARY KEY (user_id),
UNIQUE KEY email_UNIQUE (email),
UNIQUE KEY dni_UNIQUE (dni),
KEY rol_user_idx (rol_id),
CONSTRAINT rol_user FOREIGN KEY (rol_id) REFERENCES roles (id_rol)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;