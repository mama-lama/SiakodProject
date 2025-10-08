package ru.vsu.sc.siakod25.g11.theme_6.unit_classes;

import ru.vsu.sc.siakod25.g11.theme_6.structure_classes.ArrayList;

public class Project {
    private String projectName;
    private ArrayList<ru.vsu.sc.siakod25.g11.theme_6.unit_classes.Character> characters;
    private ArrayList<Item> items;
    private ArrayList<Location> locations;
    private ArrayList<Plot> plots;
    //private ArrayList<Relationship> relationships;

    public Project(String projectName) {
        this.projectName = projectName;
        this.characters = new ArrayList<>();
        this.items = new ArrayList<>();
        this.locations = new ArrayList<>();
        this.plots = new ArrayList<>();
        //this.relationships = new ArrayList<>();
    }

    //---------Гетеры--------------
    public String getProjectName() {
        return projectName;
    }
    public ArrayList<ru.vsu.sc.siakod25.g11.theme_6.unit_classes.Character> getCharacters() {
        return characters;
    }
    public ArrayList<Item> getItems() {
        return items;
    }
    public ArrayList<Location> getLocations() {
        return locations;
    }
    public ArrayList<Plot> getPlots() {
        return plots;
    }
    /*public ArrayList<Relationship> getRelationships() {
        return relationships;
    }*/

    //---------Сетеры--------------
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    //---------Методы добавления---------------
    public void addCharacter(ru.vsu.sc.siakod25.g11.theme_6.unit_classes.Character character) {
        characters.add(character);
    }
    public void addItem(Item item) {
        items.add(item);
    }
    public void addLocation(Location location) {
        locations.add(location);
    }

    public void addPlot(Plot plot) {
        plots.add(plot);
    }

    //---------Методы удаления по ID---------------
    public boolean removeCharacter(int id) {
        for (int i = 0; i < characters.getSize(); i++) {
            if (characters.get(i).getId() == id) {
                characters.remove(i);
                //Отношения тоже удалять придется
                return true;
            }
        }
        return false;
    }
    public boolean removeItems(int id) {
        for (int i = 0; i < items.getSize(); i++) {
            if (items.get(i).getId() == id) {
                items.remove(i);
                return true;
            }
        }
        return false;
    }
    public boolean removeLocation(int id) {
        for (int i = 0; i < locations.getSize(); i++) {
            if (locations.get(i).getId() == id) {
                locations.remove(i);
                return true;
            }
        }
        return false;
    }

    /* Скорее всего убрать, поскольку предположительно отношения будут храниться в персонажах
    public boolean removeRelationship(int characterId1, int characterId2) {
        for (int i = 0; i < relationships.getSize(); i++) {
            Relationship relationship = relationships.get(i);
            if ((relationship.getThisCharacter().getId() == characterId1 &&
                    relationship.getOtherCharacter().getId() == characterId2) ||
                    (relationship.getThisCharacter().getId() == characterId2 &&
                            relationship.getOtherCharacter().getId() == characterId1)) {
                relationships.remove(i);
                return true;
            }
        }
        return false;
    }

    // Доработать Plot
    public boolean removePlot(int id) {
        for (int i = 0; i < plots.getSize(); i++) {
            if (plots.get(i).getId() == id) {
                plots.remove(i);
                return true;
            }
        }
        return false;
    }


    public void removeRelationshipsForCharacter(int characterId) {
        for (int i = relationships.getSize() - 1; i >= locations.getSize(); i--) {
            Relationship relationship = relationships.get(i);
            if (relationship.getThisCharacter().getId() == characterId ||
                    relationship.getOtherCharacter().getId() == characterId) {
                relationships.remove(i);

            }
        }
    } */

    //------------Другие методы---------------
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("==============" + this.getProjectName() + "==============\n");
        sb.append("Characters:\n");
        sb.append(characters.toString1());
        sb.append("\nItems:\n");
        sb.append(items.toString1());
        sb.append("\nLocations:\n");
        sb.append(locations.toString1());
        sb.append("\n=======================================");
        return sb.toString();
    }

    public static void main(String[] args) {
        Project pr1 = new Project("First");

        pr1.addCharacter(new ru.vsu.sc.siakod25.g11.theme_6.unit_classes.Character(1,"Менди"));
        pr1.addCharacter(new Character(2, "Рой", "Описание типо лол"));
        pr1.addLocation(new Location(1, "Школа"));
        pr1.addLocation(new Location(2, "Университет"));
        pr1.addLocation(new Location(3, "Город"));

        System.out.println(pr1.toString());

        pr1.removeCharacter(1);

        System.out.println(pr1.toString());
    }
}
