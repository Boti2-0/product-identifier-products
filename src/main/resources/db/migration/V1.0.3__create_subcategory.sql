create sequence public.subcategory_id
    as integer;

create table sub_category
(
    id          integer not null DEFAULT nextval('subcategory_id'::regclass),
    sub_category varchar not null,
    category_id integer,
    CONSTRAINT pk_sub_category_id PRIMARY KEY (id),
    CONSTRAINT subcategory_category_id_fk FOREIGN KEY (category_id)
        REFERENCES public.category (id)

);

