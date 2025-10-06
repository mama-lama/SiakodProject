package ru.vsu.sc.siakod25.g11.theme_6.unit_classes;

public class Item {
    private int id;
    private String name;
    private String description;
    private int ownerId;

    //-------Конструкторы-------
    public Item(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    public Item(int id, String name, String description, int ownerId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
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
    public int getOwnerId() {
        return ownerId;
    }

    //---------Сетеры----------------
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
    public String toString() {
        return "ID: " + id + "\nНазвание: " + name + "\nОписание: " + description + "\n";
    }

    //-------Ошибки-------
    private void idCheck(Character other) {
        if (this.id == other.getId()) {
            throw new IllegalArgumentException("Предметы не могут быть с одинаквоым id! (Ошибка)");
        }
    }
}
