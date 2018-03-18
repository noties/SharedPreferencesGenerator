package ru.noties.spg.processor.data;

public class DefItem {

    public final String value;
    public final boolean isEvaluation;

    DefItem(String value, boolean isEvaluation) {
        this.value = value;
        this.isEvaluation = isEvaluation;
    }

    @Override
    public String toString() {
        return "DefItem{" +
                "value='" + value + '\'' +
                ", isEvaluation=" + isEvaluation +
                '}';
    }
}
