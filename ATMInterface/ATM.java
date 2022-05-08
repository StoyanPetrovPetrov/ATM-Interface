package ATMInterface;

import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank theBank = new Bank("Bank of Stoyan");

        User aUser = theBank.addUser("Elka","Petrova","1234");

        Accaunt newAccaunt = new Accaunt("Cheking",aUser,theBank);
        aUser.addAccaunt(newAccaunt);
        theBank.addAccaunt(newAccaunt);
        User curUser;
        while (true){
            curUser = ATM.mainMenuPrompt(theBank,scanner);
            ATM.printUserMenu(curUser,scanner);
        }

    }
    public static User mainMenuPrompt(Bank theBank,Scanner scanner){
        String userID;
        String pin;
        User authUser;
        do {
            System.out.printf("\n\nWelcome to %s\n\n",theBank.getName());
            System.out.print("Enter user ID: ");
            userID = scanner.nextLine();
            System.out.print("Enter pin: ");
            pin = scanner.nextLine();
            authUser = theBank.userLogin(userID,pin);
            if (authUser == null){
                System.out.println("Incorrect user ID/Pin combination. " + "Please try agai.");
            }

        }while (authUser == null);
        return authUser;
    }
    public static void printUserMenu(User theUser,Scanner scanner){
        theUser.printAccountsSummary();

        int choice;
        do {
            System.out.printf("Welcome %s,what woud you like to do?\n",theUser.getFirstName());
            System.out.println("  1) Show account transaction history");
            System.out.println("  2) Withdrawl");
            System.out.println("  3) Deposit");
            System.out.println("  4) Transfer");
            System.out.println("  5) Quit");
            System.out.println();
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();

            if (choice < 1 || choice > 5){
                System.out.println("Invalid choice.Pleace enter 1-5");
            }
        }while (choice < 1 || choice > 5);
        switch (choice){
            case 1:
                ATM.showTrnceHistory(theUser,scanner);
                break;
            case 2:
                ATM.withdrawlFunds(theUser,scanner);
                break;
            case 3:
                ATM.depositFunds(theUser,scanner);
                break;
            case 4:
                ATM.tranceferFunds(theUser,scanner);
                break;
            case 5:
                scanner.nextLine();
                break;
        }
        if (choice != 5){
            ATM.printUserMenu(theUser,scanner);
        }
    }
    public static void showTrnceHistory(User theUser,Scanner scanner){
        int theAcct;
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "whose transaction you want to see: ",theUser.numAccounts());
            theAcct = scanner.nextInt() - 1;
            if (theAcct < 0 || theAcct >= theUser.numAccounts()){
                System.out.println("Invalid account.Pleace try again");
            }

        }while (theAcct < 0 || theAcct >= theUser.numAccounts());
        theUser.printAcctTransHistory(theAcct);
    }
    public static void tranceferFunds(User theUser,Scanner scanner){
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        do {
            System.out.printf("Enter the number (1-%d) of the account\n" + "to trancefer from: ",theUser.numAccounts());
            fromAcct = scanner.nextInt()-1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()){
                System.out.println("Invalid account.Pleace try again");
            }

        }while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" + "to trancefer to: ",theUser.numAccounts());
            toAcct = scanner.nextInt()-1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()){
                System.out.println("Invalid account.Pleace try again");
            }

        }while (toAcct < 0 || toAcct >= theUser.numAccounts());

        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $",acctBal);
            amount = scanner.nextDouble();
            if (amount < 0){
                System.out.println("Amount must be greater then zero. ");
            }else if (amount > acctBal){
                System.out.printf("Amount must not be greater than\n" +
                        "balance of $%.02f.\n",acctBal);
            }
        }while (amount < 0 || amount > acctBal);

        theUser.addAcctTransaction(fromAcct,-1*amount,String.format("Transfer to acount %s" ,theUser.getAcctUUID(toAcct)));
        theUser.addAcctTransaction(toAcct,amount,String.format("Transfer to acount %s" ,theUser.getAcctUUID(toAcct)));

    }
    public static void withdrawlFunds(User theUser,Scanner scanner){
        int fromAcct;

        double amount;
        double acctBal;
        String memo;

        do {
            System.out.printf("Enter the number (1-%d) of the account\n" + "to withdraw from: ",theUser.numAccounts());
            fromAcct = scanner.nextInt()-1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()){
                System.out.println("Invalid account.Pleace try again");
            }

        }while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);
        do {
            System.out.printf("Enter the amount to withdraw (max $%.02f): $",acctBal);
            amount = scanner.nextDouble();
            if (amount < 0){
                System.out.println("Amount must be greater then zero. ");
            }else if (amount > acctBal){
                System.out.printf("Amount must not be greater than\n" +
                        "balance of $%.02f.\n",acctBal);
            }
        }while (amount < 0 || amount > acctBal);
        scanner.nextLine();
        System.out.println("Enter a memo: ");
        memo = scanner.nextLine();
        theUser.addAcctTransaction(fromAcct,-1*amount,memo);

    }
    public static void depositFunds(User theUser,Scanner scanner){
        int toAcct;

        double amount;
        double acctBal;
        String memo;

        do {
            System.out.printf("Enter the number (1-%d) of the account\n" + "to deposi in: ",theUser.numAccounts());
            toAcct = scanner.nextInt()-1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()){
                System.out.println("Invalid account.Pleace try again");
            }

        }while (toAcct < 0 || toAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(toAcct);
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $",acctBal);
            amount = scanner.nextDouble();
            if (amount < 0){
                System.out.println("Amount must be greater then zero. ");

            }
        }while (amount < 0 );
        scanner.nextLine();
        System.out.println("Enter a memo: ");
        memo = scanner.nextLine();
        theUser.addAcctTransaction(toAcct,amount,memo);

    }

    }


