# <p align="center">History</p>

### [Вернуться на главную](/README.md)
### [Работа с API](/docs/api.md)

* [import](#IMPORT)
* [create](#CREATE)
* [read](#READ)
* [update](#UPDATE)
* [delete](#DELETE)
---

# IMPORT
`POST /api/histories/import`

```text
    Необходимо отправить XML FILE, данные для парсинга 
    жестко закодированы в HistoryXmlParser классе.
    При отсутствии ценной бумаги история не будет сохраняться.
```

# CREATE

`POST /api/histories`

```text
  Создать новый объект
```

Поля и их данные по умолчанию, если они не были переданы в запросе.
```json
{
  "admittedQuote": 0,
  "admittedValue": 0,
  "boardId": "string",
  "close": 0,
  "high": 0,
  "legalClosePrice": 0,
  "low": 0,
  "marketPrice2": 0,
  "marketPrice3": 0,
  "marketPrice3TradesValue": 0,
  "mp2ValTrd": 0,
  "numTrades": 0,
  "open": 0,
  "secId": "string",
  "shortname": "string",
  "tradeDate": "yyyy-MM-dd",
  "value": 0,
  "volume": 0,
  "waPrice": 0,
  "waVal": 0
}
```

# READ
`GET /api/histories[?page|size|sort|title]`
```text
  Получить список объектов
```

По умолчанию `GET /api/histories?page=0&size=10&sort=secId` - (desc)

* **page** - номер страницы, по умолчанию 0. `/api/histories?page=2`
* **size** - количество возвращаемых объектов, по умолчанию 10. `/api/histories?size=5`
* **sort** - имя поля для сортировки, case-sensitive :(  
  По умолчанию данные сортируются по **secId** - `/api/histories?sort=secId`,
  для сортировки по возрастанию необходимо через запятую написать **asc** - `/api/histories?sort=secId,asc`

* **date** - фильтрация по **trade_date**. `/api/histories?date=yyyy-MM-dd`

<br>

`GET /api/histories/{id}`
```text
  Получить объект по его ID
```


# UPDATE
`PATCH /api/histories/{id}`

```text
  Обновить существующий в БД объект по ID.
  Данные полей, не переданных в теле запросе, не изменятся
```

```json
{
  "admittedQuote": 0,
  "admittedValue": 0,
  "boardId": "string",
  "close": 0,
  "high": 0,
  "legalClosePrice": 0,
  "low": 0,
  "marketPrice2": 0,
  "marketPrice3": 0,
  "marketPrice3TradesValue": 0,
  "mp2ValTrd": 0,
  "numTrades": 0,
  "open": 0,
  "secId": "string",
  "shortname": "string",
  "tradeDate": "yyyy-MM-dd",
  "value": 0,
  "volume": 0,
  "waPrice": 0,
  "waVal": 0
}
```

# DELETE
`DELETE /api/histories/{id}`
```text
  Если объект существует по ID, то он будет удален вместо и с историей.
```

[НАВЕРХ](#History)
<br>