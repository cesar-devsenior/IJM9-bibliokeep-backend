insert into roles (id, name) values (1, 'ADMIN');
insert into roles (id, name) values (2, 'USER');

insert into users (id, email, password, annual_goal)
values ('e7b1c9d2-3f4a-4b6c-9d8e-0a1b2c3d4e5f', 'cdiaz@test.com', 
        '$2a$12$NpTaGxWlIk.FsuYCVnEms.ZGWYcUdYEMOZqNMY/1sNRGaPXGftccu', 1);

insert into user_roles (user_id, role_id)
values ('e7b1c9d2-3f4a-4b6c-9d8e-0a1b2c3d4e5f', 1);
insert into user_roles (user_id, role_id)
values ('e7b1c9d2-3f4a-4b6c-9d8e-0a1b2c3d4e5f', 2);
