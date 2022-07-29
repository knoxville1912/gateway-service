Здесь находится описание gateway-service  
Описание всего проекта находится по ссылке ниже:  
[README.md проекта](https://github.com/knoxville1912/project/blob/main/README.md)  
  
## Сервис gateway, здесь происходит генерация данных больных, лежащих в реанимации и отправка их(данных) в другие сервисы ##

В gateway-service происходит генерация данных больных каждые 5 секунд, с помощью шедуллера, они отправляются в очередь, в сервис [regular](https://github.com/knoxville1912/Regular-service/blob/main/README.md),
а также, если данные больных угрожают их жизни, то данные отправляют в сервис [emergency](https://github.com/knoxville1912/emergency-service/blob/main/README.md) с помощью restTemplate
