public class User{
    
    enum Permissions{
        Root, Mentor, Student;
    }

    private String firstName;
    private String lastName;
    private int id;
    private String passwordHash;
    private String email;
    private Permissions permission;
}