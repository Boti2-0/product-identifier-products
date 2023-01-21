create table produto
(
    id                           serial
        constraint produto_pk
            primary key,
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
    can_ads_google               bool
);

