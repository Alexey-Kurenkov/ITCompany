package IT.dto;

import IT.dto.interfaces.RatingCalculate;
import IT.dto.sorted_list.SortedList;
import IT.exceptions.BacklogRangeException;

import java.time.LocalDate;

public class Manager extends Employee {
    final private static int dailyBonus = 1;
    final private SortedList<Employee> executors;

    public Manager(String name, int maxTasks, SortedList<Employee> executors) {
        super(name, maxTasks, new RatingCalculate() {
            @Override
            public double calculate() {
                return 0;
            }
        });
        this.executors = new SortedList<>(-1);
    }

    @Override
    void doWorkDay(LocalDate date) {
        try {
            while (!backlog.isEmpty() && !executors.isEmpty()) {
                Employee employee = executors.get().get();
                Task task = backlog.get().get();
                employee.addNewTask(task);
            }
        } catch (BacklogRangeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    int getDailyBonus() {
        return dailyBonus;
    }
}
