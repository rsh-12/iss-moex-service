create table securities
(
    id                  int unique,
    sec_id              varchar(64) primary key,
    shortname           varchar(64),
    regnumber           varchar(128),
    name                varchar(128),
    isin                varchar(128),
    is_traded           int,
    emitent_id          int,
    emitent_title       varchar(128),
    emitent_inn         varchar(64),
    emitent_okpo        varchar(64),
    gosreg              varchar(128),
    type                varchar(64),
    "group"             varchar(64),
    primary_boardid     varchar(4),
    marketprice_boardid varchar(4)
);

create table history
(
    id                         serial primary key,
    board_id                   varchar(32),
    trade_date                 date,
    shortname                  varchar(128),
    sec_id_fk                  varchar references securities (sec_id) on DELETE CASCADE,
    num_trades                 numeric(20, 4),
    value                      numeric(20, 4),
    open                       numeric(20, 4),
    low                        numeric(20, 4),
    high                       numeric(20, 4),
    legal_close_price          numeric(20, 4),
    wa_price                   numeric(20, 4),
    close                      numeric(20, 4),
    volume                     numeric(20, 4),
    market_price2              numeric(20, 4),
    market_price3              numeric(20, 4),
    admitted_quote             numeric(20, 4),
    mp2_val_trd                numeric(20, 4),
    market_price3_trades_value numeric(20, 4),
    admitted_value             numeric(20, 4),
    wa_val                     numeric(20, 4)
);