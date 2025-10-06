package ru.vsu.sc.siakod25.g11.theme_6.structure_classes;

<<<<<<< HEAD
import java.util.Comparator;

public class PriorityQueue<T> {
    private int size;
    private final static int DEFAULT_CAPACITY = 10;
    private T[] heap;
    private final Comparator<? super T> comparator;

    //-------Конструкторы-------
    public PriorityQueue() {
        this.heap = (T[]) new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.comparator = null;
    }

    public PriorityQueue(Comparator<? super T> comparator) {
        this.heap = (T[]) new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.comparator = comparator;
    }

    public PriorityQueue(int capacity, Comparator<? super T> comparator) {
        this.heap = (T[]) new Object[capacity];
        this.size = 0;
        this.comparator = comparator;
    }

    //-------Публичные методы-------
    public void add(T element) {
        resize();
        heap[size] = element;
        siftUp(size); // Просеивание
        size++;
    }

    public T poll() {
        isEmptyCheck();
        T result = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        siftDown(0);

        return result;
    }

    public T peak() {
        isEmptyCheck();
        return heap[0];
    }

    public void clear() {
        heap = (T[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }
=======
public class PriorityQueue {
    private int size;

    public PriorityQueue(){}
>>>>>>> 20a6026926ccbdf02ae25791d833257a2053c0a1

    public int size(){
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

<<<<<<< HEAD
    //------------Приватные методы----------
    private void resize() {
        if (size == heap.length) {
            T[] newHeap = (T[]) new Object[heap.length * 2];
            System.arraycopy(heap, 0, newHeap, 0, heap.length);
            heap = newHeap;
        }
    }

    private void siftUp(int index) {
        int parent = (index - 1) / 2; // Поиск родителя по формуле
        while (index > 0 && compare(heap[index], heap[parent]) < 0) {
            swap(index, parent);
            index = parent;
            parent = (index - 1) / 2;
        }
    } // Используется при добавлении

    private void siftDown(int index) {
        int rightChild = index * 2 + 2;
        int leftChild = index * 2 + 1;
        int smallest = index;

        if (rightChild < size && compare(heap[rightChild], heap[smallest]) < 0) {
            smallest = rightChild;
        }
        if (leftChild < size && compare(heap[leftChild], heap[smallest]) < 0) {
            smallest = leftChild;
        }
        if (smallest != index) {
            swap(index, smallest);
            siftDown(smallest); //Рекурсия
        }
    } // Используется при удалении корня

    private void swap(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private int compare(T a, T b) {
        if (comparator != null) {
            return comparator.compare(a, b);
        } else { //Естественный порядок
            return ((Comparable<? super T>) a).compareTo(b);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(heap[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    //----------Ошибки----------
    private void isEmptyCheck() {
        if (isEmpty()){
            throw new IllegalStateException("Ошибка! Очередь пустая!");
        }
    }

    //------------Main----------
    public static void main(String[] args) {
        PriorityQueue pq = new PriorityQueue(2, Comparator.naturalOrder());
        pq.add(1);
        pq.add(2);
        pq.add(3);
        System.out.println(pq.toString());
        System.out.println(pq.size());
    }
=======

>>>>>>> 20a6026926ccbdf02ae25791d833257a2053c0a1
}
