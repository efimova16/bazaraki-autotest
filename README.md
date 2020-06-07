
## Настройка окружения:
необходимо установить и настроить переменные среды для следующих компонент
 
 * Java SDK 8
 
 * maven 
 
 http://maven.apache.org/download.cgi
 
 * Android Studio + Android SDK + создать 2 Android эмулятора c установленным Google Chrome

 https://developer.android.com/studio

 https://nishantverma.gitbooks.io/appium-for-android/system_installation/installing_android.html
 
  Результат команды
 
 ```emulator -list-avds```

 должен отображать настроенные эмуляторы
 
 * node.js
 
 https://nodejs.org/en/
 
 * Appium command tool + Chromedriver, соответствующий версии Chrome на эмуляторах
 
 http://appium.io/docs/en/about-appium/getting-started/?lang=ru#installing-appium

 https://github.com/appium/appium/blob/master/docs/en/writing-running-appium/web/chromedriver.md
 
 Например
 
 ```npm install -g appium --chromedriver_version="81.0.4044.138"```
 

## Запуск Selenium Grid:

Запустить хаб из папки ..\bazaraki-autotest\grid командой

```java -jar selenium-server-standalone-3.141.59.jar -role hub```

В браузере по ссылке  http://localhost:4444/grid/console проверить статус хаба

В файлах ../grid/nodeconfig_android_*.json указать настройки своих эмуляторов

Запустить Appium как ноды из папки ..\bazaraki-autotest\grid командами (использовать названия своих эмуляторов)

```appium -p 4734 --chromedriver-port 8000 --avd Nexus_6_API_25_Portrait --orientation PORTRAIT --nodeconfig nodeconfig_android_portrait.json > logP.txt```

```appium -p 4733 --chromedriver-port 8001 --avd Nexus_5_API_25_Landscape --orientation LANDSCAPE --nodeconfig nodeconfig_android_landscape.json > logL.txt```

В браузере по ссылке  http://localhost:4444/grid/console проверить статус нод


## Запуск автотестов:

Автотесты запускаются в корне проекте ..\bazaraki-autotest командой

```mvn -U clean integration-test serenity:aggregate```

## Отчет о прогоне автотестов:
Для просмотра полного отчета о результатах прогона автотестов открыть файл ..\target\site\\serenity\index.html
