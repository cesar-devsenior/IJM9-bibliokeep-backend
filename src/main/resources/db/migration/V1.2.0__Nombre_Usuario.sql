ALTER TABLE users ADD COLUMN name varchar(255) not null default 'Sin Nombre';

UPDATE users 
SET name = 'Administrador del sistema'
WHERE id = '468a2f60-f343-48d6-bf29-dc55d9c7c28a';
