package ATMInterface;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
    private String firstName;
    private String lastname;
    private String uuid;
    private byte pinHash[];
    private ArrayList<Accaunt> accaunts;

    public User(String firstName,String lastname,String pin,Bank theBank){
        this.firstName = firstName;
        this.lastname = lastname;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());

        }catch (NoSuchAlgorithmException e){
            System.err.println("error, cought NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        this.uuid = theBank.getNewUserUUID();
        this.accaunts = new ArrayList<Accaunt>();

        System.out.printf("New user %s,%s with ID %s created.%n",lastname,firstName,this.uuid);

    }
    public void addAccaunt(Accaunt anAcct){
        this.accaunts.add(anAcct);
    }
    public String getUUID(){

        return this.uuid;
    }
    public boolean validatePin(String aPin){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()),this.pinHash );

        }catch (NoSuchAlgorithmException e){
            System.err.println("error, cought NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;


    }
    public String getFirstName(){
        return this.firstName;
    }
    public void printAccountsSummary(){
        System.out.printf("\n\n%s's accounts sumary\n",this.firstName);
        for (int a = 0;a < this.accaunts.size();a++){
            System.out.printf("  %d) %s\n",a + 1,this.accaunts.get(a).getSummaryLine());
        }
        System.out.println();

    }
    public int numAccounts(){
        return this.accaunts.size();
    }
    public void printAcctTransHistory(int acctIdx){
        this.accaunts.get(acctIdx).printTransHistory();
    }
    public double getAcctBalance(int acctIdx){
        return this.accaunts.get(acctIdx).getBalance();
    }
    public String getAcctUUID(int acctIdx){
        return this.accaunts.get(acctIdx).getUUID();
    }
    public void addAcctTransaction(int acctIdx,double amount,String memo){
        this.accaunts.get(acctIdx).addTransaction(amount,memo);
    }

}
