package IT.dto.interfaces;

/**
 * Функциональный интерфейс, для реализации подсчёта рейтинга, в зависимости от полей класса внутри
 * которого осуществлена реализация этого интерфейса
 */
@FunctionalInterface
public interface RatingCalculate {
    double calculate();
}
