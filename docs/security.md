# <p align="center">Security</p>

### [Вернуться на главную](/README.md)
### [Работа с API](/docs/api.md)

* [import](#IMPORT)  
  
* [create](#CREATE)  
* [read](#READ)  
* [update](#UPDATE)  
* [delete](#DELETE)
---

# IMPORT
`POST /api/securities/import`

```text
    Необходимо отправить XML FILE, данные для парсинга 
    жестко закодированы в SecurityXmlParser классе.
```

# CREATE

`POST /api/securities`

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
  "tradeDate": "2021-05-02",
  "value": 0,
  "volume": 0,
  "waPrice": 0,
  "waVal": 0
}   
```

# READ
`GET /api/securities[?page|size|sort|title]`  
```text
  Получить список объектов
```

По умолчанию `GET /api/securities?page=0&size=10&sort=secId` - (desc) 

* **page** - номер страницы, по умолчанию 0. `/api/securities?page=2`
* **size** - количество возвращаемых объектов, по умолчанию 10. `/api/securities?size=5`
* **sort** - имя поля для сортировки, case-sensitive :(  
По умолчанию данные сортируются по **secId** - `/api/securities?sort=secId`, 
  для сортировки по возрастанию необходимо через запятую написать **asc** - `/api/securities?sort=secId,asc`

* **title** - фильтрация по **emitent_title**. `/api/securities?title=emitent title`

<br>

`GET /api/securities/{id}`
```text
  Получить объект по его ID
```


# UPDATE
`PATCH /api/securities/{id}`

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
  "tradeDate": "2021-05-02",
  "value": 0,
  "volume": 0,
  "waPrice": 0,
  "waVal": 0
}
```

# DELETE
`DELETE /api/securities/{id}`
```text
  Если объект существует по ID, то он будет удален вместо и с историей.
```

[НАВЕРХ](#Security)
<br>