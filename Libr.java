import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.util.stream.Collectors;


public class Libr {
    //Тут идёт вся работа с JSON файлом. Сначала присваиваются значения для Gson (gson), затем с помощью конструкции обратотки ошибок (try... catch)
    // запускается магия обработки файла для читабельного вида
    //Сначала запускаются модули для чтения файла
    //Затем Список<ПосетителиКласс> (List<VisitorClass>) присваевается тип, для правильного чтения самой Джавой
    //После чего возвращает значения открытия файла (gson.fromJson), в котором указываются переменные,
    // считывающие сам файл и переменная, хранящая правильность чтения файла (reader и visList)

    public static List<VisitorClass> loadVisFromJson(String filepath){
        Gson gson = new GsonBuilder().create();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))){ //Используется БуфферРидер и ФайлРидер потому что ФайлРидер для работы с маленькими блоками, а БуфферРидер разбивает большие блоки на маленькие.
            Type visList = new TypeToken<List<VisitorClass>>() {}.getType();        //Тайп используется для того, чтобы показать JAVA каким образом обращаться со списком. ТайпТокен создает анонимный класс с типом List<VisitorClass>, а getType() возвращает объект Type
            return gson.fromJson(reader, visList);
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }


    //Тут начинается основа кода
    //Сначала переменной присваивается место расположения файла
    //Затем переменной из списка класса VisitorClass присваивается переменная из ранее созданного модификатора(?)(был бы это питон, я бы сказал функция, но я даже не знаю что ЭТО)
    //А потом циклом выводит значения из файла
    public static void main(String[] args){
        Integer count_vis  = 0;
        final Integer[] count_book = {0};
        Integer count_fav_book = 0;
        Integer middle_book = 0;
//        boolean jane_aust = false;

        String filepath = "C:/Users/books.json";
        List<VisitorClass> visitor = loadVisFromJson(filepath);

        if (visitor != null){

            for (VisitorClass visitors: visitor){
                count_fav_book = 0;
                count_vis++;

                System.out.println("Имя, Фамилия: " + visitors.getName() + " " + visitors.getSurname());
                System.out.println("Номер телефона: " + visitors.getPhone());
                System.out.println("Подписка: " + (visitors.isSubscribed()? "Подписан":"Не подписан")); //Структура: (конструкция ? "Значение если истина": "Значение если ложь")
                System.out.println("Книги: " );

                for (BookClass book: visitors.getFavoriteBooks()){

                        System.out.println("    – " + book.getName() + ". Автор: " + book.getAuthor() + ". Выпущенная в " + book.getPublishingYear());
                        count_fav_book++;

                    }

                middle_book = middle_book + count_fav_book;


                System.out.println("Количество книг в избранном: " + count_fav_book);
//                System.out.println("Книга от Jane Austen: " + (jane_aust? "Есть" : "Нет"));
                System.out.println(" " );
            }
            middle_book = middle_book / count_vis;

            System.out.println("Ср знач: " + middle_book);
        }
        System.out.println("Всего посетителей: " + count_vis);
        System.out.println("---------------------------------------------------------------------------------------------");

        // Список книг с сортировкой
        List<BookClass> books = visitor.stream()
                .distinct()
                .flatMap(visitors -> visitors.getFavoriteBooks().stream())
                .sorted(Comparator.comparing(BookClass::getPublishingYear))
                .collect(Collectors.toList());

        Set<String> uniqBook = new HashSet<>();
        books.forEach(book -> {
            if(uniqBook.add(book.name)) {
                System.out.println("Название: " + book.getName() + ".  Автор: " + book.getAuthor() + ".  Дата публикации: " + book.getPublishingYear());
                count_book[0]++;
            }
        });
        System.out.println("Книг всего: " + count_book[0]);
        System.out.println("---------------------------------------------------------------------------------------------");

        List<VisitorClass> subVis = visitor.stream()
                .filter(VisitorClass::isSubscribed)
                .collect(Collectors.toList());

        double averangeFav = subVis.stream()
                .mapToInt(vis -> vis.getFavoriteBooks().size())
                .average()
                .orElse(0.0);
        // Создаем SMS-сообщения
        List<SmsMessage> smsMessages = subVis.stream()
                .map(vis -> {
                    int favoriteCount = vis.getFavoriteBooks().size();
                    String message;

                    if (favoriteCount > averangeFav) {
                        message = "you are a bookworm";
                    } else if (favoriteCount < averangeFav) {
                        message = "read more";
                    } else {
                        message = "fine";
                    }

                    return new SmsMessage(vis.getPhone(), message);
                })
                .collect(Collectors.toList());

        // Выводим сообщения на экран
        smsMessages.forEach(System.out::println);

    }
}
