# Monday meeting 4

- Date: 12-12-2022 10:15
- Place: Erik (Simeon was interim secretary for this meeting)
- Chair: Steve
- Secretary: TBD

## Agenda

1. Stand-up
2. Choosing secretary
3. Sprint retro
4. Sprint planning and refinement


## Sprint retrospective

**everyone fills their own issues in the Sprint retrospective document**

## Sprint planning
- Sprint 2 will be focused on linking everything together and testing
- HOA will store permission levels
- user requests the gateway/security microservice for user info; gets the username, and that is passed along
- we are **not** merging Requirements into HOA, but storing the list of requirements into the Requirements microservice
- Requirements microservice stores HOA id and the list of requirements and reports
- Software Overengineering Methods
- DTOs will be split into a `commons` directory
- the Voting microservice sends a request with the vote results to the respective microservice (async programming in Voting?)
- **MRs this week need improved test coverage**
- have some test coverage by Wednesday 14/12/2022
- Vasko will make a MD template for the sprint retrospective
- no new functionality after Saturday

## Assignment 1 - Architecture draft
- Vasko will write down the changes in the architecture
- Figure out design patterns on Thursday
- Everyone will write new things in the shared Google doc
- Change the diagrams