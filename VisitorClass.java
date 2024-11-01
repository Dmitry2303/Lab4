import java.util.List;

class VisitorClass {
    private String name;
    private String surname;
    private String phone;
    private boolean subscribed;
    private List<BookClass> favoriteBooks;

    //Тут указывается то, что у тебя будет принимать значения из списка, то есть если
    //в JSON есть значения у имени, то оно будет искать именно name, не какой-нибудь условный FirstName
    //так что надо смотреть файл и указывать значение соответствующие именно человеку

    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }
    public String getPhone(){
        return phone;
    }
    public boolean isSubscribed(){
        return subscribed;
    }
    public List<BookClass> getFavoriteBooks(){
        return favoriteBooks;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setSurname(String surname){
        this.surname = surname;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public void setSubscribed(boolean subscribed){
        this.subscribed = subscribed;
    }

    public void setFavoriteBooks(List<BookClass> favoriteBooks){
        this.favoriteBooks = favoriteBooks;
    }
}
