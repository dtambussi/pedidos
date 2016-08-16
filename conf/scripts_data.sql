-- Iteration 1

INSERT INTO Categoria
(id, name)
VALUES
(1, 'Entradas'),
(2, 'PlatosPrincipales'),
(3, 'Postres'),
(4, 'Bebidas'),
(5, 'Cafeteria');

INSERT INTO EstadoItemDePedido
(id, name)
VALUES
(1, 'Generado'),
(2, 'Pendiente'),
(3, 'Confeccionado'),
(4, 'Entregado'),
(5, 'Cancelado');

INSERT INTO EstadoPedido
(id, name)
VALUES
(1, 'Generado'),
(2, 'Pendiente'),
(3, 'Confeccionado'),
(4, 'Entregado'),
(5, 'Cancelado');

INSERT INTO EstadoSugerencia
(id, name)
VALUES
(1, 'Publicado'),
(2, 'NoPublicado');

INSERT INTO Status
(id, name)
VALUES
(1, 'Inactive'),
(2, 'Active'),
(3, 'Deleted');

INSERT INTO Menu
(status, nombre, fecha_creacion, fecha_ultima_modificacion, vigente)
VALUES
(1, 'Menu Verano 2015', now(), now(), b'1');

INSERT INTO ItemDeMenu
(status, categoria, nombre, descripcion, precio, fecha_creacion, fecha_ultima_modificacion)
VALUES
(1, 2, 'Milanesa Capresse', 'Milanesa con queso, albahaca y tomate', 70, now(), now()),
(1, 2, 'Pure de papas', 'Puré natural de papas', 30, now(), now()),
(1, 2, 'Papas Guarda La Vieja', 'Papas fritas con queso y panceta', 45, now(), now());

INSERT INTO Menu_ItemDeMenu
(id_menu, id_item_de_menu)
VALUES
(1, 1),
(1, 2);

INSERT INTO Rol
(status, nombre, descripcion, fecha_creacion, fecha_ultima_modificacion)
VALUES
(1, 'UsuarioRegistrado', 'Usuario Registrado', now(), now()),
(2, 'UsuarioNoRegistrado', 'Usuario No Registrado', now(), now());

INSERT INTO Usuario
(status, nickname)
VALUES
((SELECT id FROM Status WHERE name = 'Active'), 'demo');

INSERT INTO Usuario_Rol
(id_usuario, id_rol)
VALUES
((SELECT id FROM Usuario WHERE nickname = 'demo'), (SELECT id FROM Rol WHERE nombre = 'UsuarioRegistrado'));

INSERT INTO InfoUsuarioRegistrado
(id_usuario, nombre, apellido, fecha_nacimiento, email, password)
VALUES
((SELECT id FROM Usuario WHERE nickname = 'demo'), 'roger', 'waters', now(), 'demo@gmail.com', 'demo');

-- Iteration 2





















	
	
	
