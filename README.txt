TODO:
-Reorganize framework -> DONE
-Classes, methods refactoring DONE
    -Use CommonPage inheritance for all pages - DONE
    -Remove catch NullPointer exception - DONE
    -Create pages from previous page method - done partially DONE
    -Remove test logic from pages DONE

-Add full test suit to testng xml DONE
-Design patterns:
    -Singleton DONE
    -Factory DONE
    -Static Factory DONE
    -Builder
    -Decorator


-Run tests with maven -> DONE with 'mvn surefire:test', testng.xml added
-Run tests with testng xml +runner -> DONE

1.	Посмотреть внимательно все классы: удалить временные комментарии, неиспользуемый код; comments with TODO left for now
2.	BookingPage: некоторые моменты нужно вынести в сервис или Utils (сложные вычисления, парсинг и т.д.)
3.	isElementVisible переименовать в isElementPresent (Displayed) и посмотреть все моменты, где еще используется isDisplayed; DONE
4.	IndexOutOfBoundsException: заменить try-catch на проверку size() DONE
5.	Не использовать static DONE
6.	Использовать private DONE
7.	Константы как final и название в формате DEFAULT_URL DONE
8.	В тестах есть несколько моментов, где нужно создание данных вынести или в Static Factory DONE, или в Builder TODO
9.	Enums: TODO
- делать throw custom exception
- использовать такой метод и тогда try-catch и throw custom exception не нужен
10.	Добавить Decorator (например для WebDriver) TODO
11.	Попробовать убрать priority в тестах и заменить testng.xml примерно так: DONE (fixed bug in passengersPopUpActivate method)
