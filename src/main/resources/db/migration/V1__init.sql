CREATE TABLE TODOS (
  id serial not null,
  user_id bigint not null,
  title varchar(255) not null,
  description varchar(255) not null,
  priority smallint not null,
  CREATED_DATE date not null,
  MODIFIED_DATE date not null,
  CREATED_BY bigint not null,
  MODIFIED_BY bigint not null
);
