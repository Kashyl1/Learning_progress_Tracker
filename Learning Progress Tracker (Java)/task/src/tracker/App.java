package tracker;
import java.util.*;

public class App {
    Scanner scanner = new Scanner(System.in);
    Set<Student> studentSet = new HashSet<>();
    int id = 10000;
    Map<String, int[]> courseStatistics;
    private final Set<Integer> notifiedStudentIds = new HashSet<>();


    public App() {
        courseStatistics = new HashMap<>();
        courseStatistics.put("Java", new int[]{0, 0, 0});
        courseStatistics.put("Spring", new int[]{0, 0, 0});
        courseStatistics.put("Databases", new int[]{0, 0, 0});
        courseStatistics.put("DSA", new int[]{0, 0, 0});

        System.out.println("Learning Progress Tracker");
        String input = scanner.nextLine();
        Commands commands = Commands.get(input);
        while (commands != Commands.EXIT) {

            switch (commands) {
                case ADD_STUDENTS -> {
                    System.out.println(commands.getMessage());
                    addStudent();
                }
                case LIST -> {
                    if (studentSet.isEmpty()) {
                        System.out.println("No students found.");
                    } else {
                        System.out.println(commands.getMessage());
                        studentSet.stream()
                                .sorted(Comparator.comparing(Student::getId))
                                .forEach(student -> System.out.println(student.getId()));
                    }
                }
                case ADD_POINTS -> {
                    System.out.println(commands.getMessage());
                    addPoints();
                }
                case FIND -> {
                    System.out.println(commands.getMessage());
                    findStudent();
                }
                case STATISTICS -> {
                    System.out.println(commands.getMessage());
                    statistics();
                }
                case NOTIFY -> sendMailToStudents();
                case UNKNOWNCOMMAND, EMPTY -> System.out.println(commands.getMessage());
                case BACK -> System.out.println("Enter 'exit' to exit the program.");
            }
            input = scanner.nextLine();
            commands = Commands.get(input);
        }
        System.out.println(commands.getMessage());
    }

    /**
     *  Add student
     */
    public void addStudent() {
        int students = 0;
        String input = scanner.nextLine();
        Commands commands = Commands.get(input);
        while (commands != Commands.BACK) {
            students += validateInput(input);
            input = scanner.nextLine();
            commands = Commands.get(input);
        }
            System.out.println("Total " + students + " students have been added.");
    }

    /**
     * add points
     */
    public void addPoints() {
        String input = scanner.nextLine();
        Commands commands = Commands.get(input);
        while (commands != Commands.BACK) {
            validatePoints(input);
            input = scanner.nextLine();
            commands = Commands.get(input);
        }
    }

    /**
     * Find student by ID
     */
    public void findStudent() {
        String input = scanner.nextLine();
        Commands commands = Commands.get(input);
        while (commands != Commands.BACK) {
            try {
                int id = Integer.parseInt(input);
                studentSet.stream()
                        .filter(student -> student.getId() == id)
                        .findFirst()
                        .ifPresent(student ->
                        System.out.println(student.toString()));
            } catch (Exception e) {
                System.out.println("No student is found for id=" + input + ".");
            }
            input = scanner.nextLine();
            commands = Commands.get(input);
        }
    }

