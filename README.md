Copyright 2022 Dragusin Daniela-Alexandra (321 CA 2022/2023)

# POO PROJECT - POO TV

## General description
I have implemented a platform for viewing movies and series.
The platform enables direct interaction with users through various
actions such as register, login, logout, search, view movie, rate
movie, etc.


## File structure
                                                src
                                                ├── commands
                                                │   ├── BackCommand.java
                                                │   ├── ChangePageCommand.java
                                                │   ├── Command.java
                                                │   ├── CommandFactory.java
                                                │   ├── DatabaseCommand.java
                                                │   ├── Invoker.java
                                                │   ├── OnpageCommand.java
                                                │   └── Receiver.java
                                                ├── input
                                                │   ├── Action.java
                                                │   ├── Contain.java
                                                │   ├── Credentials.java
                                                │   ├── Filter.java
                                                │   ├── Input.java
                                                │   ├── Movie.java
                                                │   ├── Sort.java
                                                │   └── User.java
                                                ├── main
                                                │   ├── Output.java
                                                │   └── Start.java
                                                ├── notifications
                                                │   ├── Genre.java
                                                │   ├── MovieDatabase.java
                                                │   ├── Notification.java
                                                │   ├── Observer.java
                                                │   └── Subject.java
                                                ├── pages
                                                │   ├── LoginPage.java
                                                │   ├── LogoutPage.java
                                                │   ├── MoviesPage.java
                                                │   ├── Page.java
                                                │   ├── PageFactory.java
                                                │   ├── RegisterPage.java
                                                │   ├── SeeDetailsPage.java
                                                │   └── UpgradesPage.java
                                                ├── Main.java
                                                └── Test.java
                                                README.md


## Implementation

### Input package
    The input package contains the classes used to extract the information from the input files. 

### Pages package
    The pages package contains an interface named Page that contains two methods named doAction and
    changePage. This class is implemented by all other pages available on the platform: Login, Logout,
    Movies, Register, SeeDetails, Upgrades.
    
    The pages are created using Factory Design Pattern. In this way I created PageFactory class
    that creates instances of Page objects based on a provided string parameter who describes the
    type of page.
    
    These page classes override the doAction method depending on their specific actions and the changePage
    method to change the page on the current page.

### Commands package
    The commands package contains all the commands that a user can make on the movies platform. The
    commands are created as classes: ChangePageCommand, OnpageCommand, BackCommand, DatabaseCommand that
    inherit the Command abstract class. 

    The commands are created using Factory Design Pattern. I created CommandFactory class
    that creates instances of Command objects based on a provided string parameter who describes the
    type of command.

    The implementation of these Command classes contain the call to the Receiver class.

    The Receiver class is the class on which the call is made and contains the actual implementation
    of the actions that want to be executed (change page, back, etc.).

    The Invoker class calls the action execution method on the specified Command object.

    I used the Command Design Pattern.

    
### Main package
    The main package contains Start class defined as a Singleton. Start represents the starting point of
    the execution of the actions. It performs the actions according to their type by creating the specific
    command to the action and creating the invoker to execute it.
    
    Output class is responsible for adding the specific result to each action in the output array node
    that is created in the Main class. This class has methods for creating different nodes that contains
    details about a movie, user, notification, etc.
    
    The Main class extracts the information from the files, iterates the actions to be executed and writes
    the information from the main array node to the main file.

### Notifications package
    The notifications package contains the classes used to implement the Observer Design Pattern : Subject
    and Observer and other classes.

    MovieDatabase class represents the actual implementation of adding and deleting movies from
    the movie list.

    The Subject interface contains methods for adding new observers and for notifying
    them of changes that occur in the movie database.

    The Observer interface provides two methods : update when a movie is added and update when a movie
    is deleted. This methods can be invoked by the Subject to notify a change.

    The observers are represented by the users of the film platform. Thus, the User class implements the
    Observer interface and overrides the two methods of it by adding notifications about deletion or addition to its
    notification list.

    The subjects are represented by films and genres.

    I created a class called Genre, which implements the Subject interface. Genre contains the name of
    the respective genre and a list of its observers. When a new movie is added to the database, the
    notifyObservers method of the Genre class notifies the observers who are subscribed to one of the
    genres of the movie.

    The Movie class in the input package also implements the Subject interface. This notifies observers
    when a movie that users have bought is deleted.


