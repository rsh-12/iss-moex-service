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
Обязательны **id, name, secId**
```json
{
  "emitentId": 0,
  "emitentInn": "string",
  "emitentOkpo": "string",
  "emitentTitle": "string",
  "gosreg": "string",
  "group": "string",
  "id": 0,
  "isTraded": 0,
  "isin": "string",
  "marketPriceBoardid": "string",
  "name": "string",
  "primaryBoardid": "string",
  "regnumber": "string",
  "secId": "string",
  "shortname": "string",
  "type": "string"
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

## Вывод `close, emitentTitle, name, numTrades, open, regnumber, secId, tradeDate`

<br>

`GET /api/securities/view[?page|size|sort|title|date]` - см. выше

```text
  Сортировка осуществляется по всем приведенным выше полям.
  Фильтрая по name и tradeDate.
```


# UPDATE
`PATCH /api/securities/{id}`

```text
  Обновить существующий в БД объект по ID.
  Данные полей, не переданных в теле запросе, не изменятся
```

```json
{
  "emitentId": 0,
  "emitentInn": "string",
  "emitentOkpo": "string",
  "emitentTitle": "string",
  "gosreg": "string",
  "group": "string",
  "id": 0,
  "isTraded": 0,
  "isin": "string",
  "marketPriceBoardid": "string",
  "name": "string",
  "primaryBoardid": "string",
  "regnumber": "string",
  "secId": "string",
  "shortname": "string",
  "type": "string"
}
```

# DELETE
`DELETE /api/securities/{id}`
```text
  Если объект существует по ID, то он будет удален вместе с историей.
```

[НАВЕРХ](#Security)
<br>