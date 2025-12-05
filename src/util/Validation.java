package util;

public class Validation {
    public static boolean isUsernameValid(String s) {
        return s != null && s.matches("[A-Za-zА-Яа-я0-9_-]{2,20}");
    }
}
