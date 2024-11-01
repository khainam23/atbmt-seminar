package model;

public enum Alphabet {
    ENGLISH("abcdefghijklmnopqrstuvwxyz"),
    VIETNAM("aăâbcdđeêghiklmnôơpqrstuưvy");
    private final String description;

    Alphabet(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
