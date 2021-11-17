package ru.academits.maksimenko.model;

public class ErrorInfo {
    private final String message;

    public ErrorInfo(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}