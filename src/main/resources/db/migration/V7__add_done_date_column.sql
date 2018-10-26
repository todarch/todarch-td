
ALTER TABLE todos
  ADD done_date timestamp;

ALTER TABLE todos ALTER COLUMN done_date
  SET DEFAULT null;

-- populate the done date for the ones that are already done

UPDATE todos SET done_date = todos.modified_date
  WHERE todo_status = 1;
