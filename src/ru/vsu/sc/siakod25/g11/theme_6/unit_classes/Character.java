package ru.vsu.sc.siakod25.g11.theme_6.unit_classes;

public class Character {
    private int id;
    private String name;
    private String description;
    private Relationship relationships; // Заменить на Map

    public Character(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.relationships = null; // Заменить на Map
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
    public Relationship getRelationships() {
        return relationships;
    } // Заменить на Map
    //---------Сетеры----------------
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    //--------Работа с отношениями--------------
    public Relationship addRelationship(Character other, int level) {
        idCheck(other);
        Relationship relationship = new Relationship(this, other, level);
        return relationship;
    } // Доработать с Map!

    public Relationship addRelationship(Character other) {
        return addRelationship(other,0);
    }

    //public Relationship updateRelationship(Character other, int newLevel) {}

    public String toString() {
        return "ID: " + id + "\nИмя: " + name + "\nОписание: " + description + "\n";
    } // Переделать

    //-------Ошибки-------
    private void idCheck(Character other) {
        if (this.id == other.getId()) {
            throw new IllegalArgumentException("Персонаж не может иметь отношения с самим собой! (Ошибка)");
        }
    }

}
