# city-events
Java program for visualizing social events and managing them by confirming presence, creating new events, and more...

## Usage
From any IDE, run the program and follow the commands in the screen

## Functionality
### Authenticantion
There's a simple authentication using a code (CPF) for each user.

### User Registering
Some information is asked for the user, then, when the program ends, this informations is saved on a file named ***events.data*** for further authentication.

### Managing Events
Three lists of events are shown:
 1. Past Events
 2. Events Happening
 3. Events To Happen

Only the current time decides where a registered event is placed.

Then the user can either confirm presence in an event or cancel presence.

### Creating Event
Almost all event information is given by the user. Only an unique ID is automatically signed to it.
This ID is controlled by the **FileModel** (the object representation of the information in the ***events.data*** file)

## Code Overview
### File Reading
Information is read using the ***events.data*** file.
Each tipe of data is preceded by an identificator.
 1. newUser - For reading users
 2. newEvent - For reading events
 3. newId - For reading the next ID to be added in an event created

When a type of data is read, an model is created using the informations in each line of it.
This models are so added on the **FileModel** that is the representation of the read file.

### Authentication
The **FileModel** holds a list of all CPF's registered, so authentication verifies if the CPF given by the user is in the list.

### User Registering
After the user type all the informations, a new model **User** is added to the **FileModel** and the corresponding CPF is added on *registeredCPFs* list.

### Confirm Presence
When the user confirm presence at an event, the event's ID is added to the user's *confirmedEvents* list.
Also, the user CPF is added to the event's *confirmedCPFs* list.

### Cancel Presence
When the user cancel presence at an event, the contrary proccess of the confirmation is executed.

### Create Event
After the user type all the event's information, a new model **Event** is added to the **FileModel** and then sort all events by date.

### File Writing
Before finishing the program, the ***events.data*** file is cleaned and written using the **FileModel**.
 1. Write Users in *userList* preceded by "newUser" lines
 2. Write Events on each corresponding list preceded by "newEvent" lines
 3. Write next ID to be added in an event precede by "newId" line

## Credits
The inspiration for this program was given by an IBMR university course.



