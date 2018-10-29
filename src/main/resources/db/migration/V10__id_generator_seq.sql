
-- application requests for next id, better to increment by 1

DROP SEQUENCE tag_seq;

DROP SEQUENCE todo_seq;

CREATE SEQUENCE todo_id_seq
  START WITH 300
  INCREMENT BY 100;

CREATE SEQUENCE tag_id_seq
  START WITH 300
  INCREMENT BY 100;

-- these two id table do not have to have constraints
-- they are hold dummy data for 'save and retrieve id' operation

CREATE TABLE tag_id (
  id bigint
);

CREATE TABLE todo_id (
  id bigint
);
