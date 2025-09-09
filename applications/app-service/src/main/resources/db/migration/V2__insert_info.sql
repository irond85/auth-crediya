INSERT INTO roles
(name, description)
VALUES('ADMIN', 'Administrador');
INSERT INTO roles
(name, description)
VALUES('ADVISOR', 'Asesor');
INSERT INTO roles
(name, description)
VALUES('CUSTOMER', 'Cliente');

INSERT INTO users
(name, last_name, birthday, address, phone, email, base_salary, dni, rol_id, password)
VALUES('Sheshin', 'Dev', '2025-12-31 13:45:00', 'Street', '+57132123', 'admin@crediya.co', 600000, '100', 1, '$2a$10$JViBeEDxfKhS1l4aZGrjseod.HVwMz86zo8Jk63QCVH.3ELauTVHm');
INSERT INTO users
(name, last_name, birthday, address, phone, email, base_salary, dni, rol_id, password)
VALUES('Asesor', 'LastN2', '2025-03-03 13:45:00', 'Street 222', '+57132123', 'asesor1@crediya.co', 600000, '102', 2, '$2a$10$JViBeEDxfKhS1l4aZGrjseod.HVwMz86zo8Jk63QCVH.3ELauTVHm');
INSERT INTO users
(name, last_name, birthday, address, phone, email, base_salary, dni, rol_id, password)
VALUES('camilito', 'LastN2', '2025-03-03 13:45:00', 'Street 222', '+57132123', 'cliente1@email.co', 600000, '111', 3, '$2a$10$1mVKczJST8XNAtItUD9quuU0Ns7gpIm4AwA0ACBI08SyPSMW0pQ6q');
INSERT INTO users
(name, last_name, birthday, address, phone, email, base_salary, dni, rol_id, password)
VALUES('miguelito', 'LastN2', '2025-03-03 13:45:00', 'Street 222', '+57132123', 'cliente2@email.co', 600000, '112', 3, '$2a$10$CIelXVGgGONcJtYIQZEza.oMj2SInlZlPBy/b2591P5eufUfQ98IW');