CREATE SEQUENCE tag_seq;

CREATE TABLE tags (
  id int not null ,
  user_id int not null,
  name char(225) not null,
  primary key (id),
  unique (user_id, name)
);

CREATE TABLE todo_tag (
  todo_id int not null,
  tag_id int not null,
  primary key (todo_id, tag_id)
);
