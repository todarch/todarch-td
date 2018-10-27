
-- if it is only 'char(255)',
-- postgresql inserts as right-padded with spaces.

ALTER TABLE tags
  ALTER COLUMN name TYPE varchar(255);
