# Job4j Pooh

Учебный проект по теме многопоточность в Java. Представляет собой простой аналог асинхронной очереди. Порядок работы:

- Приложение запускает Socket и ждет клиентов.
- Клиенты могут быть двух типов: отправители (publisher), получатели (subscriber).
- В качестве клиента используется cURL.
- В качестве протокола используется HTTP.
- Имеет два режима:
  - "topic" - для каждого потребителя создается своя уникальная очередь с данными;
  - "queue" - все потребители получают данные из одной общей очереди.