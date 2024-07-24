create sequence public.category_id
    as integer;

create table category
(
    category varchar not null,
    marketplace varchar not null,
    id       integer not null DEFAULT nextval('category_id'::regclass),
    CONSTRAINT pk_category_id PRIMARY KEY (id)
);

comment on table public.category is 'category of products';

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

CREATE SEQUENCE public.product_id;
ALTER TABLE public.product_id OWNER TO products_identifier;

create table product
(
    id                           integer NOT NULL DEFAULT nextval('product_id'::regclass),
    site                         varchar,
    key                          varchar,
    title                        varchar,
    description                  text,
    url                          varchar,
    activate_date                date,
    category_id                  integer,
    sub_category_id              integer,
    initial_dollars_per_sale     double precision,
    average_dollars_per_sale     double precision,
    ranking                      varchar,
    total_rebill                 double precision,
    standard                     bool,
    physical                     bool,
    upsell                       bool,
    rebill                       bool,
    affiliate_url               varchar,
    google_ads_available         bool,
    valid                        bool,
    marketplace                  varchar,
    CONSTRAINT pk_product PRIMARY KEY (id),
    CONSTRAINT product_category_id_fk FOREIGN KEY (category_id)
        REFERENCES public.category (id),
    CONSTRAINT product_subcategory_category_id_fk FOREIGN KEY (sub_category_id)
        REFERENCES public.sub_category (id)
);