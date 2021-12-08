import java.util.*;

public class Purse {
    enum LCS {PRE_PERSO, USE, BLOCKED, DEAD}
    enum TType {DEBIT,CREDIT}

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
    private boolean inTransaction = false;
    private int transactionMoney = 0;
    private TType currentTransactionType;


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
            System.out.println("Entrez votre PIN Administrateur : ");
            String pins =sc.nextLine();
            int[] pin = new int[6];
            for (int i =0; i < 6; i++)
            {
                pin[i] = (int)pins.charAt(i) - '0';
            }

            if (verifyPINAdmin(pin)){
                adminTriesLeft = MAX_ADMIN_TRIES;
                setAdminAuthenticate(true);
                setLifeCycleState(LCS.USE);
                return true;
            }
            System.out.println("Code PIN erroné");
            adminTriesLeft--;
            System.out.println("Il vous reste "+adminTriesLeft+" tentatives");
        }

        System.out.println("Carte morte");
        setLifeCycleState(LCS.DEAD);
        return false;

    }
    private boolean getIdentificationUser(){
        System.out.println(lifeCycleState);
        while (userTriesLeft > 0 && lifeCycleState == LCS.USE)
        {
            Scanner sc = new Scanner(System.in);
            System.out.println("Entrez votre PIN : ");
            String pins =sc.nextLine();
            int[] pin = new int[4];
            for (int i =0; i < 4; i++)
            {
                pin[i] = (int)pins.charAt(i) - '0';
            }

            if (verifyPINUser(pin)){
                userTriesLeft = MAX_USER_TRIES;
                setUserAuthenticate(true);
                return true;
            }
            System.out.println("Code PIN erroné");
            userTriesLeft--;
            System.out.println("Il vous reste "+userTriesLeft+" tentatives");

        }
        System.out.println("Carte bloquée");
        setLifeCycleState(LCS.BLOCKED);
        return false;

    }
    void PINChangeUnblock(){
       if( lifeCycleState == LCS.BLOCKED) {
           boolean identification = getIdentificationAdmin();
           if (identification) {
               setLifeCycleState(LCS.USE);
               userTriesLeft = 3;
           } else if (!adminAuthenticate) {
               System.out.println("Cette operation doit etre faite par l'administrateur");
           }
       }
    }
    void beginTransactionDebit(int amount){
        inTransaction = true;
        currentTransactionType = TType.DEBIT;
        if(lifeCycleState == LCS.PRE_PERSO){
            setLifeCycleState(LCS.USE);
        }
        if(lifeCycleState == LCS.DEAD){
            System.out.println("Carte Morte");
            return;
        }

        boolean identification = getIdentificationUser();
        if(identification)
        {
            if(amount <= balance && amount <= maxDebitAmount)
            {
                balance -= amount;
                transactionMoney = amount;
            }
            else
                System.out.println("Transaction échouée Solde insuffisant");
        }
        else
            System.out.println("Non authentifié");
    }
    void beginTransactionCredit(int amount) {
        inTransaction = true;
        currentTransactionType = TType.CREDIT;

        if (lifeCycleState == LCS.PRE_PERSO) {
            setLifeCycleState(LCS.USE);
        }
        if (lifeCycleState == LCS.DEAD) {
            System.out.println("Carte Morte");
            return;
        }
        boolean identification = getIdentificationUser();
        if (identification) {
            if (amount <= maxCreditAmount) {
                balance += amount;
                transactionMoney = amount;
            } else
                System.out.println("Transaction échouée Max Credit dépassé");

        }else{
                System.out.println("Non authentifié");
                if (lifeCycleState == LCS.BLOCKED) {
                    PINChangeUnblock();
                }
            }
        }

    void commitTransactionDebit(){
        if(userAuthenticate)
        {
            transLeft--;
            inTransaction=false;
            transactionMoney = 0;
            System.out.println("Vous avez " + transLeft + "Transaction à faire");
        }
        if (transLeft == 0)
        {
            setLifeCycleState(LCS.DEAD);
        }
    }
    void commitTransactionCredit(){
        if(userAuthenticate)
        {
            transLeft--;
            inTransaction=false;
            transactionMoney = 0;
            System.out.println("Vous avez " + transLeft + "Transaction à faire");

        }
        if (transLeft == 0)
        {
            setLifeCycleState(LCS.DEAD);
        }
    }
    int getData(){
        if(userAuthenticate)
        {
            return balance;
        }
        return 0;
    }

    void reset(){

        if(inTransaction) {
            System.out.println("Arrachage de la transaction en cours");
            if (currentTransactionType == TType.DEBIT) {
                abortDebitTransaction();
            }
            if (currentTransactionType == TType.CREDIT) {
                abortCreditTransaction();

            }

        }
    }

    void abortDebitTransaction(){
        balance += transactionMoney;
        System.out.println(transactionMoney+"n'ont pas été debité de votre carte");
        inTransaction=false;
        transactionMoney = 0;

    }

    void abortCreditTransaction(){

        balance -= transactionMoney;
        System.out.println(transactionMoney+"n'ont pas été credité a votre carte");
        inTransaction=false;
        transactionMoney = 0;

    }
}
