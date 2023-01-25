create sequence public.product_id
    as integer;

alter table produto
    alter column id set default nextval('product_id'::regclass);

