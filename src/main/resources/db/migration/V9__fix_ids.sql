
CREATE SEQUENCE todo_seq
  START WITH 100
  INCREMENT BY 100;

ALTER TABLE todos
  ALTER COLUMN id TYPE bigint;

ALTER TABLE todo_tag
  ALTER COLUMN todo_id TYPE bigint;

ALTER TABLE todo_tag
  ALTER COLUMN tag_id TYPE bigint;

ALTER TABLE tags
  ALTER COLUMN id TYPE bigint;

ALTER TABLE tags
  ALTER COLUMN user_id TYPE bigint;
