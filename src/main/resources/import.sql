
INSERT INTO usuario(id_usuario,contrasenha,username,nombres,apellidos,correo,dni,estado,telefono)
values(1,'$2a$12$kLQpDlP2gQBhfhM4lEQioOzqSsBkFbc7SY58WUaOiLUN8Zak3MtTC','coco','Miguel','Mendoza','piero@gmail.com','98987878',true,'989878787' );/*comida*/
INSERT INTO rol(id_rol,user_id,nombre_rol,estado)
VALUES(1,1,'ADMIN',true);
INSERT INTO cliente (id_cliente,nombres,apellidos,correo,telefono,dni,es_activo,aplica_bono)
VALUES (1,'Luis Alberto', 'Ramírez Soto', 'luis.ramirez@example.com', '987654321', '74851236', true, false),
       (2,'María Fernanda', 'Lopez Vargas', 'maria.lopez@example.com', '912345678', '72145896', true, true),
       (3,'Jorge Andrés', 'Quispe Huamán', 'jorge.quispe@example.com', '965874123', '70321458', false, false),
       (4,'Ana Sofía', 'Rojas Salazar', 'ana.rojas@example.com', '998745632', '75213694', true, true),
       (5,'Carlos Daniel', 'Mendoza Ruiz', 'carlos.mendoza@example.com', '984123765', '74123658', true, false),
       (6,'Valeria Camila', 'Paredes Torres', 'valeria.paredes@example.com', '923658741', '71659832', false, false),
       (7,'Diego Armando', 'Gómez Tapia', 'diego.gomez@example.com', '934812765', '70549321', true, true),
       (8,'Isabella Nicole', 'Cárdenas León', 'isabella.cardenas@example.com', '978451236', '73201945', true, false),
       (9,'Ricardo Jesús', 'Salinas Ponce', 'ricardo.salinas@example.com', '987123654', '75439821', false, true),
       (10,'Fiorella Andrea', 'Castro Morales', 'fiorella.castro@example.com', '915478236', '72654198', true, true);

