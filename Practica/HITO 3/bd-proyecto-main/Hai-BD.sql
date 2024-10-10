CREATE DATABASE  IF NOT EXISTS Hai;

USE Hai;

CREATE TABLE IF NOT EXISTS Editorial (
    Editorial_id INT UNIQUE NOT NULL,
    Nombre VARCHAR(255),
    Nacionalidad VARCHAR(50),
     PRIMARY KEY (Editorial_id)
);


CREATE TABLE IF NOT EXISTS Area_de_Investigacion (
    id_area_investigacion INT UNIQUE NOT NULL,
    Nombre_area_investigacion VARCHAR(255),
    PRIMARY KEY (id_area_investigacion)
);


CREATE TABLE IF NOT EXISTS Revista (
    Journal_id INT UNIQUE NOT NULL,
    JIF_Quartile VARCHAR(10),
    Jif FLOAT,
    Issn VARCHAR(20),
    Journal_name VARCHAR(255),
    Editorial_id INT,
    PRIMARY KEY (Journal_id),
    FOREIGN KEY (Editorial_id)
    REFERENCES Editorial (Editorial_id)
);


CREATE TABLE IF NOT EXISTS Articulo (
    DOI VARCHAR(50) UNIQUE NOT NULL,
    Titulo VARCHAR(255),
    URL VARCHAR(255),
    Num_citaciones INT,
    Fecha DATETIME,
    Journal_id INT,
    PRIMARY KEY (DOI),
    FOREIGN KEY (Journal_id)
    REFERENCES Revista (Journal_id)
);


CREATE TABLE IF NOT EXISTS Profesional (
    Numero_id INT UNIQUE NOT NULL,
    Nombre_completo VARCHAR(255),
    PRIMARY KEY (Numero_id)
);

CREATE TABLE IF NOT EXISTS Entidad (
    Affiliation_id INT UNIQUE NOT NULL,
    Affiliation_name VARCHAR(255),
    Ciudad VARCHAR(100),
    Nombre_pais VARCHAR(100),
    PRIMARY KEY (Affiliation_id)
);


CREATE TABLE IF NOT EXISTS Grupo_de_Trabajo (
    id_grupo INT UNIQUE NOT NULL,
    Nombre_grupo VARCHAR(255),
    id_area_investigacion INT,
    PRIMARY KEY (id_grupo),
    FOREIGN KEY (id_area_investigacion)
    REFERENCES Area_de_Investigacion (id_area_investigacion)
);


CREATE TABLE IF NOT EXISTS Pertenece (
    Journal_id INT,
    id_area_investigacion INT,
    PRIMARY KEY (Journal_id, id_area_investigacion),
    FOREIGN KEY (Journal_id) REFERENCES Revista (Journal_id),
    FOREIGN KEY (id_area_investigacion) REFERENCES Area_de_Investigacion (id_area_investigacion)
);

CREATE TABLE IF NOT EXISTS Envia_a_revisar (
    Journal_id INT,
    DOI VARCHAR(50),
    PRIMARY KEY (Journal_id, DOI),
    FOREIGN KEY (Journal_id) REFERENCES Revista (Journal_id),
    FOREIGN KEY (DOI) REFERENCES Articulo (DOI)
);

CREATE TABLE IF NOT EXISTS Referencia (
    DOI_referencia VARCHAR(50),
    DOI_referenciado VARCHAR(50),
    PRIMARY KEY (DOI_referencia, DOI_referenciado),
    FOREIGN KEY (DOI_referencia) REFERENCES Articulo (DOI),
    FOREIGN KEY (DOI_referenciado) REFERENCES Articulo (DOI)
);

CREATE TABLE IF NOT EXISTS Escribe (
    DOI VARCHAR(50),
    Numero_id INT,
    Firma VARCHAR(255),
    PRIMARY KEY (DOI, Numero_id),
    FOREIGN KEY (DOI) REFERENCES Articulo (DOI),
    FOREIGN KEY (Numero_id) REFERENCES Profesional (Numero_id)
);

CREATE TABLE IF NOT EXISTS Revisa (
    DOI VARCHAR(50),
    Numero_id INT,
    Fecha DATETIME,
    Resultado VARCHAR(255),
    PRIMARY KEY (DOI, Numero_id),
    FOREIGN KEY (DOI) REFERENCES Articulo (DOI),
    FOREIGN KEY (Numero_id) REFERENCES Profesional (Numero_id)
);

CREATE TABLE IF NOT EXISTS Colabora (
    Numero_id_a INT,
    Numero_id_b INT,
    PRIMARY KEY (Numero_id_a, Numero_id_b),
    FOREIGN KEY (Numero_id_a) REFERENCES Profesional (Numero_id),
    FOREIGN KEY (Numero_id_b) REFERENCES Profesional (Numero_id)
);

CREATE TABLE IF NOT EXISTS Afiliado (
    Numero_id INT,
    Affiliation_id INT,
    PRIMARY KEY (Numero_id, Affiliation_id),
    FOREIGN KEY (Numero_id) REFERENCES Profesional (Numero_id),
    FOREIGN KEY (Affiliation_id) REFERENCES Entidad (Affiliation_id)
);

CREATE TABLE IF NOT EXISTS Forma (
    Numero_id INT,
    id_grupo INT,
    PRIMARY KEY (Numero_id, id_grupo),
    FOREIGN KEY (Numero_id) REFERENCES Profesional (Numero_id),
    FOREIGN KEY (id_grupo) REFERENCES Grupo_de_Trabajo (id_grupo)
);

CREATE TABLE IF NOT EXISTS Asociado (
    Affiliation_id INT,
    id_grupo INT,
    PRIMARY KEY (Affiliation_id, id_grupo),
    FOREIGN KEY (Affiliation_id) REFERENCES Entidad (Affiliation_id),
    FOREIGN KEY (id_grupo) REFERENCES Grupo_de_Trabajo (id_grupo)
);