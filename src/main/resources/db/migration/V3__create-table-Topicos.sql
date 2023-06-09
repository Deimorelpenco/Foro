create table Topicos(

    id bigint not null auto_increment,
    titulo varchar(100) not null unique,
    mensaje varchar(100) not null unique,
    fechaCreacion dateTime not null,
    status varchar(100) not null,
    autorId bigint not null,
    cursoId bigint not null,

    primary key (id),
    foreign key (autorId) references Usuarios(id) on delete cascade,
    foreign key (cursoId) references Cursos(id) on delete cascade

);