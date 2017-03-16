SET @@global.innodb_large_prefix = 1;
SET @@global.innodb_file_format=barracuda;
SET @@global.innodb_file_per_table=true;

drop schema if exists `Dance_Form`;

create database if not exists Dance_Form;
ALTER SCHEMA `Dance_Form`  DEFAULT CHARACTER SET utf8;
