INSERT INTO task(title, content, status, performer, deadline_start, deadline_end, created_by) VALUES ('Delete ', 'Tại api delete tất cả những người đã nghỉ việc ', 'ready', 'admin', '2023-09-12 05:21:00', '2023-09-17 05:21:00', 'admin');
INSERT INTO task (title, content, status, performer, deadline_start, deadline_end, created_by) VALUES ('Insert', 'Tạo api insert group', 'ready', 'admin', '2023-09-12 05:21:00', '2023-09-17 05:21:00', 'admin');
INSERT INTO task(title, content, status, performer, deadline_start, deadline_end, created_by) VALUES ('Insert', 'Tạo service sau khi thêm 1 user thì add vào group login', 'ready', 'admin', '2023-09-12 05:21:00', '2023-09-17 05:21:00', 'admin');

INSERT INTO group_permission (name, code) VALUES ('Thêm sửa task', 'ADMIN_TASK_INSERT_AND_UPDATE');
INSERT INTO group_permission (name, code) VALUES ('Xóa task', 'ADMIN_TASK_DELETE');

