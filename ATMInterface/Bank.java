package ATMInterface;

import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private String name;
    private ArrayList<User> users;
    private ArrayList<Accaunt> accaunts;

    public Bank(String name){
        this.name = name;
        this.users = new ArrayList<User>();
        this.accaunts = new ArrayList<Accaunt>();
    }

    public String getNewUserUUID(){
        String uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique;
        do {
            uuid = "";
            for (int c = 0;c<len;c++){
                uuid += ((Integer)rng.nextInt(10)).toString();
            }
            nonUnique = false;
            for (User u : this.users){
                if (uuid.compareTo(u.getUUID()) == 0){
                    nonUnique = true;
                    break;
                }
            }

        }while (nonUnique);

        return uuid;

    }
    public String getNewAccauntUUID(){

        String uuid;
        Random rng = new Random();
        int len = 10;
        boolean nonUnique;
        do {
            uuid = "";
            for (int c = 0;c<len;c++){
                uuid += ((Integer)rng.nextInt(10)).toString();
            }
            nonUnique = false;
            for (Accaunt a : this.accaunts){
                if (uuid.compareTo(a.getUUID()) == 0){
                    nonUnique = true;
                    break;
                }
            }

        }while (nonUnique);

        return uuid;


    }
    public void addAccaunt(Accaunt anAcct){
        this.accaunts.add(anAcct);
    }

    public User addUser(String firstName,String lastName,String pin){
        User newUser = new User(firstName,lastName,pin,this);
        this.users.add(newUser);

        Accaunt newAccaunt = new Accaunt("Savings",newUser,this);
        newUser.addAccaunt(newAccaunt);
        this.addAccaunt(newAccaunt);
        return newUser;
    }
    public User userLogin(String userID,String pin){
        for (User u : this.users){
            if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)){
                return u;
            }
        }
        return null;
    }
    public String getName(){
        return this.name;
    }
}
