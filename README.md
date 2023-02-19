# AirportsSearch - Библиотека autocomplete вводимого текста
 -----
 Для запуска приложения в корне проекта находится батник start.bat, в котором уже прописан аргумент ограничения использования памяти, а также второй номер столбца.
 -----
 > <p>При запуске приложение полностью прочитывает файл, выбирая из него лишь значения указанного столбца и отступ в байтах каждой строчки от начала файла для более простой выборки данных всей строки позднее. (В моём случае это в среднем занимало 3.5 секунды, мб будет меньше если всё-таки решу сделать грамотное мультипотоковое чтение файла)</p>
 
 > <p>Выбранные данные хранятся в структуре SortedMap для простоты поиска нужных данных, из-за этого также дополнительно соблюдается требование сортировки данных.</p>
 
 > <p>Теперь программа попросит ввести строку для её автодополнения, после чего выполняется сам поиск по преждевременно сгенерированной структуре и подходящие значения забиваются в List. Подсчёт времени поиска идёт именно в этом куске кода с помощью nanoTime для большей точности, при указание данных как в примере у меня выходило в районе 15-17 мс.</p>
 
 > <p>После поиска происходит вывод найденной информации, однако поскольку мы имеем лишь значение выбранного столбца, мы добираем данные из файла в конкретной строке с помощью сохраненных отступов и RandomAccessFile. Также, сам вывод в консоль здесь реализован с помощью PrintWriter для оптимизации вывода большого количества строк подряд. Для большей наглядности данный отрывок также засекался по времени и в моём случае выполнялся примерно за 15 мс.</p>
 -----
 Постановка задачи:
 - При запуске программы указывается параметром номер колонки, где нумерация начинается с 1
 - После запуска программа выводит в консоль предложение ввести текст.
 - Программа выводит все строки с найденными значениями в формате "Найденное значение[Полная строка]"
 - После вывода всех строк программа должна вывести число найденных строк и время в миллисекундах, затраченное на поиск.
 - Затем предложить снова ввести текст для поиска.
 - Для завершения программы нужно ввести "!quit"
 -----
 Нефункциональные требования:
 - Перечитывать все строки файла при каждом поиске нельзя (в том числе читать только определенную колонку у каждой строки).
 - Нельзя редактировать csv-файл, создавать новые и использовать БД
 - Хранить весь файл в памяти нельзя(не только в качестве массива байт, но и в структуре, которая так или иначе содержит все данные из файла).
 - Выделять программе не более 7 МБ (-Xmx7m)
 - Сложность поиска меньше чем O(n), где n — число строк файла.
 - Ошибочные и краевые ситуации должны быть корректно обработаны.
 - Использовать готовые библиотеки для парсинга CSV формата нельзя.
 - Скорость поиска должна быть максимально высокой (на поиск по «Bo», который выдает 68 строк, требуется 25 мс).

