import java.util.Scanner;

public class Program {
    public enum WeekDay {
        TU, WE, TH, FR, SA, SU, MO
    }

    public static void sortClasses(String[][] classes, int classesCount) {
        for (int i = 0; i < classesCount; i++) {
            for (int j = 0; j < classesCount - 1; j++) {
                if (classes[j][0].compareTo(classes[j + 1][0]) > 0) {
                    String temp = classes[j][0];
                    classes[j][0] = classes[j + 1][0];
                    classes[j + 1][0] = temp;
                    temp = classes[j][1];
                    classes[j][1] = classes[j + 1][1];
                    classes[j + 1][1] = temp;
                }
            }
        }
        for (int i = 0; i < classesCount; i++) {
            for (int j = 0; j < classesCount - 1; j++) {
                if (WeekDay.valueOf(classes[j][1]).ordinal() > WeekDay.valueOf(classes[j + 1][1]).ordinal()) {
                    String temp = classes[j][1];
                    classes[j][1] = classes[j + 1][1];
                    classes[j + 1][1] = temp;
                    temp = classes[j][0];
                    classes[j][0] = classes[j + 1][0];
                    classes[j + 1][0] = temp;
                }
            }
        }
    }

    public static int searchAttendance(String[][] attendance, int recordsCount, String student, String time,
            String day) {
        int res = 0;
        for (int i = 0; i < recordsCount; i++) {
            if (attendance[i][0].equals(student) && attendance[i][1].equals(time.trim())
                    && attendance[i][2].equals(day.trim())) {
                if (attendance[i][3].equals("HERE")) {
                    res = 1;
                } else {
                    res = -1;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String[] students = new String[10];
        int studentsCount = 0;

        while (10 - studentsCount > 0) {
            String buffer = in.nextLine();
            if (buffer.equals(".")) {
                break;
            }
            students[studentsCount] = buffer;
            studentsCount++;
        }

        String[][] timetable = new String[10][2];
        int classesPerWeek = 0;
        while (10 - classesPerWeek > 0) {
            String buffer = in.nextLine();
            if (buffer.equals(".")) {
                break;
            }
            String[] parts = buffer.split(" ");
            timetable[classesPerWeek][0] = parts[0];
            timetable[classesPerWeek][1] = parts[1];
            classesPerWeek++;
        }
        sortClasses(timetable, classesPerWeek);

        int classesNum = 0;
        String[] header = new String[31];
        for (int i = 1; i < 31; i++) {
            WeekDay day = WeekDay.values()[(i - 1) % 7];
            for (int j = 0; j < classesPerWeek; j++) {
                if (WeekDay.valueOf(timetable[j][1]) == day) {
                    header[classesNum++] = String.format("%s:00 %s %2s|", timetable[j][0], timetable[j][1], i);
                }
            }
        }
        int maxRecords = studentsCount * classesNum, recordsCount = 0;
        String[][] attendance = new String[maxRecords][4];
        while (maxRecords - recordsCount > 0) {
            String buffer = in.nextLine();
            if (buffer.equals(".")) {
                break;
            }
            String[] parts = buffer.split(" ");
            attendance[recordsCount][0] = parts[0];
            attendance[recordsCount][1] = parts[1];
            attendance[recordsCount][2] = parts[2];
            attendance[recordsCount][3] = parts[3];
            recordsCount++;
        }

        System.out.print("          ");
        for (int i = 0; i < classesNum; i++) {
            System.out.printf("%10s", header[i]);
        }

        System.out.println();
        for (int i = 0; i < studentsCount; i++) {
            System.out.printf("%10s", students[i]);
            for (int j = 0; j < classesNum; j++) {
                int res = searchAttendance(attendance, recordsCount, students[i],
                        header[j].substring(0, 1),
                        header[j].substring(8, 10));

                if (res == 1) {
                    System.out.printf("%10s|", "1");
                } else if (res == -1) {
                    System.out.printf("%10s|", "-1");
                } else {
                    System.out.printf("%10s|", " ");
                }

            }
            System.out.println();
        }

        in.close();

    }
}
