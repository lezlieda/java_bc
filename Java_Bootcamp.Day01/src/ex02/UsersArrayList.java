package ex02;

public class UsersArrayList implements UsersList {
    private int usersNum = 0;
    private int usersMax = 10;
    private User[] users = new User[usersMax];

    public void addUser(User user) {
        if (usersNum == usersMax) {
            usersMax *= 2;
            User[] tmp = new User[usersMax];
            for (int i = 0; i < usersNum; i++) {
                tmp[i] = users[i];
            }
            users = tmp;
        }
        if (user.getBalance() >= 0) {
            users[usersNum++] = user;
        }
    }

    public User getUserById(int id) {
        for (int i = 0; i < usersNum; i++) {
            if (users[i].getId() == id) {
                return users[i];
            }
        }
        throw new UserNotFoundException("User by id " + id + ", not found.");
    }

    public User getUserByIndex(int index) {
        if (index < 0 || index >= usersNum) {
            throw new UserNotFoundException("User by index " + index + ", not found.");
        }
        return users[index];
    }

    public int getusersNum() {
        return usersNum;
    }

    public void printUsers() {
        for (int i = 0; i < usersNum; i++) {
            users[i].printUserInfo();
        }
    }

}
