package demo;

public class Teacher implements Work{
    @Override
    public String work() {
        System.out.println("my work is teach students");
        return "Teacher";
    }

    static class fuckTeacher {
        protected fuckTeacher() {

        }
        public void fuckTeach () {
            System.out.println("i am teacher fuck");
        }
    }
}
