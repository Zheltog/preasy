create table save (
    id text primary key,
    password text,
    file bytea not null,
    expires timestamp without time zone not null
);

create index save_expires_index on save(expires);