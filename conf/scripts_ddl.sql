
CREATE TABLE Status (
    id INT NOT NULL ,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Categoria (
    id INT NOT NULL ,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE EstadoItemDePedido (
    id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE EstadoPedido (
    id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE EstadoSugerencia (
    id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE ItemDeMenu (
     id BIGINT AUTO_INCREMENT NOT NULL,
     status INT NOT NULL,
     categoria INT NOT NULL,
     nombre VARCHAR(100) NOT NULL,
     descripcion VARCHAR(250),
     precio DECIMAL(10,2) NOT NULL,
     fecha_creacion DATETIME NOT NULL,
     fecha_ultima_modificacion DATETIME NOT NULL,
     PRIMARY KEY (id),
     FOREIGN KEY (status) REFERENCES Status(id),
     UNIQUE(nombre)
);
	
CREATE TABLE Menu (
	id BIGINT AUTO_INCREMENT NOT NULL,
	status INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    fecha_ultima_modificacion DATETIME NOT NULL,
    vigente bit DEFAULT 0,
    PRIMARY KEY (id),
    FOREIGN KEY (status) REFERENCES Status(id),
    UNIQUE(nombre)
);

CREATE TABLE Menu_ItemDeMenu (
	id_menu BIGINT NOT NULL,
	id_item_de_menu BIGINT NOT NULL,
	PRIMARY KEY (id_menu, id_item_de_menu),
	FOREIGN KEY (id_menu) REFERENCES Menu(id),
	FOREIGN KEY (id_item_de_menu) REFERENCES ItemDeMenu(id)
);	
	

CREATE TABLE Pedido (
	id BIGINT AUTO_INCREMENT NOT NULL,
	status INT NOT NULL,
	estado INT NOT NULL,
	id_menu BIGINT NOT NULL,
	fecha_creacion DATETIME NOT NULL,
    fecha_ultima_modificacion DATETIME NOT NULL,
    comentario VARCHAR(250) DEFAULT '',
    abonado bit DEFAULT 0,
    id_usuario BIGINT NOT NULL,
    id_usuario_ultima_modificacion BIGINT,
    mesa VARCHAR(50),
    PRIMARY KEY (id),
    FOREIGN KEY (status) REFERENCES Status(id),
    FOREIGN KEY (estado) REFERENCES EstadoPedido(id),
    FOREIGN KEY (id_menu) REFERENCES Menu(id)
);	
	
CREATE TABLE ItemDePedido (
    id BIGINT AUTO_INCREMENT NOT NULL,
    id_pedido BIGINT NOT NULL,
    id_item_de_menu BIGINT NOT NULL,
    status INT NOT NULL,
    estado INT NOT NULL,
    cantidad INT NOT NULL,
    comentario VARCHAR(250) DEFAULT '',
    abonado bit DEFAULT 0,
    fecha_ultima_modificacion DATETIME NOT NULL,
    id_usuario_ultima_modificacion BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (id_pedido) REFERENCES Pedido(id),
    FOREIGN KEY (id_item_de_menu) REFERENCES ItemDeMenu(id),
    FOREIGN KEY (status) REFERENCES Status(id),
    FOREIGN KEY (estado) REFERENCES EstadoItemDePedido(id)
);

CREATE TABLE Rol (
	id BIGINT AUTO_INCREMENT NOT NULL,
	status INT NOT NULL,
	nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(250),
    fecha_creacion DATETIME NOT NULL,
    fecha_ultima_modificacion DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (status) REFERENCES Status(id),
    UNIQUE(nombre)
);

CREATE TABLE Usuario (
	id BIGINT AUTO_INCREMENT NOT NULL,
	status INT NOT NULL,
	nickname VARCHAR(100) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (status) REFERENCES Status(id)
);

CREATE TABLE InfoUsuarioRegistrado (
	id_usuario BIGINT NOT NULL,
	nombre VARCHAR(100) NOT NULL,
	apellido VARCHAR(100) NOT NULL,
	fecha_nacimiento DATETIME NOT NULL,
	email VARCHAR(100) NOT NULL,
	password VARCHAR(100) NOT NULL,
	PRIMARY KEY (id_usuario),
	FOREIGN KEY (id_usuario) REFERENCES Usuario(id)
);

CREATE TABLE Usuario_Rol (
	id_usuario BIGINT NOT NULL,
	id_rol BIGINT NOT NULL,
	PRIMARY KEY (id_usuario, id_rol),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id),
    FOREIGN KEY (id_rol) REFERENCES Rol(id)
);

CREATE TABLE Sugerencia (
	id BIGINT AUTO_INCREMENT NOT NULL,
	status INT NOT NULL,
	estado INT NOT NULL,
	nombre VARCHAR(100) NOT NULL,
	descripcion VARCHAR(250) NOT NULL,
	id_item_de_menu BIGINT NOT NULL,
	precio DECIMAL(10,2) NOT NULL,
	cantidad_disponible INT NOT NULL,
	fecha_creacion DATETIME NOT NULL,
    fecha_ultima_modificacion DATETIME NOT NULL,
    fecha_inicio DATETIME NOT NULL,
    fecha_fin DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (status) REFERENCES Status(id),
    FOREIGN KEY (estado) REFERENCES EstadoSugerencia(id),
    FOREIGN KEY (id_item_de_menu) REFERENCES ItemDeMenu(id),
    UNIQUE(nombre)
);



















	
	
	
