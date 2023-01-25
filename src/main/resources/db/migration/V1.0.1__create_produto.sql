CREATE SEQUENCE public.product_id;
ALTER TABLE public.product_id OWNER TO mkdigital;

create table produto
(
    id                           integer NOT NULL DEFAULT nextval('product_id'::regclass),
    site                         varchar,
    title                        varchar,
    description                  text,
    favorite                     bool,
    url                          varchar,
    activate_date                date,
    category                     varchar,
    sub_category                 varchar,
    initial_dollars_per_sale     double precision,
    average_dollars_per_sale     double precision,
    gravity                      double precision,
    total_rebill                 double precision,
    de                           bool,
    en                           bool,
    es                           bool,
    fr                           bool,
    it                           bool,
    pt                           bool,
    standard                     bool,
    physical                     bool,
    standard_url_present         bool,
    mobile_enabled               bool,
    whitelist_vendor             bool,
    cpa_visible                  bool,
    dollar_trial                 bool,
    has_additional_site_hoplinks bool,
    affiliates_tools_url         varchar,
    affiliates_support_email     varchar,
    skype_name                   varchar,
    can_ads_google               bool,
    CONSTRAINT pk_produto PRIMARY KEY (id)
);

