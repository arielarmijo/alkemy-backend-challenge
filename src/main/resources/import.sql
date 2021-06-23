insert into personajes (id, imagen, nombre, edad, peso) values(1, 'https://static.dw.com/image/19339014_401.jpg', 'Peter Pan 1', 10, 45);
insert into personajes (id, imagen, nombre, edad, peso) values(2, 'https://static.dw.com/image/19339014_401.jpg', 'Peter Pan 2', 10, 45);
insert into personajes (id, imagen, nombre, edad, peso) values(3, 'https://cdn.hobbyconsolas.com/sites/navi.axelspringer.es/public/styles/1200/public/media/image/2019/05/pelicula-cruella-vil-llegara-2020.jpg', 'Cruella De Vil', 25, 54);

insert into generos (id, nombre, imagen) values(1, 'Fantasy', 'https://i0.wp.com/xn--oo-yjab.cl/wp-content/uploads/2020/06/oso-polar-definicion-genero-de-la-fantasia.jpg');
insert into generos (id, nombre, imagen) values(2, 'Drama', 'http://2.bp.blogspot.com/-nu8vpnOX3y4/T_2bjyYm4NI/AAAAAAAACO8/Mr0aw1RXgTQ/s1600/lady-oscar-musical.jpg');

insert into peliculas (id, titulo, imagen, fecha_creacion, calificacion, genero_id) values (1,'Peter Pan I', 'https://i2.wp.com/centraldeheroes.com/wp-content/imagenes/2019/02/peter-pan-66-aniversario-central-de-heroes.jpg', DATE'2021-01-01', 5, 1);
insert into peliculas (id, titulo, imagen, fecha_creacion, calificacion, genero_id) values (2,'Peter Pan II', 'https://i2.wp.com/centraldeheroes.com/wp-content/imagenes/2019/02/peter-pan-66-aniversario-central-de-heroes.jpg', DATE'2021-02-02', 3, 1);
insert into peliculas (id, titulo, imagen, fecha_creacion, calificacion, genero_id) values (3,'Peter Pan III', 'https://i2.wp.com/centraldeheroes.com/wp-content/imagenes/2019/02/peter-pan-66-aniversario-central-de-heroes.jpg', DATE'2021-03-03', 1, 1);
insert into peliculas (id, titulo, imagen, fecha_creacion, calificacion, genero_id) values (4,'Cruella', 'https://es.web.img3.acsta.net/pictures/21/04/21/11/08/5393301.jpg', DATE'2021-06-15', 5, 2);

insert into personajes_peliculas (personaje_id, pelicula_id) values (1, 1);
insert into personajes_peliculas (personaje_id, pelicula_id) values (1, 2);
insert into personajes_peliculas (personaje_id, pelicula_id) values (1, 3);
insert into personajes_peliculas (personaje_id, pelicula_id) values (3, 4);

insert into usuarios (id, username, password, enabled) values (1, 'arielarmijo@yahoo.es', '$2y$12$juyJgR6jrZ16.r2.AAR87eQwK3.CDJGPTK8fDJK0s0fGACwn2pble', 1);

insert into roles (id, nombre) values (1, 'ROLE_USER');

insert into usuarios_roles (usuario_id, rol_id) values (1, 1);