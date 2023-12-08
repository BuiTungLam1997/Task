create table user
(
    id           bigint       not null primary key auto_increment,
    username     varchar(150) not null unique,
    password     varchar(150) not null,
    full_name    varchar(150) not null,
    created_by   varchar(150) not null,
    created_date timestamp,
    status       varchar(150) not null,
    email        varchar(150) unique
);
create table task
(
    id             bigint       not null primary key auto_increment,
    title          varchar(150) not null,
    content        text         not null,
    status         enum("ready","working","done") not null,
    performer      varchar(150),
    deadline_start timestamp,
    deadline_end   timestamp,
    created_by     varchar(150)
);
create table comment
(
    id           bigint not null primary key auto_increment,
    user_id      bigint not null,
    task_id      bigint not null,
    content      varchar(150),
    created_date timestamp
);

alter table comment
    add constraint fk_comment_user foreign key (user_id) references user (id);
alter table comment
    add constraint fk_comment_task foreign key (task_id) references task (id);

create table permission
(
    id   bigint       not null primary key auto_increment,
    name varchar(150) not null,
    code varchar(150) not null,
    note varchar(150)
);
create table group_permission
(
    id   bigint       not null primary key auto_increment,
    name varchar(150) not null,
    code varchar(150) not null,
    note varchar(150)
);
create table user_group
(
    id            bigint not null primary key auto_increment,
    user_id       bigint not null,
    permission_id bigint not null,
    group_id      bigint not null
);
alter table user_group
    add constraint fk_usergroup_permission foreign key (permission_id) references permission (id);
alter table user_group
    add constraint fk_usergroup_user foreign key (user_id) references user (id);
alter table user_group
    add constraint fk_usergroup_group foreign key (group_id) references group_permission (id);