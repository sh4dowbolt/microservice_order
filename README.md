<h1 align="center">Order Microservice</h1>


## Описание
Order Microservice - демо проект для демонстрации микросервисной архитектуры.
Представляет собой headless backend приложение, разработанное на Java, Spring Boot. 
Для хранения данных используется MongoDB Atlas.
В рамках приложения происходят CRUD операции по модели взаимодействия клиента с электронным магазином.

Данный микросервис взаимодействует с микросервисом клиентов https://github.com/sh4dowbolt/microservice_customer.
Используя REST API демонстрируют микросервисное взаимодействие.

## Инструкция по запуску
### Требования:
- Установлен Docker
### Последовательность:
1. Сделать git clone проекта
2. Запустить docker
3. В первую очередь, должен быть запущен Customer Microservice(https://github.com/sh4dowbolt/microservice_customer, инструкция по запуску прилагается).
Затем из корневой папки запустить команду:```docker-compose up -d``` (создаст две контейнера: базу данных и сам микросервис).
4. После того как контейнеры запустятся, приложение будет доступно на 8100 порту.
5. Ready

Для проверки работоспособности приложения пройти по линку:
#### http://localhost:8100/order/api/v1/health
Сервис описан с помощью Open API и Swagger. Для получения документации пройти по линку:
#### http://localhost:8100/order/swagger-ui/index.html

### TODO:
1. Развернуть контейнеры в Kubernetes и EKS
2. Настроить мониторинг


