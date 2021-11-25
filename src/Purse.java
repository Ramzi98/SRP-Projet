import java.util.*;

public class Purse {
    enum LCS {PRE_PERSO, USE, BLOCKED, DEAD}

    private int[] userPIN;
    private int[] adminPIN;
    private final int MAX_USER_TRIES = 3;
    private final int MAX_ADMIN_TRIES = 4;
    private final int MAX_TRANS = 500;
    private final int MAX_BALANCE = 100;
    private final int MAX_CREDIT_AMOUNT = 50;
    private final int MAX_DEBIT_AMOUNT = 30;
    private int userTriesLeft;
    private int adminTriesLeft;
    private int balance;
    private int transLeft;
    private boolean userAuthenticate;
    private boolean adminAuthenticate;
    private LCS lifeCycleState;

    private int maxCreditAmount;
    private int maxDebitAmount;

    Purse(int MAX_USER_TRIES, int MAX_ADMIN_TRIES, int MAX_TRANS, int MAX_BALANCE, int MAX_CREDIT_AMOUNT, int MAX_DEBIT_AMOUNT, int[] userPIN, int[] adminPIN)
    {
        this.userPIN = userPIN;
        this.adminPIN = adminPIN;
        this.userTriesLeft = MAX_USER_TRIES;
        this.adminTriesLeft = MAX_ADMIN_TRIES;
        this.transLeft = MAX_TRANS;
        this.balance = MAX_BALANCE;
        this.maxCreditAmount = MAX_CREDIT_AMOUNT;
        this.maxDebitAmount = MAX_DEBIT_AMOUNT;
        lifeCycleState = LCS.PRE_PERSO;

    }

    Purse( int[] userPIN, int[] adminPIN)
    {
        this.userPIN = userPIN;
        this.adminPIN = adminPIN;
        this.userTriesLeft = MAX_USER_TRIES;
        this.adminTriesLeft = MAX_ADMIN_TRIES;
        this.transLeft = MAX_TRANS;
        this.balance = MAX_BALANCE;
        this.maxCreditAmount = MAX_CREDIT_AMOUNT;
        this.maxDebitAmount = MAX_DEBIT_AMOUNT;
        lifeCycleState = LCS.PRE_PERSO;
    }


    public int[] getUserPIN() {
        return userPIN;
    }

    public void setUserPIN(int[] userPIN) {
        this.userPIN = userPIN;
    }

    public int[] getAdminPIN() {
        return adminPIN;
    }

    public void setAdminPIN(int[] adminPIN) {
        this.adminPIN = adminPIN;
    }

    public int getUserTriesLeft() {
        return userTriesLeft;
    }

    public void setUserTriesLeft(int userTriesLeft) {
        this.userTriesLeft = userTriesLeft;
    }

    public int getAdminTriesLeft() {
        return adminTriesLeft;
    }

    public void setAdminTriesLeft(int adminTriesLeft) {
        this.adminTriesLeft = adminTriesLeft;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getTransLeft() {
        return transLeft;
    }

    public void setTransLeft(int transLeft) {
        this.transLeft = transLeft;
    }

    public boolean isUserAuthenticate() {
        return userAuthenticate;
    }

    public void setUserAuthenticate(boolean userAuthenticate) {
        this.userAuthenticate = userAuthenticate;
    }

    public boolean isAdminAuthenticate() {
        return adminAuthenticate;
    }

    public void setAdminAuthenticate(boolean adminAuthenticate) {
        this.adminAuthenticate = adminAuthenticate;
    }

    public LCS getLifeCycleState() {
        return lifeCycleState;
    }

    public void setLifeCycleState(LCS lifeCycleState) {
        this.lifeCycleState = lifeCycleState;
    }

    public int getMaxCreditAmount() {
        return maxCreditAmount;
    }

    public void setMaxCreditAmount(int maxCreditAmount) {
        this.maxCreditAmount = maxCreditAmount;
    }

    public int getMaxDebitAmount() {
        return maxDebitAmount;
    }

    public void setMaxDebitAmount(int maxDebitAmount) {
        this.maxDebitAmount = maxDebitAmount;
    }

    boolean verifyPINUser(int[] PINCode){
        if (Arrays.equals(PINCode,userPIN)) return true; else return false;
    }
    boolean verifyPINAdmin(int[] PINCode){
        if (Arrays.equals(PINCode,adminPIN)) return true; else return false;
    }
    private boolean getIdentificationAdmin(){
        while (adminTriesLeft > 0 && lifeCycleState == LCS.BLOCKED)
        {
            Scanner sc = new Scanner(System.in);
            System.out.println("Entrez votre PIN : ");
            int i=0;
            int[] pin = new int[10];
            while (sc.hasNext()) {
                if (sc.hasNextInt()) {
                    pin[i]=sc.nextInt();
                    i++;
                }
            }
            System.out.println(pin);
            if (verifyPINUser(pin)){
                adminTriesLeft = MAX_ADMIN_TRIES;
                lifeCycleState = LCS.USE;
                return true;
            }
            System.out.println("Code PIN erroné");
            adminTriesLeft--;
        }

        System.out.println("Carte bloquée");
        setLifeCycleState(LCS.BLOCKED);
        return false;

    }
    private boolean getIdentificationUser(){
        while (userTriesLeft > 0 && lifeCycleState == LCS.PRE_PERSO)
        {
            Scanner sc = new Scanner(System.in);
            System.out.println("Entrez votre PIN : ");
            int i=0;
            int[] pin = new int[10];
            while (sc.hasNext()) {
                if (sc.hasNextInt()) {
                    pin[i]=sc.nextInt();
                    i++;
                }
            }
            System.out.println(pin);
            if (verifyPINUser(pin)){
                userTriesLeft = MAX_USER_TRIES;
                lifeCycleState = LCS.USE;
                return true;
            }
            System.out.println("Code PIN erroné");
            userTriesLeft--;
        }

        System.out.println("Carte bloquée");
        setLifeCycleState(LCS.BLOCKED);
        return false;
    }
    void PINChangeUnblock()
    void beginTransactionDebit(int amount)
    void beginTransactionCredit(int amount)
    void commitTransactionDebit()
    void commitTransactionCredit()
    int getData()
}
