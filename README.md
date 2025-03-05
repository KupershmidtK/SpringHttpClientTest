Для запуска Postgres и создания БД необходимо запустить контейнер

```declarative
docker compose -d up
```
Для остановки контейнера
```declarative
docker compose down
```
Если что-то пошло не по резьбе можно удалить volume и образ приложенным скриптом
```declarative
./clear_container.sh
```