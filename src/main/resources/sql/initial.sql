SET @@global.innodb_large_prefix = 1;
SET @@global.innodb_file_format=barracuda;
SET @@global.innodb_file_per_table=true;

drop schema if exists `DanceForm`;

create database if not exists DanceForm;
ALTER SCHEMA `DanceForm`  DEFAULT CHARACTER SET latin1;
