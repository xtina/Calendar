
create table calendar(
datetime varchar(8) not null,
memo varchar(20000),
constraint pk_calendar primary key (datetime)
);

create table event(
datetime varchar(8) not null,
name varchar(20000),
starttime varchar(5),
endtime varchar(5),
constraint fk_event foreign key(datetime) references calendar (datetime)
);


create table alarm(
datetime varchar(8) not null,
name varchar(20000),
starttime varchar(5),
constraint fk_alarm foreign key(datetime) references calendar (datetime)
);

