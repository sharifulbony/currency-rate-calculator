# Sample currency calculator app
## Development decisions:
1. Initially calculation is done on stream api. later changed it to simple min max finding to be more performant
2. Webclient is used to allow non thread blocking request 
 so that in future application may be used for more 
  use cases without need to replace the main rest request component
3. custom exception is thrown . although implementation is more rudimentary but can be extended easily
4. A docker file also provided for quick containerization

Deployment

From local in CMD env
Run the MainMethod.class
if needed
* gradlew clean 
* gradlew build
* run the test cases if necessary

With Docker 
    docker-compose up -d


How much time did it take to complete this tasks?
Around three hours
Please evaluate the quality of this task from 1 to 10:
the task requires some very important aspect of a developers day to day life job.
So on that aspect I give it a solid 8 
Requirements and expectations were clear (1 - not clear at all, 10 - absolutely clear)
The expectation was clear without one point I noticed.
What was not clear?
Regardless of user input for the api param . if the given input currency is not a valid currency 
the api responses with all the currency available for the api to response. So in order to check the validation I have 
checked the currency against java default currency list as api giving resposes anyway regardless of right or wrong currency
this can be discussed further on how to handle
Requirements and expectations were relevant to the position you applied (1 - not relevant at all, 10 - absolutely relevant)
The expectations are relevant . so a solid 7 
How hard this task was for you from 1 to 10? (1 - very easy, 10 - very hard)
The task was pretty easy . A solid 3 or 4 from my side
