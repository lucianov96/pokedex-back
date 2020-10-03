create database pokemon;
use pokemon;
create table pokemon(
	id bigint(10) primary key not null,
	name varchar(50) not null,
	ability_1 varchar(50) not null,
	ability_2 varchar(50),
	type_1 varchar(50) not null,
	type_2 varchar(50),
	hp bigint(10) not null,
	attack bigint(10) not null,
	defense bigint(10) not null,
	sp_attack bigint(10) not null,
    sp_defense bigint(10) not null,
    speed bigint(10) not null
);

create table movement(
	id bigint(10) primary key not null,
	name varchar(50) not null,
	type varchar(50) not null,
	movement_type varchar(50) not null,
	points bigint(10),
	accuracy bigint(10),
	priority boolean
);

create table pokemon_movement(
	id bigint(10) primary key not null auto_increment,
	id_pokemon bigint(10) not null,
	id_movement bigint(10) not null,
	foreign key (id_pokemon) references pokemon(id),
	foreign key (id_movement) references movement(id)
);

create table pokemon_catch_way(
	id bigint(10) primary key not null auto_increment,
	id_pokemon bigint(10) not null,
	pokemon_version varchar(50) not null,
	location varchar(50) not null,
	way varchar(50) not null,
	foreign key (id_pokemon) references pokemon(id)
);

