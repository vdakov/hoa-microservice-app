### Task: Notify the users about events - Erik Vidican

- Issue regarding notifying users about a creation/change of requirements has been added
- Created a controller in the gateway for processing a notification (and then saving it to JPA),
creating a notification and finally marking a notification as read
- Made the notifications easily expandable by using inheritance from specific classes
in the commons folder, so other microservices can implement their own notifications based on existing
functionality
- Created routing methods between the HOA (retrieve memebers of an HOA) and gateway (process
notifications)

#### Notes
- Testing for this is not entirely done since I will have to travel on friday again and the deadline is
approaching, I will try to do my best
- Storing a notification only works if the superclass is _not_ abstract. I would still like
to make it abstract, so I will have to figure out how to solve this during the refactoring process

### Task: Finish functionality of joining/leaving HOAs - Steve Varadi
- make functionality for leaving
- plug into gateway
- write tests for everything

#### Notes
- Less friction in leaving than expected
    - Existence of connection table means I don't have to worry about cascading
- Falling ill severely dropped productivity
- managed to plug it in to gateway relatively easily
- some tests came out little mindless to achieve high coverage (aka basically just testing that mocking works)

### Task: Linking PNB functionality with the gateway - Patrik Varga

- One endpoint for creating activities, one for getting them by HOA ID, and one for getting all relevant 
activities for the current user.
- Filtering by user was new functionality on the HOA microservice side too, implemented and tested that
(with Steve's help for custom queries).

### Task: Linking creating an HOA to the gateway - Patrik Varga

- Made endpoint on gateway that forwards the HTTP request to HOA.

#### Notes

- The actual functionality was implemented by Steve.

### Task: Creating manual testing manual for system-level testing with Postman
- Created base document.
- Added instructions for using every endpoint on gateway related to the HOA microservice.
- Did the actual testing as described, successfully!

#### Notes

- The manual for other endpoints will be completed by other group members. 