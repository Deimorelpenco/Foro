create table Respuestas(

    id bigint not null auto_increment,
    mensaje varchar(300) not null unique,
    topicoId bigint not null,
    fechaCreacion dateTime not null,
    autorId bigint not null,
    solucion boolean not null,

    primary key (id),
    foreign key (topicoId) references Topicos(id) on delete cascade,
    foreign key (autorId) references Usuarios(id) on delete cascade

);