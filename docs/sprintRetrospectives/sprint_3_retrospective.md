# Sprint 3 Retrospective

## Tasks

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


### Task: Finish Linking HOA and Voting Microservices as well as gateway (Simeon and Vasko)

- Issues regarding starting the vote from the gateway and casting votes from it
- Refactored many of the DTOs and the logic between the Voting, HOA and Gateway Microservices
- Extended voting with builder and strategy design patterns (Simeon)
- Separated Board Members more 

#### Notes
- Test coverage for voting microservice dropped a bit due to refactoring
- Some caveats had to be made for the prototype to be made on time (e.g it is assumed only
board members will be the one to vote)
- Much more scalable implementation overall adhering to the SEM design principles 

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

### Task: Creating manual testing manual for system-level testing with Postman - Patrik and Erik
- Created base document.
- Added instructions for using every endpoint on gateway related to the HOA and Requirements microservices.
- Did the actual testing as described, successfully!

#### Notes

- The manual for other endpoints will be completed by other group members.

### Task:  Creating a gateway to route requests to the proper microservice - Gijs 

- Created skeletons in gateway microservice as foundation for linking
- Cleaned up parts of the gateway code

#### Notes:
- The small cleanup ended up causing some merge conflicts, but we could resolve them with a reasonable amount of effort
- The routing functionality of the gateway is only tested manually with Postman, see the relevant document in `docs`.

## Main problems encountered

### Approaching deadline
On Thursday's meeting, we were faced with the painful fact that we still had an awful lot to do before we could call
the prototype done. Fortunately, in the comeback of the century, everyone joined forces, and we ended up getting an 
incredible amount of work done, with just 30 hours on the clock (which in the end got extended to 36, oh well). 
Multiple group members skipped some sleep on Thursday night, and it seems like we will actually manage to finish 
everything we have to finish. I (Patrik) am very proud of how this project turned out in the end, even if I still 
have frustrations about the format and even if there were some difficulties in Sprint 2.