    /**
     * Check if points are correct
     * @param input User input
     */
    public void validatePoints(String input) {
        String[] parts = input.trim().split("\\s+");
        if (parts.length != 5) {
            System.out.println(ERROR.INVALID_POINTS.getMessage());
            return;
        }
        if (!isValidNumber(parts[0])) {
            System.out.println("No student is found for id=" + parts[0] + ".");
            return;
        }

        try {
            int id = Integer.parseInt(parts[0]);
            if (!studentWithIdExists(id)) {
                System.out.println("No student is found for id=" + id + ".");
                return;
            }
                   //POPRAW
            int[] points = new int[4];
            for (int i = 1; i < 5; i++) {
                points[i - 1] = Integer.parseInt(parts[i]);
                if (points[i - 1] < 0) {
                    System.out.println(ERROR.INVALID_POINTS.getMessage());
                    return;
                }
            }

            studentSet.stream()
                    .filter(student -> student.getId() == id)
                    .findFirst()
                    .ifPresent(student -> {
                        int currentJavaPoints = student.getJava();
                        int currentDsaPoints = student.getDsa();
                        int currentDbPoints = student.getDb();
                        int currentSpringPoints = student.getSpring();

                        student.setJava(student.getJava() + points[0]);
                        student.setDsa(student.getDsa() + points[1]);
                        student.setDb(student.getDb() + points[2]);
                        student.setSpring(student.getSpring() + points[3]);
                        System.out.println("Points updated");

                        updateCourseStatistics("Java", points[0], currentJavaPoints);
                        updateCourseStatistics("DSA", points[1], currentDsaPoints);
                        updateCourseStatistics("Databases", points[2], currentDbPoints);
                        updateCourseStatistics("Spring", points[3], currentSpringPoints);
                    });

        } catch (Exception e) {
            System.out.println(ERROR.INVALID_POINTS.getMessage());
        }
    }

    /**
     * Updating statistics of courses
     * [0] is the number of students taking the course
     * [1] is the total number of points in course
     * [2] is the total number of tasks done in course
     * @param course Name of course (Java, DSA, Database, Spring)
     * @param points Number of points obtained by student
     * @param currentStudentPoints The current number of points earned by the student during the course
     */
    private void updateCourseStatistics(String course, int points, int currentStudentPoints) {
        int[] stats = courseStatistics.get(course);
        if (points > 0) {
            if (currentStudentPoints == 0) {
                stats[0]++;
            }
            stats[1] += points;
            stats[2]++;
        }
        courseStatistics.put(course, stats);
    }


    /**
     * Check if ID is correct
     * @param number Student ID
     * @return True if ID is correct
     */
    private boolean isValidNumber(String number) {
        try {
            int value = Integer.parseInt(number);
            return value >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Check if student's ID exist
     * @param studentId Student ID to find
     */
    public boolean studentWithIdExists(int studentId) {
        return studentSet.stream().anyMatch(student -> student.getId() == studentId);
    }

    /**
     * Check if user input is correct
     * @param input User input
     * @return the number of students that have been added
     */
    public int validateInput(String input) {
        String[] parts = input.split("\\s+");
        int students = 0;
        if (parts.length <= 2) {
            System.out.println("Incorrect credentials.");
            return students;
        }
        String name = parts[0];
        String lastName = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length - 1));
        String email = parts[parts.length - 1];

        Student newStudent = new Student(name, lastName, email, id, 0, 0, 0, 0);

        if (Validation.isInvalidName(name)) {
            System.out.println(ERROR.INVALID_NAME.getMessage());

        }
        else if (Validation.isInvalidName(lastName)) {
            System.out.println(ERROR.INVALID_SURNAME.getMessage());
        }
        else if (!Validation.isValidEmail(email)) {
            System.out.println(ERROR.INVALID_MAIL.getMessage());
        }
        else if (studentSet.contains(newStudent)) {
            System.out.println(ERROR.TAKEN_MAIL.getMessage());
        }
        else {
            System.out.println("The student has been added.");
            studentSet.add(new Student(name, lastName, email, id, 0, 0, 0, 0));
            students++;
            id++;
        }
        return students;
    }

