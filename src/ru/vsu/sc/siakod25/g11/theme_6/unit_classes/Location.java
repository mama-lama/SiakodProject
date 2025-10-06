package ru.vsu.sc.siakod25.g11.theme_6.unit_classes;

public class Location {
    private int id;
    private String name;
    private String description;

    //-------Конструкторы-------
    public Location(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    //---------Гетеры----------------
    public int getId(){
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    //---------Сетеры----------------
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return "ID: " + id + "\nНазвание: " + name + "\nОписание: " + description + "\n";
    }

    //-------Ошибки-------
    private void idCheck(Character other) {
        if (this.id == other.getId()) {
            throw new IllegalArgumentException("Локации не могут быть с одинаковым id! (Ошибка)");
        }
    }
}
