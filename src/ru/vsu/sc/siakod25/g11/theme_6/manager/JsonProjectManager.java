package ru.vsu.sc.siakod25.g11.theme_6.manager;

import ru.vsu.sc.siakod25.g11.theme_6.structure_classes.ArrayList;
import ru.vsu.sc.siakod25.g11.theme_6.unit_classes.Project;
import ru.vsu.sc.siakod25.g11.theme_6.unit_classes.Plot;
import ru.vsu.sc.siakod25.g11.theme_6.unit_classes.Location;
import ru.vsu.sc.siakod25.g11.theme_6.unit_classes.Item;
import ru.vsu.sc.siakod25.g11.theme_6.unit_classes.Character;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonProjectManager {
    private static final String PROGECTS_DIR = "projects";
    private static JsonProjectManager instance; //Единственный экземпляр
    private Project currentProject;

    //------------Конструктор в единственном экземпляре------------
    private JsonProjectManager() {
        createProjectDirectory();
    }

    public static JsonProjectManager getInstance() {
        if (instIsNull()) {
            instance = new JsonProjectManager();
        }
        return instance;
    } // Гарантирует единственное существование менеджера

    public static boolean instIsNull() {
        return instance == null;
    }

    private void createProjectDirectory() {
        File dir = new File(PROGECTS_DIR);
        if (!dir.exists()) { // Проверка на существование
            dir.mkdirs(); // Создает папки
        }
    }

    public void createNewProject(String projectName) {
        this.currentProject = new Project(projectName);
        saveProject();
    }

    //---------------Гетеры---------------
    public Project getCurrentProject() {
        return currentProject;
    }

    public String getProgectsDir() {
        return PROGECTS_DIR;
    }

    public String[] getExistingProjects() {
        File dir = new File(getProgectsDir()); // Папка с проектами
        File[] files = dir.listFiles((d, name) -> name.endsWith(".json")); // Проверяет на json
        if (files == null) {
            return new String[0];
        }

        String[] projects = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            String fileName = files[i].getName();
            projects[i] = fileName.substring(0, fileName.length() - 5); //Убираем .json из названия
        }
        return projects;
    } //Показывает существующие проекты

    //---------------Сетеры---------------
    public void setCurrentProject(Project project) {
        this.currentProject = project;
    }

    //-----------------Манипуляции с проектом
    public void saveProject() {
        if (currentProject == null) {
            return;
        }
        try(PrintWriter writer = new PrintWriter(new FileWriter(getProjectFilePath(currentProject.getProjectName())))) {
            String json = projectToJsonString(); // Строка в Json
            writer.print(json); // json в файл
        } catch (IOException e) {
            throw new RuntimeException("Ошибка сохранения проекта: " + e.getMessage(), e);
        }
    }

    public void loadProject(String projectName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(getProjectFilePath(projectName)))) {
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine())!= null) { // Читаем построково
                jsonBuilder.append(line);
            }
            this.currentProject = parseJsonToProject(jsonBuilder.toString());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки проекта: " + e.getMessage(), e);
        }
    }

    private String getProjectFilePath(String projectName) {
        return PROGECTS_DIR + File.separator + projectName + ".json"; //separator - разделитель папок
    } // Передает путь к файлу с названием

    private String projectToJsonString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");

        //--------------Название проекта---------------------
        sb.append("  \"projectName\": \"").append(escapeJson(currentProject.getProjectName())).append("\",\n");

        //----------------Персонажи---------------------
        sb.append("  \"characters\": [\n");
        for (int i = 0; i < currentProject.getCharacters().getSize(); i++) {
            Character character = currentProject.getCharacters().get(i);
            sb.append("    {\n");
            sb.append("      \"id\": ").append(character.getId()).append(",\n");
            sb.append("      \"name\": \"").append(escapeJson(character.getName())).append("\",\n");
            sb.append("      \"description\": \"").append(escapeJson(character.getDescription())).append("\"\n");
            sb.append("    }");
            if (i < currentProject.getCharacters().getSize() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("  ],\n");

        //-------------------------Предметы---------------------------
        sb.append("  \"items\": [\n");
        for (int i = 0; i < currentProject.getItems().getSize(); i++) {
            Item item = currentProject.getItems().get(i);
            sb.append("    {\n");
            sb.append("      \"id\": ").append(item.getId()).append(",\n");
            sb.append("      \"name\": \"").append(escapeJson(item.getName())).append("\",\n");
            sb.append("      \"description\": \"").append(escapeJson(item.getDescription())).append("\",\n");
            sb.append("      \"ownerId\": ").append(item.getOwnerId()).append("\n");
            sb.append("    }");
            if (i < currentProject.getItems().getSize() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("  ],\n");

        //------------------------Локации----------------------
        sb.append("  \"locations\": [\n");
        for (int i = 0; i < currentProject.getLocations().getSize(); i++) {
            Location location = currentProject.getLocations().get(i);
            sb.append("    {\n");
            sb.append("      \"id\": ").append(location.getId()).append(",\n");
            sb.append("      \"name\": \"").append(escapeJson(location.getName())).append("\",\n");
            sb.append("      \"description\": \"").append(escapeJson(location.getDescription())).append("\"\n");
            sb.append("    }");
            if (i < currentProject.getLocations().getSize() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("  ],\n");

        //------------------------Сюжеты------------------------------
        sb.append("  \"plots\": [\n");
        for (int i = 0; i < currentProject.getPlots().getSize(); i++) {
            Plot plot = currentProject.getPlots().get(i);
            sb.append("    {\n");
            sb.append("      \"id\": ").append(plot.getId()).append(",\n");
            sb.append("      \"name\": \"").append(escapeJson(plot.getName())).append("\",\n");
            sb.append("      \"description\": \"").append(escapeJson(plot.getDescription())).append("\"\n");
            sb.append("    }");
            if (i < currentProject.getPlots().getSize() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("  ]\n");
        sb.append("}");
        return sb.toString();
    }

    //---------------Парсинг-------------------
    private Project parseJsonToProject(String json) {
        String projectName = extractStringValue(json, "projectName");
        Project project = new Project(projectName);

        //Персонажи
        String charactersJson = extractArray(json, "characters");
        if (charactersJson != null) {
            String[] characterObjects = splitJsonObjects(charactersJson);
            for (String charJson : characterObjects) {
                if (!charJson.trim().isEmpty()) {
                    Character character = parseCharacter(charJson);
                    project.addCharacter(character);
                }
            }
        }

        //Предметы
        String itemsJson = extractArray(json, "items");
        if (itemsJson != null) {
            String[] itemObjects = splitJsonObjects(itemsJson);
            for (String itemJson : itemObjects) {
                if (!itemJson.trim().isEmpty()) {
                    Item item = parseItem(itemJson);
                    project.addItem(item);
                }
            }
        }

        //Локации
        String locationsJson = extractArray(json, "locations");
        if (locationsJson != null) {
            String[] locationObjects = splitJsonObjects(locationsJson);
            for (String locJson : locationObjects) {
                if (!locJson.trim().isEmpty()) {
                    Location location = parseLocation(locJson);
                    project.addLocation(location);
                }
            }
        }

        //Сюжеты
        String plotsJson = extractArray(json, "plots");
        if (plotsJson != null) {
            String[] plotObjects = splitJsonObjects(plotsJson);
            for (String plotJson : plotObjects) {
                if (!plotJson.trim().isEmpty()) {
                    Plot plot = parsePlot(plotJson);
                    project.addPlot(plot);
                }
            }
        }
        return project;
    }

    private Character parseCharacter(String json) {
        int id = extractIntValue(json, "id");
        String name = extractStringValue(json, "name");
        String description = extractStringValue(json, "description");
        return new Character(id, name, description);
    }

    private Item parseItem(String json) {
        int id = extractIntValue(json, "id");
        String name = extractStringValue(json, "name");
        String description = extractStringValue(json, "description");
        int ownerId = extractIntValue(json, "ownerId");
        return new Item(id, name, description, ownerId);
    }

    private Location parseLocation(String json) {
        int id = extractIntValue(json, "id");
        String name = extractStringValue(json, "name");
        String description = extractStringValue(json, "description");
        return new Location(id, name, description);
    }

    private Plot parsePlot(String json) {
        int id = extractIntValue(json, "id");
        String name = extractStringValue(json, "name");
        String description = extractStringValue(json, "description");
        return new Plot(id, name, description);
    }

    //-----------------Другие методы-------------------
    private boolean isWhitespace(char c) {
        return c == ' ' || c == '\t' || c == '\n' || c == '\r' || c == '\f';
    } // Проверяет на пробел

    private String escapeJson(String str) {
        if (str == null) {
            return "";
        } else {
            return str.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
        }
    } // Помогает избеэать ошибок в строках

    private String unescapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\\"", "\"")
                .replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t")
                .replace("\\\\", "\\");
    }

    private int extractIntValue(String json, String key) {
        String pattern = "\"" + key + "\":";
        int start = json.indexOf(pattern);
        if (start == -1) {
            return 0; // Если не найден ключ
        }
        start += pattern.length();
        int end = json.indexOf(",", start);
        if (end == -1) { //Нет запятой
            end = json.indexOf("}", start);
        }
        if (end == -1) { //Нет скобки (пустой файл )
            return 0;
        }
        String valueStr = json.substring(start, end).trim();
        try {
            return Integer.parseInt(valueStr); //Попытка преобразования
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String extractStringValue(String json, String key) {
        String pattern = "\"" + key + "\":\"";
        int start = json.indexOf(pattern);
        if (start == -1) {
            return "";
        }
        start += pattern.length();
        int end = json.indexOf("\"", start);
        if (end == -1) {
            return "";
        }
        String value = json.substring(start, end);
        return unescapeJson(value); // Возврат символов
    }

    private String extractArray(String json, String key) {
        String pattern = "\"" + key + "\":\\[";
        int start = json.indexOf(pattern);
        if (start == -1) {
            return null;
        }
        start += pattern.length() - 1; //Чтобы вырезать со скобкой

        int bracketCount = 1;
        int end = start;
        while (end < json.length() && bracketCount > 0) {
            end++;
            if (json.charAt(end) == '[') {
                bracketCount++;
            } else if (json.charAt(end) == ']') {
                bracketCount--;
            }
        }
        return json.substring(start, end + 1);
    }

    private String[] splitJsonObjects(String arrayJson) {
        String content = arrayJson.substring(1, arrayJson.length() - 1).trim(); // - скобки
        if (content.isEmpty()) {
            return new String[0];
        }

        ArrayList<String> objects = new ArrayList<>();
        int start = 0;
        int bracketCount = 0;

        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if (c == '{') {
                bracketCount++;
            } else if (c == '}') {
                bracketCount--;
            }

            if (bracketCount == 0 && c == '}') { //Объкект закрыт
                objects.add(content.substring(start, i + 1)); //Включая скобку
                start = i + 1;
                while (start < content.length() && (content.charAt(start) == ',' || isWhitespace(content.charAt(start)))) { //Пропуск пробелов и запятых
                    start++;
                }
                i = start - 1;
            }
        }
        return objects.toStringArray();
    }

    public boolean projectExists(String projectName) {
        File file = new File(getProjectFilePath(projectName));
        return file.exists();
    }

}
