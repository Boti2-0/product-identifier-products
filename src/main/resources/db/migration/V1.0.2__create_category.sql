create sequence public.category_id
    as integer;

create table category
(
    category varchar not null,
    id       integer not null DEFAULT nextval('category_id'::regclass),
    CONSTRAINT pk_category_id PRIMARY KEY (id)
);

comment on table public.category is 'category of products';

