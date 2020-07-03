INSERT INTO traveler(id, name, email, document)values(1001, 'TRAVELER 01', 'traveler01@test.com', '111.111.111-11');
INSERT INTO traveler(id, name, email, document)values(1002, 'TRAVELER 02', 'traveler02@test.com', '222.222.222-22');
INSERT INTO traveler(id, name, email, document)values(1003, 'TRAVELER 03', 'traveler03@test.com', '333.333.333-33');


INSERT INTO phone(id, traveler_id, prefix, number)values(101, 1001, 11, '11111-1111');
INSERT INTO phone(id, traveler_id, prefix, number)values(102, 1002, 22, '22222-2222');
INSERT INTO phone(id, traveler_id, prefix, number)values(103, 1003, 33, '33333-3333');
