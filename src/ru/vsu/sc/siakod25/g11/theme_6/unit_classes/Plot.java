package ru.vsu.sc.siakod25.g11.theme_6.unit_classes;

public class Plot {
    // В основе будет лежать граф
    private int id;
    private String name;
    private String description;

    public Plot(int id, String name) {
        this.id = id;
        this.name = name;
        this.description = null;
    }

    public Plot(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    //-----------Геттеры----------------
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    //-----------------Сеттеры--------------------
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        if (description != null) {
            return "ID: " + id + " | Название: " + name + " | Описание: '" + description + "';\n";
        } else {
            return "ID: " + id + " | Название: " + name + " | Описание: " + description + ";\n";
        }
    }
}