    /**
     * Method for statistics
     * Calculate which course:
     * Is the most/least popular
     * has the most/lowest activity
     * is the easiest/hardest
     */
    public void statistics() {
        Set<String> mostPopularSet = new HashSet<>();
        Set<String> leastPopularSet = new HashSet<>();
        Set<String> highestActivitySet = new HashSet<>();
        Set<String> lowestActivitySet = new HashSet<>();
        Set<String> easiestCourseSet = new HashSet<>();
        Set<String> hardestCourseSet = new HashSet<>();

        int highestStudentCount = Integer.MIN_VALUE;
        int lowestStudentCount = Integer.MAX_VALUE;
        int highestTaskCount = Integer.MIN_VALUE;
        int lowestTaskCount = Integer.MAX_VALUE;
        double highestAveragePoints = Double.MIN_VALUE;
        double lowestAveragePoints = Double.MAX_VALUE;

        boolean dataAvailable = false;

        for (Map.Entry<String, int[]> entry : courseStatistics.entrySet()) {
            String course = entry.getKey();
            int[] stats = entry.getValue();
            int studentCount = stats[0];
            int totalPoints = stats[1];
            int taskCount = stats[2];
            double averagePoints = taskCount > 0 ? (double) totalPoints / taskCount : 0;

            dataAvailable = dataAvailable || studentCount > 0 || taskCount > 0;

            // Most popular
            if (studentCount > highestStudentCount) {
                mostPopularSet.clear();
                mostPopularSet.add(course);
                highestStudentCount = studentCount;
            } else if (studentCount == highestStudentCount) {
                mostPopularSet.add(course);
            }

            // Least popular
            if (studentCount < lowestStudentCount && studentCount > 0) {
                leastPopularSet.clear();
                leastPopularSet.add(course);
                lowestStudentCount = studentCount;
            } else if (studentCount == lowestStudentCount) {
                leastPopularSet.add(course);
            }

            // Highest activity
            if (taskCount > highestTaskCount) {
                highestActivitySet.clear();
                highestActivitySet.add(course);
                highestTaskCount = taskCount;
            } else if (taskCount == highestTaskCount) {
                highestActivitySet.add(course);
            }

            // Lowest activity
            if (taskCount < lowestTaskCount && taskCount > 0) {
                lowestActivitySet.clear();
                lowestActivitySet.add(course);
                lowestTaskCount = taskCount;
            } else if (taskCount == lowestTaskCount) {
                lowestActivitySet.add(course);
            }

            // Easiest course
            if (averagePoints > highestAveragePoints) {
                easiestCourseSet.clear();
                easiestCourseSet.add(course);
                highestAveragePoints = averagePoints;
            } else if (averagePoints == highestAveragePoints) {
                easiestCourseSet.add(course);
            }

            // Hardest course
            if (averagePoints < lowestAveragePoints && averagePoints > 0) {
                hardestCourseSet.clear();
                hardestCourseSet.add(course);
                lowestAveragePoints = averagePoints;
            } else if (averagePoints == lowestAveragePoints) {
                hardestCourseSet.add(course);
            }
        }

        // Remove intersections
        leastPopularSet.removeAll(mostPopularSet);
        lowestActivitySet.removeAll(highestActivitySet);
        hardestCourseSet.removeAll(easiestCourseSet);

        // Convert sets to strings
        String mostPopular = mostPopularSet.isEmpty() ? "n/a" : String.join(", ", mostPopularSet);
        String leastPopular = leastPopularSet.isEmpty() ? "n/a" : String.join(", ",leastPopularSet);
        String highestActivity = highestActivitySet.isEmpty() ? "n/a" : String.join(", ", highestActivitySet);
        String lowestActivity = lowestActivitySet.isEmpty() ? "n/a" : String.join(", ", lowestActivitySet);
        String easiestCourse = easiestCourseSet.isEmpty() ? "n/a" : String.join(", ", easiestCourseSet);
        String hardestCourse = hardestCourseSet.isEmpty() ? "n/a" : String.join(", ", hardestCourseSet);

        if (!dataAvailable) {
            mostPopular = leastPopular = highestActivity = lowestActivity = easiestCourse = hardestCourse = "n/a";
        }

        System.out.println("Most popular: " + mostPopular);
        System.out.println("Least popular: " + leastPopular);
        System.out.println("Highest activity: " + highestActivity);
        System.out.println("Lowest activity: " + lowestActivity);
        System.out.println("Easiest course: " + easiestCourse);
        System.out.println("Hardest course: " + hardestCourse);

        String input = scanner.nextLine();
        while (!input.equals("back")) {
            switch (input) {
                case "Java" -> score("Java", 600);
                case "Spring" -> score("Spring", 550);
                case "DSA" -> score("Dsa", 400);
                case "Databases" -> score("Databases", 480);
                default -> System.out.println("Unknown course.");
            }
            input = scanner.nextLine();
        }
    }

