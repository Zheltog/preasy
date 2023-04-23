create table save (
    id text primary key,
    password text,
    file bytea not null,
    time timestamp without time zone not null
);