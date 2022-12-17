#Sprint Retrospective I
## Project: Home Owners Association
## Group 18


###Task: Create a Public Notice Board
####Assignee: Patrik Varga
####User Stories / Issues: 
* As a member, I want to see the public notice board 
* Create the Public Notice Board Skeleton in the HOA microservice
* Plan internal architecture for PNB functionality
* Handle rest requests
* Save activities into a repository
* Access data from the repository
]

#### Notes:
* Over the course of the sprint, the Public Notice Board (which started out
as its own microservice) got merged into the HOA microservice 
* Created endpoints + skeleton functionality


###Task: Create a Voting Microservice 
#### Assignee: Simeon Atanasov 
####User Stories / Issues:
* Create a Voting Microservice
* Clone the example microservice and work on the copy
* Create endpoints for the voting 



#### Notes:
* Saving temporary results should be done, but how that is done is better discussed for the next sprint
* Implemented logic for elections, requirements TBD
* The HOA runs with the assumption that there is only one ongoing vote 
* Currently all votes are stored in a HashMap instead of the database 

###Task: Create a Requirements Microservice 
#### Assignee: Erik Vidican 
####User Stories / Issues:
* Create a Requirements Microservice
* As a member, I want to be able to report another member 


#### Notes:
Added the requirements microservice along with a basic reporting system. When HOA and Voting gets merged, the logic in this microservice will be properly finished in a new merge request (for eg. display only requirements associated to an HOA)
For now, we have:

(POST) createRequirement - Creates and saves a new requirement to repo.
(POST) report - Creates a new report by providing a username and a broken requirement id
(GET) getRequirements - Lists all requirements (this will be changed in the future to only show requirements of a specific board)
(GET) showAllReports - Lists all reports (this will also be changed like the previous one)


###Task: Create HOA Microservice
#### Assignee/s: Vasil Dakov, Steve Varadi
####User Stories / Issues:
* As a user, I am able to create my own HOA 
* As a user, I am able to join an HOA 
* As a user, I am able to leave an HOA
* As a developer, I want there to be an HOA microservice 

#### Notes:
* Created initial structure for HOA microsevice
* Refactored folder structure 
* Setup database relations 
* Created HOA endpoints, services and repositories
* Internal logic functions 
* What remains is elaborating on the logic and linking it to the other microservices + other endpoints 

###Task: Create an Authentication/Gateway Microservice 
#### Assignee: Gijs Hoedemaker 
####User Stories / Issues:
* As a user, I am able to login into the platform with credentials 
* As an admin, I want each user's credentials to be unique 
* As a user, I am able to register a new account with specific credentials 
* As a user, I should be notified when I am trying to register a new account with an 
already existing id 


#### Notes:
* Saving temporary results should be done, but how that is done is better discussed for the next sprint
* Implemented logic for elections, requirements TBD
* The HOA runs with the assumption that there is only one ongoing vote
* Currently all votes are stored in a HashMap instead of the database 
