package ru.baib.input;

public interface Input {
    String askStr(String question);
    int askInt(String question);
    int askInt(String question, int max);
}