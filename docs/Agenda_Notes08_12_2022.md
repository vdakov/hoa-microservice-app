#Thursday meeting 3

- Date: 01-12-2022 10:15
- Place: Drebbelweg
- Chair: Patrik
- Secretary: Steve

## Agenda

1. Stand-up
2. Sergey's points
3. Discussion on the architecture draft
    * Choice of microservices as an architecture
    * Component diagram in multiple pieces
    * Database options
    * Voting microservice
4. Technical questions
    * Pipeline issues
    * Number of classes
5. Wrap-up: distributing tasks until next Thursday

## Notes

### Stand-up
- goofy ahh stand-up

### Sergey's points

- everyone happy with distribution

- testing not yet started, going to be next step

- more merges, more code, more contribution!

### Draft

- do we have to explain microservices?
    - explain why we have exactly the chosen ones

- database
    - it's fine, just discuss it and argue for it in the report

- voting
    - Vasco's explanation:
        - not core functionality
        - reusability (for other possible future voting types)

- diagram
    - multiple diagrams?
        - we HAVE to do the subsystems

- meeting next week
    - yes

### Technical

- pipeline issues resolved

- classes. classes everywhere

- diagrams (again)

### Wrap-up

- deadline for assignment 1

- continuation meeting

- our design pattern plans are good!


### Post-meeting-meeting

- PNB has been merged into HOA
    - pros:
        - PNB was just a database access without access to database
    - cons:
        bigger HOA
    - decision: MERGED

- gateway/security
    - email as username, chosen display name (full name)

- checkstyle