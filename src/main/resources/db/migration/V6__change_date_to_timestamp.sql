
-- use timestamp instead of date type
-- losing time part when using date type
-- timestamp value is in UTC

ALTER TABLE TODOS
  ALTER COLUMN CREATED_DATE TYPE timestamp;


ALTER TABLE TODOS
  ALTER COLUMN MODIFIED_DATE TYPE timestamp;
