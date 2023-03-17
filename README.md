# Learning progress Tracker

A Java console application that simulates a database for storing information about students and their progress in courses.
The user can add points to students in various courses and track their progress, after collecting the maximum number of points, send them an e-mail informing them that the course has been completed. 
The application also performs some statistics to obtain information, e.g. about the popularity of the course



# Technologies Used
- Java


Example session of application:

```
Learning Progress Tracker
> add students
Enter student credentials or 'back' to return:
> Kamil Babinski babinskikamil8@gmail.com
The student has been added.
> Jacek Kowalski kowal324@wp.pl
The student has been added.
> Cheater jakis kowal324@wp.pl
This email is already taken.
> back
Total 2 students have been added.
> list
Students:
10000
10001
> find
Enter an id or 'back' to return
> 10000
10000 points: Java=0; DSA=0; Databases=0; Spring=0
> back
> add points
Enter an id and points or 'back' to return:
10000 50 50 50 50
Points updated
> back
> find
Enter an id or 'back' to return
> 10000
10000 points: Java=50; DSA=50; Databases=50; Spring=50
> back
>
No input.
> add poins
Error: unknown command!
> add points
Enter an id and points or 'back' to return:
> 10001
Incorrect points format.
> 10001 25 25 25 25
Points updated
> back
> add students
Enter student credentials or 'back' to return:
> Waclaw Prosty Waclawek2009@cringe.com
The student has been added.
> back
Total 1 students have been added.
> list
Students:
10000
10001
10002
> add points
Enter an id and points or 'back' to return:
> 10002 100 100 100 100
Points updated
> back
> statistics
Type the name of a course to see details or 'back' to quit
Most popular: Java, Databases, DSA, Spring
Least popular: n/a
Highest activity: Java, Databases, DSA, Spring
Lowest activity: n/a
Easiest course: Java, Databases, DSA, Spring
Hardest course: n/a
> back
> add students
Enter student credentials or 'back' to return:
> Will Smith Smith@gmail.com
The student has been added.
> back
Total 1 students have been added.
> add points
Enter an id and points or 'back' to return:
> 10003 10 0 0 0
Points updated
> back
> statistics
Type the name of a course to see details or 'back' to quit
Most popular: Java
Least popular: Databases, DSA, Spring
Highest activity: Java
Lowest activity: Databases, DSA, Spring
Easiest course: Databases, DSA, Spring
Hardest course: Java
> Java
Java
id    points    completed
10002 100        16,7%
10000 50         8,3%
10001 25         4,2%
10003 10         1,7%
> DSA
Dsa
id    points    completed
10002 100        25,0%
10000 50         12,5%
10001 25         6,3%
> Databases
Databases
id    points    completed
10002 100        20,8%
10000 50         10,4%
10001 25         5,2%
> Spring
Spring
id    points    completed
10002 100        18,2%
10000 50         9,1%
10001 25         4,5%
> back
> find
Enter an id or 'back' to return
> 10000
10000 points: Java=50; DSA=50; Databases=50; Spring=50
> back
> add points
Enter an id and points or 'back' to return:
> 10000 550 25 25 25
Points updated
> back
> notify
To: babinskikamil8@gmail.com
Re: Your learning Progress
Hello, Kamil Babinski! You have accomplished our Java course.
Total 1 students have been notified.
> exit
Bye!
