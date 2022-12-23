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