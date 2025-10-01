package ru.vsu.sc.siakod25.g11.theme_6.unit_classes;

public class Relationship {
    private int level;
    private Character thisCharacter;
    private Character otherCharacter;

    //-------Конструкторы-------
    public Relationship(Character thisCharacter, Character otherCharacter) {
        this.thisCharacter = thisCharacter;
        this.otherCharacter = otherCharacter;
        this.level = 0;
    }
    public Relationship(Character thisCharacter, Character otherCharacter, int level) {
        this.thisCharacter = thisCharacter;
        this.otherCharacter = otherCharacter;
        levelCheck(level);
        this.level = level;
    }

    //---------Гетеры----------------
    public int getLevel() {
        return level;
    }
    public Character getThisCharacter() {
        return thisCharacter;
    }
    public Character getOtherCharacter() {
        return otherCharacter;
    }

    //---------Сетеры----------------
    public void setLevel(int level) {
        levelCheck(level);
        this.level = level;
    }
    public void setLevel() {
        this.level = 0;
    }

    public int plus(int plusLevel) {
        int newLevel = level + plusLevel;
        levelCheck(newLevel);
        this.level = newLevel;
        return newLevel;
    }

    public int minus(int minusLevel) {
        int newLevel = level - minusLevel;
        levelCheck(newLevel);
        this.level = newLevel;
        return newLevel;
    }

    public String toString() {
        return "Отношения между " + thisCharacter.getName() + " и " + otherCharacter.getName()
                + ": " + level;
    }

    //-------Ошибки-------
    private void levelCheck(int index) {
        if (index < -100 || index > 100) {
            throw new IllegalArgumentException("Уровень отношений за пределами ранга" + index);
        }
    }
}
