
-- use timestamp instead of date type
-- losing time part when using date type
-- timestamp value is in UTC

ALTER TABLE TODOS
  MODIFY COLUMN CREATED_DATE timestamp not null;


ALTER TABLE TODOS
  MODIFY COLUMN MODIFIED_DATE timestamp not null;
