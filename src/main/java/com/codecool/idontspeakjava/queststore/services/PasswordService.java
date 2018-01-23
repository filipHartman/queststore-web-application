package com.codecool.idontspeakjava.queststore.services;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordService {

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String candidate, String hashed) {
        return BCrypt.checkpw(candidate, hashed);
    }

    public boolean checkIfNewPasswordIsCorrect(String newPassword) {
/*        Explanations:

        (?=.*[0-9]) a digit must occur at least once
        (?=.*[a-z]) a lower case letter must occur at least once
        (?=.*[A-Z]) an upper case letter must occur at least once
        (?=.*[!@.,#$%&*()_+=|<>?{}\\[\\]) a special character must occur at least once
        (?=\\S+$) no whitespace allowed in the entire string
        .{8,} at least 8 characters*/
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@.,#$%&*()_+=|<>?{}\\\\[\\\\]~-])(?=\\S+$).{8,}";
        return newPassword.matches(pattern);
    }
}
