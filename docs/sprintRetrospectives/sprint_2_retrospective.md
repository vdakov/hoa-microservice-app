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

## Main problems encountered

### Lateness with merge requests

We had the same problem as in Sprint 1: barely any functionality was actually done by the end of Saturday (which we agreed on), and by Monday, we still have people who have not submitted anything. Reviews and approvals are also lacking. We are unsure about solutions, as Sprint 3 is only 4 days long, and with the beginning of the winter break approaching, members of the team are less and less motivated to contribute.
