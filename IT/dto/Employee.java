package IT.dto;

import IT.dto.sorted_list.SortedList;
import IT.dto.interfaces.RatingCalculate;
import IT.exceptions.BacklogRangeException;
import IT.exceptions.DateSequenceException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class Employee implements Comparable {
    /**
     * уникальный целочисленный сквозной идентификатор
     */
    private static int autoIncrement = 0;
    private int id;
    /**
     * текущий бэклог сотрудника
     */
    final SortedList<Task> backlog;
    /**
     * история рабочих дней сотрудника
     */
    final SortedList<WorkDay> histroryWorkDay;
    /**
     * ФИО
     */
    protected String name;
    /**
     * алгоритм вычисления рейтинга - может быть null, тогда вместо рейтинга вернет бонус
     */
    protected RatingCalculate bonusCalculate;
    /**
     * текущие накопленные бонусы
     */
    protected int bonuses;

    public Employee(String name, int maxTasks, RatingCalculate calculate) {
        this.name = name;
        this.bonusCalculate = calculate;
        backlog = new SortedList<>(maxTasks);
        histroryWorkDay = new SortedList<>(-1);
        id = ++autoIncrement;
    }

    public final int getId() {
        return id;
    }

    void addBonus(int bonuses) {
        this.bonuses += bonuses;
    }
    double getBonus() {
        if (bonusCalculate == null) {
            return bonuses;
        }
        return bonusCalculate.calculate();
    }
    void clearBonus() {
        bonuses = 0;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " { id = " + id + ", name = '" + name + "'}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Employee)) return false;
        return ((Employee) obj).getId() == getId();
    }

    public void addNewTask(Task task) throws BacklogRangeException {
        backlog.add(task, LocalDateTime.now());
    }
    final double getRating() {
        if (bonusCalculate == null)
            return bonuses;

        return bonusCalculate.calculate();
    }
    /**
     * у кого больше места в бэклоге тому и задача
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Object o) {
        Developer t = (Developer) o;
        return this.backlog.getlistSize() - t.backlog.getlistSize();
    }

    void doWork(LocalDate date) {
        LocalDateTime start = LocalDateTime.of(date, LocalTime.of(10, 0));
        LocalDateTime finish = LocalDateTime.of(date, LocalTime.of(19, 0));
        try {
            System.out.println(this + " начал рабочий день " + date + " в " + start);
            histroryWorkDay.add(new WorkDay(date), start);
            doWorkDay(date);
            histroryWorkDay.get().setFinish(finish);
            System.out.println(this + " закончил рабочий день " + date + " в " + start);
        } catch (BacklogRangeException | DateSequenceException e) {}
    }
    /**
     * метод который необходимо переопределить в предках, должен исполнять какую то работу:
     * выполнять задачу из бэклога, или делегировать задачу из бэклога. Как бы то ни было делегирование тоже работа
     * просто быстрее по времени.
     */
    abstract void doWorkDay(LocalDate date);

    /**
     * Дневной бонус за выход на работу - программист 3, тим Лид 2, менеджер 1
     * проще переопределить в наследниках и возвращать константу, а не хранить в каждом экземпляре
     * класса одинаковые числа.
     * @return
     */
    abstract int getDailyBonus();
}
