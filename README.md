[![CI](https://github.com/Romanow/papajohns-pizza-order/actions/workflows/build.yml/badge.svg)](https://github.com/Romanow/papajohns-pizza-order/actions/workflows/build.yml)
[![pre-commit](https://img.shields.io/badge/pre--commit-enabled-brightgreen?logo=pre-commit)](https://github.com/pre-commit/pre-commit)
[![License](https://img.shields.io/github/license/Romanow/papajohns-pizza-order)](https://github.com/Romanow/papajohns-pizza-order/blob/master/LICENSE)

# Автоматизация заказа пиццы Итальянская с моцареллой и пепперони с сырным бортиком в пиццерии Papa Johns

## Запуск

Для авторизации на сайте [Papa Johns](https://papajohns.ru) нужно прописать логин и пароль в env (`PAPA_JOHNS_LOGIN`,
`PAPA_JOHNS_PASSWORD`), либо в файле [config.properties](src/test/resources/config.properties).

Запуск тестов выполняется с помощью команды `./gradlew test`.
