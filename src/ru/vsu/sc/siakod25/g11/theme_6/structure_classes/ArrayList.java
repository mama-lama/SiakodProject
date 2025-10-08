package ru.vsu.sc.siakod25.g11.theme_6.structure_classes;

public class ArrayList<E> {
    private E[] data;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    //-------Конструкторы-------
    public ArrayList() {
        data = (E[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }
    public ArrayList(int capacity){
        if (capacity < 0) throw new IllegalArgumentException
                ("Размер списка должен быть не меньше 0");
        data = (E[]) new Object[capacity];
        size = 0;
    }

    //---------Гетеры----------------
    public int getSize () {
        return size;
    }

    public E get(int index) {
        indexCheck(index);
        return data[index];
    }

    //---------Сетеры----------------
    public E set(int index, E element) {
        indexCheck(index);
        E old = data[index];
        data[index] = element;
        return old;
    }

    //---------Другие методы----------------
    public boolean isEmpty() {
        return size == 0;
    }

    public void add(E element) {
        capacityCheck(size + 1);
        data[size++] = element;
    }

    public void add(int index, E element) {
        indexForAddCheck(index);
        capacityCheck(size + 1);
        System.arraycopy(data, index, data,
                index + 1, size - index);
        data[index] = element;
        size++;
    }

    public E remove(int index) {
        indexCheck(index);
        E removed = data[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(data, index + 1, data, index, numMoved);
        }
        data[size--] = null;
        return removed;
    }

    public void clear() {
        data = (E[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public boolean contains(Object o){
        return indexOf(o) >= 0;
    }

    public E[] toArray(){
        E[] result = (E[]) new Object[size];
        for (int i = 0; i < size; i++) {
            result[i] = data[i];
        }
        return result;
    }
    //---------Вспомогательные методы---------------
    public int indexOf(Object o){
        for (int i = 0; i < size; i++) {
            if (o == null ? data[i] == null : o.equals(data[i])) {
                return i;
            }
        }
        return -1;
    }

    private E[] copyOf(E[] source, int newLength) {
        E[] newArray = (E[]) new Object[newLength];
        int len = Math.min(source.length, newLength);
        for (int i = 0; i < len; i++) {
            newArray[i] = source[i];
        }
        return newArray;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public String[] toStringArray() {
        String[] result = new String[size];
        for (int i = 0; i < size; i++) {
            result[i] = String.valueOf(data[i]);
        }
        return result;
    }

    public String toString1(){
        StringBuilder sb = new StringBuilder("----\n");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
        }
        sb.append("----");
        return sb.toString();
    }

    private void capacityCheck(int minCapacity){
        if (minCapacity > data.length) {
            int newCapacity = Math.max(data.length * 2, minCapacity);
            data = copyOf(data, newCapacity);
        }
    }
    //--------------ОШИБКИ---------------
    private void indexCheck(int index){
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException
                    ("Индекс: " + index + ", размер: " + size + "(Ошибка!)");
        }
    }

    private void indexForAddCheck(int index){
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException
                    ("Индекс: " + index + ", размер: " + size + "(Ошибка!)");
        }
    }
}
