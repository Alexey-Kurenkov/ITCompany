package IT.dto.sorted_list;

import IT.exceptions.BacklogRangeException;
import IT.exceptions.DateSequenceException;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Сортированный список, с возможностью получения приоритетного элемента.
 * Хранит в себе сортированный список этих элементов.
 * Проектировался для хранения - бэклога или истории задач, рабочих дней и т.п.
 * Здесь же реализуем контроль максимально возможных элементов в списке, например программист работает только
 * над одной задачей, в то время как тим-лид может иметь в списке десяток задач, а менеджер проекта имеет
 * зачастую список с несколькими сотнями задач. В общем этот функционал по контролю пихаем сюда.
 * По это список с доп полями время-старт и время-окончания.
 * @param <T> - должен имплементировать интерфейс Comparable.
 */
public final class SortedList<T extends Comparable> {
    /**
     * тело бэклога - null быть не может - final
     */
    final SortedSet<ListItem<T>> list;
    /**
     * ограничитель на количество элементов в бэклоге
     */
    private int maxItems = Integer.MAX_VALUE;

    public SortedList(int maxItems) {
        setMaxItems(maxItems);
        list = new TreeSet<>();
    }
    /**
     * @param maxItems - 0 - бэклог не ограничен, иначе максимальное целое число возможных T в бэклоге
     */
    public void setMaxItems(int maxItems) {
        this.maxItems = maxItems >= 0 ? maxItems : Integer.MAX_VALUE;
    }

    /**
     * Положить элемент в сортированный список, перед этим:
     * проверка что последний элемент списка закрыт и дата открытия больше даты закрытия
     * проверка переполнения лимита списка
     * @param item
     * @throws BacklogRangeException
     */
    public SortedList add(T item, LocalDateTime startTime) throws BacklogRangeException {
        if (startTime == null) {
            startTime = LocalDateTime.now();
        }
        if (!list.isEmpty()) {
            if (list.first().isBusy()) {
                throw new BacklogRangeException();
            }
        }
        if (list.size() >= maxItems) {
            throw new BacklogRangeException();
        }

        if (list.stream().noneMatch(t -> t.equals(item))) {
            ListItem newItem = new ListItem(startTime, item);
            list.add(newItem);
        }
        return this;
    }

    /**
     * Удаляет верхний элемент из списка, и удаленный элемент возвращает.
     * @return
     * @throws BacklogRangeException
     */
    public ListItem<T> remove() throws BacklogRangeException {
        if (list.isEmpty()) {
            throw new BacklogRangeException();
        }

        ListItem<T> first = list.first();
        list.remove(first);
        return first;
    }

    /**
     * Возвращает верхний элемент из списка
     * @return
     * @throws BacklogRangeException
     */
    public ListItem<T> get() throws BacklogRangeException {
        if (list.isEmpty()) {
            throw new BacklogRangeException();
        }

        return list.first();
    }

    public void setFinish(LocalDateTime finishTime) throws DateSequenceException {
        if (finishTime == null) {
            finishTime = LocalDateTime.now();
        }

        list.first().setFinish(finishTime);
    }

    /**
     * @return - возвращает сколько еще можно добавить элементов в бэклог
     */
    public int getlistSize() {
        return maxItems - list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " {" + list + '}';
    }
}
