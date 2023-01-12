package input;

import notifications.Observer;
import notifications.Subject;

import java.util.ArrayList;

public final class Movie implements Subject {
    private String name;
    private String year;
    private int duration;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;
    private final ArrayList<Integer> usersRatings = new ArrayList<>();
    private int numLikes;
    private double rating = 0.00;
    private int numRatings;
    private ArrayList<Observer> observers = new ArrayList<>();

    public Movie() {

    }

    public Movie(final Movie movie) {
        this.name = movie.getName();
        this.year = movie.getYear();
        this.duration = movie.getDuration();
        this.genres = movie.getGenres();
        this.actors = movie.getActors();
        this.countriesBanned = movie.getCountriesBanned();
        this.numLikes = 0;
        this.rating = 0.00;
        this.numRatings = 0;
        this.observers = new ArrayList<>();
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
            object.updateDelete(name);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(final String year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    public void setCountriesBanned(final ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(final int numLikes) {
        this.numLikes = numLikes;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(final double rating) {
        this.rating = rating;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(final int numRatings) {
        this.numRatings = numRatings;
    }

    public ArrayList<Integer> getRatings() {
        return usersRatings;
    }

    public ArrayList<Observer> getObservers() {
        return observers;
    }
}
