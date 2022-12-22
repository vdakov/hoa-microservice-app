# Sprint 2 Retrospective

## Tasks

### Task: Test PNB functionality - Patrik Varga
 - Have at least 80% coverage on PNB-related functionality ✅
 - Feature unit tests ✅
 - Feature integration tests ✅

#### Notes:
 - I learned quite a bit about using MockMvc for testing REST API endpoints.
 - The classes that are actually used are now at 100% test coverage.
 - I also refactored my classes into the directory structure that the others introduced over the last two weeks.

### Task: Fix PMD violations - Patrik Varga
 - Have a pipeline that doesn't throw warnings ✅

#### Notes:
 - The (deprecated) dataflow anomaly rule can lead to less readable code, in these cases it is advised to use a @SuppressWarnings annotation.
 - Some of the other rules are actually quite insightful and made me learn about good coding practices.

### Task: Create "commons" module for shared classes - Patrik Varga
 - Create meta-files for the module (e.g. pipeline- and gradle-related files) ✅
 - Migrate existing shared classes to the module ✅
 - Manage dependencies between this and the other modules ✅


### Task: Add endpoint for creating HOAs - Steve Varadi
 - Most of functionality already in service ✅
 - Include tests ✅

#### Notes:
 - Issue is as advertised, small enough to implement, tests are not too complicated

### Task: Add endpoint for joining/leaving HOA - Steve Varadi
 - Figure out database representation of a user being in HOA
 - Think about users with multiple addresses, in multiple HOAs
 - Figure out infinitely recursive serialization issue

#### Notes
 - Issue turned out way larger than advertised
 - Functionality in service was not ready to support storing user addresses, and using an address to join 
 - Leaving was left to a seperate issue, as joining turned out huge 

### Task: Write endpoints to connect the gateway to other microservices (Gijs)
 - Bearer tokens are passed along between endpoints to stay authenticated
 - Requirements, PNB and voting routing endpoints are implemented
 
#### Notes
 - HOA routing functionality not yet in place because the functionality itself was not yet implemented.
 - The HTTP requests and bearer tokens took a significant amount of time to figure out (4+ hours)
 - No tests have been written yet.

### Task: Write endpoints and client requests to connect voting and HOA microservices together (Vasko) 
- Create DTOs to ensure smooth communication between the two without coupling the data
too tighthly together 
- Refactor the HOA microservice to work with those DTOs instead of the regular entities inside of the database
- Setup a client model for communication with other microservices
- Change overall structure of HOA microservice to adhere to new communication model

#### Notes
- Voting requests are not implemented yet, as that issue has been split between 
two people, where this is my side
- Resolving conflicts turned out to be way larger than expected
- There were many unexpected issue with Spring due to the annotations of the framework
in an attempt to separate the structure as much as possible
- TO-DO: Complete linking between microservices and finish up database
logic for how voting distributions are stored

### Task: Finishing the Requirements microservice - Erik Vidican

- Created routing methods to communicate between HOA and gateway microservices
- Refactored the directories of the microservice to properly separate logic and files / tests
- Removed authentication from requirements, since this is handled by the gateway
- Updated services, added the ability to modify and delete a requirement
- Changed retrieving requirements and reports: from now on, only specific requirements/reports are
retrieved, according to the HOA id
- Added unit and integration tests for domain, controller and services
- Moved lombok models to the commons folder


#### Notes
- I was late with merging this since I had to travel
- Figuring out how to test the controller proved to be difficult when the microservice
has to communicate with another microservice
- Improvements could be made regarding the efficiency of the microservice
- Regarding reports, there is still a TO-DO left checking if a provided user actually exists
or not

### Task: Linking the Voting microservice (unfinished)
#### Assignee: Simeon Atanasov 
#### User Stories / Issues:
* (related to) As a user, I want the result of a vote to take effect after the time has passed (#48)
* (related to) As a memeber, I am able to vote in the HOA's yearly elections (#35)
* (related to) As a board member, I want to start a vote on creating and changing requirements (#8)
* (related to) As a board member, I want to vote on creating and changing requirements (#9)


#### Notes:
* I did not manage to write enough code for any MRs during this sprint , and I have no merges.
* I was able to read documentation for both RestTemplate and 
* While reviewing MRs, I also saw how DTOs are exchanged. Therefore, linking the Voting microservice to HOA and Gateway/Security will be done more quickly during this sprint.
* After the end of a vote, the Voting microservice will send a POST request to Voting so as to transmit the collated results, and drop the votes currently stored within the database. This functionality is to be implemented in this sprint.


## Main problems encountered

### Lateness with merge requests:

We had the same problem as in Sprint 1: barely any functionality was actually done by the end of Saturday (which we agreed on), and by Monday, we still have people who have not submitted anything. Reviews and approvals are also lacking. We are unsure about solutions, as Sprint 3 is only 4 days long, and with the beginning of the winter break approaching, members of the team are less and less motivated to contribute. 
Since multiple members are not in the country, having physical meetings is also not really a solution. 
Our aim for the last sprint is to focus on merging our functionality (which is still dispersed in multiple branches), and on testing it.
We all aim to have clearer communication on the issues we are tackling and what we expect from others.

### Constant refactorings:
Due to being unsure on part of the implementations on how microservice
communicate with each other, some team members were stuck in an endless
loop of refactoring their code to fit with the overall structure of the project.
Our aim to fix this, again, is just clearer communication, especially in the fields where we rely on each other's code.
