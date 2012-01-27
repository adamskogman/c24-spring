drop table IF EXISTS cdos;
create table cdos(id varchar(100),
	valid boolean, buyer varchar(128), seller varchar(128),
	xml varchar(10240));
