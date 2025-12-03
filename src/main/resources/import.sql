INSERT INTO usuario(id_usuario,contrasenha,username,nombres,apellidos,correo,dni,estado,telefono)
values(1,'$2a$12$kLQpDlP2gQBhfhM4lEQioOzqSsBkFbc7SY58WUaOiLUN8Zak3MtTC','coco','Miguel','Mendoza','piero@gmail.com','98987878',true,'989878787' );/*comida*/
INSERT INTO rol(id_rol,user_id,nombre_rol,estado)
VALUES(1,1,'ADMINISTRADOR',true);
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

INSERT INTO moneda(id_moneda,estado,tipo_moneda)
values(1,true,'soles');

INSERT INTO entidad_financiera(id_entidad,nombre,ruc,direccion,telefono,correo,estado)
VALUES(1,'BancoAlfrente','20503644968','Tu casa','989878787','tucorreo@gmail.com',true);

INSERT INTO inmobiliaria(id_inmobiliaria,ubicacion,imagen,area,precio,descripcion,situacion_inmobiliaria,estado)
VALUES(1,'TUHOGAR',123,'233',350000,'Es bonito','Listo para la venta',true);

/*INSERT INTO credito_prestamo(id_moneda,id_inmobiliaria,id_entidad,id_cliente,id_credito,plazo_meses,tipo_gracia,monto_bono,fecha_inicio,fecha_fin,capitalizacion,estado,meses_gracia)
VALUES(1,1,1,1,1,'4','TOTAL',350000,'2025-04-21','2025-11-21','Si',true,4);*/

INSERT INTO tasa_interes(id_tasa,tipo_tasa_interes,tasa_interes,estado)
VALUES(1,'TEA',0.11,true);
DELETE FROM credito_prestamo WHERE id_credito = 1;

INSERT INTO credito_prestamo(
    id_moneda,
    id_inmobiliaria,
    id_entidad,
    id_cliente,
    id_credito,
    plazo_meses,
    tipo_gracia,
    monto_bono,
    fecha_inicio,
    fecha_fin,
    capitalizacion,
    estado,
    meses_gracia
)
VALUES
    (
        1,
        1,
        1,
        1,
        1,
        120,              -- 10 años * 12 meses
        'TOTAL',          -- asegúrate que en el código comparas igual (mayúsculas/minúsculas)
        350000,
        '2025-04-21',
        '2025-11-21',     -- esta fecha no afecta al error, solo es informativa
        'Si',
        true,
        4                 -- 4 meses de gracia dentro de 120 meses de plazo
    );

INSERT INTO entidad_tasa(id_entidad,id_tasa)
VALUES(1,1);