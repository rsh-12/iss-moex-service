# <p align="center">Как запустить приложение</p>

### [Вернуться на главную](/README.md)

---

## <p style="text-align: center">GitHub</p>

1. Необходимо скачать [архив проекта](https://github.com/rsh-12/iss-moex-service) или в нужной вам директории склонировать репозиторий: **git clone git@github.com:rsh-12/iss-moex-service.git**
   

2. Далее необходимо в папке приложения найти **src/main/resources/application.properties** и заменить **jdbc url, username, password** DB на собственные значения. 


3. **mvn spring-boot:run** загрузит необходимые зависимости и запустит наше приложение.
<br>

## <p style="text-align: center">Docker Hub</p>
1. Найти файл docker-compose.yml [здесь](https://github.com/rsh-12/iss-moex-service/blob/main/docker-compose.yml). 
   

2. Скачать этот файл, внести необходимые корректировки (порты или env, etc.)
   

3. **docker-compose up** 