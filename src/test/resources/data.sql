insert into securities (id, sec_id, shortname, regnumber, name, isin, is_traded, emitent_id, emitent_title, emitent_inn,
                        emitent_okpo, gosreg, type, "group", primary_boardid, marketprice_boardid)
values (154676, '1', 'Apple', '',
        'Apple Inc.', 'US0378331005', 1, 1281003,
        'Apple Inc', '', '', '',
        'common_share', 'stock_shares', 'EQRD', ''),

       (12441, '2', 'АбрауДюрсо', '1-02-12500-A',
        'Абрау-Дюрсо ПАО ао', 'RU000A0JS5T7', 1, 2556,
        'Публичное акционерное общество "Абрау – Дюрсо"', 7727620673, '', '1 - 02 - 12500 - A',
        'common_share', 'stock_shares', 'TQBR', 'TQBR'),

       (131568, '3', 'АСКО ао', '1-01-52065-Z',
        'АСКО-СТРАХОВАНИЕ ПАО ао', 'RU000A0JXS91', 1, 1098705,
        'Публичное акционерное общество "АСКО-СТРАХОВАНИЕ"', 7453297458, '', '1-01-52065-Z',
        'common_share', 'stock_shares', 'TQDE', 'TQDE');


insert into history(board_id, trade_date, shortname, sec_id_fk, num_trades,
                    value, open, low, high, legal_close_price,
                    wa_price, close, volume, market_price2, market_price3,
                    admitted_quote, mp2_val_trd, market_price3_trades_value, admitted_value, wa_val)
values ('TQBR', '2020-04-15'::date, 'АбрауДюрсо', '1', 171, 734875, 135.5, 133.5, 136.5, 134.5, 135,
        134.5, 5440, 135, 135, 134.5, 734875, 734875, 734875, null),

       ('TQTE', '2020-04-15'::date, 'AKEU ETF', '2', 71, 24909.79, 9.02, 8.73, 9.02, 8.74, 8.81, 8.73,
        2827, 705.96, 705.96, 8.74, 1995737.53, 1995737.53, 1995737.53, 59809.7),

       ('TQTD', '2020-04-15'::date, 'AKNX ETF', 3, 403, 478429, 12.58, 12.37, 12.67, 12.53, 12.51,
        12.5, 38244, 917.17, 917.17, 12.53, 35076094.06, 35076094.06, 35076094.06, 277384.75)