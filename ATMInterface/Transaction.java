package ATMInterface;

import java.util.Date;

public class Transaction {
    private double amount;
    private Date timeStamp;
    private String memo;
    private Accaunt inAccaunt;

    public Transaction(double amount,Accaunt inAccaunt){
        this.amount = amount;
        this.inAccaunt = inAccaunt;
        this.timeStamp = new Date();
        this.memo = "";

    }
    public Transaction(double amount,String memo,Accaunt inAccaunt){
        this(amount,inAccaunt);
        this.memo = memo;

    }
    public double getAmount(){
        return this.amount;
    }
    public String getSummaryLine(){
        if (this.amount >= 0){
            return String.format("%s : $%.02f : %s",this.timeStamp.toString()
            ,this.amount,this.memo);
        }else {
            return String.format("%s : $(%.02f) : %s",this.timeStamp.toString()
                    ,-this.amount,this.memo);

        }
    }
}
