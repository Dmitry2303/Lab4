public class BookClass {
    public String name;
    public String author;
    public Integer publishingYear;
    public String isbn;
    public String publisher;

    //Тут указывается то, что у тебя будет принимать значения из списка, то есть если
    // в JSON есть значения у имени, то оно будет искать именно name, не какой-нидудь условный nameBook
    //так что надо смотреть файл и указывать значение соответствующие именно книге
    public String getName(){
        return name;
    }

    public String getAuthor(){
        return author;
    }

    public Integer getPublishingYear(){
        return publishingYear;
    }

    public String getIsbn(){
        return isbn;
    }

    public String getPublisher(){
        return publisher;
    }

}
