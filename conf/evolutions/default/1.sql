# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table category (
  id                        varchar(255) not null,
  name                      varchar(255),
  constraint pk_category primary key (id))
;

create table resource (
  id                        varchar(255) not null,
  name                      varchar(255),
  author                    varchar(255),
  section_id                varchar(255),
  shelf_id                  varchar(255),
  available                 boolean,
  constraint pk_resource primary key (id))
;

create table section (
  id                        varchar(255) not null,
  name                      varchar(255),
  borrowable                boolean,
  constraint pk_section primary key (id))
;

create table user (
  id                        varchar(255) not null,
  name                      varchar(255),
  email                     varchar(255),
  constraint pk_user primary key (id))
;

create sequence category_seq;

create sequence resource_seq;

create sequence section_seq;

create sequence user_seq;

alter table resource add constraint fk_resource_section_1 foreign key (section_id) references section (id) on delete restrict on update restrict;
create index ix_resource_section_1 on resource (section_id);
alter table resource add constraint fk_resource_shelf_2 foreign key (shelf_id) references category (id) on delete restrict on update restrict;
create index ix_resource_shelf_2 on resource (shelf_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists category;

drop table if exists resource;

drop table if exists section;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists category_seq;

drop sequence if exists resource_seq;

drop sequence if exists section_seq;

drop sequence if exists user_seq;

