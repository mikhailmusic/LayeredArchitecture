package inventory.domain;

public enum TemperatureMode {
    FROZEN("Замороженный"),
    REFRIGERATED("Охлажденный"),
    ROOM_TEMPERATURE("При комнатной температуре");

    private final String description;

    TemperatureMode(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
