-- user table
create sequence public.user_id_seq increment 1 start 100 minvalue 1 maxvalue 9223372036854775807 cache 1;
create table public.user (
    user_id bigint primary key default nextval('public.user_id_seq'::regclass),
    email varchar(250) not null,
    password  varchar(250) not null,
    created_time timestamp default (now() at time zone 'UTC'),
    last_updated_time timestamp default (now() at time zone 'UTC')
);

create unique index email_uniqe_idx on public.user(email);

-- note table
create sequence public.note_id_seq increment 1 start 100 minvalue 1 maxvalue 9223372036854775807 cache 1;
create table public.note (
    note_id bigint primary key default nextval('public.note_id_seq'::regclass),
    user_id bigint not null constraint fk_user_id references public.user(user_id),
    title varchar(50) not null,
    description varchar(1000),
    created_time timestamp default (now() at time zone 'UTC'),
    last_updated_time timestamp default (now() at time zone 'UTC')
);

insert into public.user(email, password,created_time) values ('user1@disqo.com', 'p@ssw0rd1', now());
insert into public.user(email, password,created_time) values ('user2@disqo.com', 'p@ssw0rd2', now());
insert into public.user(email, password,created_time) values ('user3@disqo.com', 'p@ssw0rd3', now());
