package notifications;

import main.Start;

import java.util.ArrayList;

public final class Genre implements Subject {
    private final String genreName;
    private final ArrayList<Observer> observers;

    public Genre(final String genreName) {
        this.observers = new ArrayList<>();
        this.genreName = genreName;
    }

    @Override
    public void register(final Observer object) {
        if (!observers.contains(object)) {
            observers.add(object);
        }
    }

    @Override
    public void notifyObservers() {
        for (Observer object : observers) {
            if (!Start.getCurrentAction().getAddedMovie()
                    .getCountriesBanned().contains(Start.getCurrentUser()
                            .getCredentials().getCountry())) {

                object.updateAdd(Start.getCurrentAction().getAddedMovie().getName());
            }
        }
    }

    public String getGenreName() {
        return genreName;
    }
}
