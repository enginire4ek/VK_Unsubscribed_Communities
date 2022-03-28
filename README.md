## Тестовое задание для отбора на стажировку в VK Android Core



### Необходимо разработать Android приложение для отписки от сообществ ВКонтакте 

Вот список требований:

 0. Приложение должно быть написано на Kotlin с использованием Android SDK
 1. Верстка приложения должна соответствовать приложенным [скриншотам](https://www.sketch.com/s/8f3df3be-dd2a-4881-82ad-8a46a983cb27)
 2. Авторизацию и методы API необходимо реализовывать через [VK SDK](https://github.com/VKCOM/vk-android-sdk)
 3. Реализовать режим показа сообществ, от которых пользователь отписался в реализованном приложении:
    1. В Toolbar должна быть добавлена toggle-кнопка, которая включает режим показа тех сообществ, от которых пользователь отписался ранее
    2. В режиме видимости сообществ, от которых пользователь отписался, тап на такое сообщество приводит к следующему поведению:
        1. Добавляет его в список выбранных сообществ
        2. Увеличивает каунтер, отображаемый в кнопке "Подписаться" (Кнопка аналогична кнопке "Отписаться" по расположению на экране и стилю в предоставленном
            макете). Если список выбранных сообществ пуст - кнопка "Подписаться" перестает отображаться
        3. Нажатие на кнопку "Подписаться" приводит к подписке на все выбранные сообщества
 4. Запрещается использовать сторонние библиотеки кроме VK SDK,  Fresco и AndroidX
 5. Стиль всех UI элементов, которые не были указаны на скриншотах, должен быть взят из [VKUI Android Library](https://www.figma.com/community/file/842774413870475604)
 6. Нужно поддержать поворот экрана (то есть приложение должно иметь и landscape ориентацию экрана)
 7. minSdkVersion - 23, targetSdkVersion - 31
 8. Минимальная версия gradle - 7.0

## Репозиторий содержит в себе:

 1. Исходный код реализованного приложения
 2. Cкомпилированную и корректно подписанную apk (важно внимательно прочитать [документацию](https://dev.vk.com/sdk/android))
 3. Видео с записью экрана вашего приложения, демонстрирующее функционал приложения:
    1. Авторизацию
    2. Отображение списка сообществ, на которые подписан пользователь
    3. Отображение списка сообществ, от которых пользователь отписался
    4. Оформление подписки на сообщество
    5. Оформление отписки от сообщества
    6. Поворот экрана