    /**
     * This method shows the top 4 students in a course and calculates their completion percentage
     * Creates a pointsComparator that obtains scores based on the points earned in the given course.
     * This comparator is used to sort the result stream.
     * For each student in the stream:
     * Retrieving the points earned by the student in the course.
     * Calculates the percentage of points earned in relation to the number of points (percentage).
     * Prints the student's score, along with the ID, a list of achievement points and mileage percentage.
     * @param course Name of course
     * @param maxPoints Max point in course
     */
    public void score(String course, int maxPoints) {

        System.out.println(course + "\n" + "id    points    completed");
        course = course.toLowerCase();
        String finalCourse = course;
        Comparator<Student> pointsComparator = (s1, s2) -> {
            int points1 = getCoursePoints(s1, finalCourse);
            int points2 = getCoursePoints(s2, finalCourse);
            return Integer.compare(points2, points1);
        };

        String finalCourse1 = course;
        String finalCourse2 = course;
        studentSet.stream()
                .filter(student -> getCoursePoints(student, finalCourse1) > 0)
                .sorted(pointsComparator)
                .limit(4)
                .forEach(student -> {
                    int points = getCoursePoints(student, finalCourse2);
                    double percentage = ((double) points / maxPoints) * 100;
                    System.out.printf("%d %1d %11.1f%%\n", student.getId(), points, percentage);
                });
    }

    /**
     *
     * @param student Object representing Student class
     * @param course Name of course
     * @return Return student points from course
     */
    private int getCoursePoints(Student student, String course) {
        course = course.toLowerCase();
        return switch (course) {
            case "java" -> student.getJava();
            case "spring" -> student.getSpring();
            case "dsa" -> student.getDsa();
            case "databases" -> student.getDb();
            default -> 0;
        };
    }

    /**
     * Send mail to Student
     */
    public void sendMailToStudents() {
        for (Student student : studentSet) {
            if (isCourseCompleted(student, "Java", 600)) {
                System.out.println(student.generateMailContent() + "Java course.");
                student.setNotified("Java", true);
                notifiedStudentIds.add(student.getId());
            }
            if (isCourseCompleted(student, "Spring", 550)) {
                System.out.println(student.generateMailContent() + "Spring course.");
                student.setNotified("Spring", true);
                notifiedStudentIds.add(student.getId());
            }
            if (isCourseCompleted(student, "DSA", 400)) {
                System.out.println(student.generateMailContent() + "DSA course.");
                student.setNotified("DSA", true);
                notifiedStudentIds.add(student.getId());
            }
            if (isCourseCompleted(student, "Databases", 480)) {
                System.out.println(student.generateMailContent() + "Databases course.");
                student.setNotified("Databases", true);
                notifiedStudentIds.add(student.getId());
            }
        }

        System.out.println("Total " + notifiedStudentIds.size() + " students have been notified.");
        notifiedStudentIds.clear();
    }


    /**
     * Check if course is completed
     * @param student Object representing Student class
     * @param course Course nam
     * @param maxPoints Points needed to pass the course
     * @return return true if student was already notified about course
     */
    private boolean isCourseCompleted(Student student, String course, int maxPoints) {
        int points = getCoursePoints(student, course);
        return !student.isNotified(course) && points == maxPoints;
    }

}
