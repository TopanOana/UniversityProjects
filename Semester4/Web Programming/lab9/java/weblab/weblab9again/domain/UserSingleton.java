package weblab.weblab9again.domain;


public class UserSingleton {
    private int nrUsers;
    private int currentUser;
    private static UserSingleton instance;

    private UserSingleton(){
        nrUsers=0;
        currentUser=0;
    }

    public synchronized int getCurrentUser() {
        return currentUser;
    }

    public synchronized void setCurrentUser(int currentUser) {
        this.currentUser = currentUser;
    }

    public synchronized static UserSingleton getInstance(){
        if (instance==null)
            instance = new UserSingleton();
        return instance;
    }

    public synchronized int getNrUsers(){
        return nrUsers;
    }

    public synchronized void setNrUsers(int nrUsers){
        this.nrUsers=nrUsers;
    }

}
