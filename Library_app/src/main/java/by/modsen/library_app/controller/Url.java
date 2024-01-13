package by.modsen.library_app.controller;

public interface Url {

    String BOOK = "/book";

    interface Book {
        String PATH = "/library/api/books";

        String ID = "/{id}";
    }

    interface Library {
        String PATH = "/library/api";

        String AVAILABLE_BOOKS = "/availableBooks";
    }

    interface Auth {
        String PATH = "/library/api/auth";

        String REGISTER = "/register";

        String LOGIN = "/login";
    }
}
