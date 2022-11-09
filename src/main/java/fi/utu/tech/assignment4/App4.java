package fi.utu.tech.assignment4;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class App4 {
  // Huom! Main-metodiin ei pitäisi tarvita tehdä muutoksia!
  // Main-metodi ainoastaan luo tilit ja alkaa tekemään samanaikaisia tilisiirtoja
  public static void main(String[] args) {
    System.out.println("Press Ctrl+C to terminate");
    Random rnd = new Random();

    // Montako tiliä luodaan
    int accountCount = 4;

    // Luodaan tilit ja annetaan starttirahaa
    Account[] accounts = new Account[accountCount];
    for (int i = 0; i < accounts.length; i++) {
      accounts[i] = new Account(rnd.nextInt());
      accounts[i].deposit(rnd.nextDouble(500));
    }
    System.out.println("=========== Accounts created ===========");
    // ExecutorService tilisiirtojen suorittamista varten. Blokkaa, mikäli jono tulee täyteen (estää muistivuodot)
    ExecutorService transferExecutor = new ThreadPoolExecutor(10,
      10,
      0L,
      TimeUnit.MILLISECONDS,
      new ArrayBlockingQueue < > (50),
      new ThreadPoolExecutor.CallerRunsPolicy());

    // Tehdään ja suoritetaan tilisiirtotapahtumia niin kauan kunnes ohjelma suljetaan
    while (true) {
      var from = accounts[rnd.nextInt(accounts.length)];
      var to = accounts[rnd.nextInt(accounts.length)];
      var bt = new BankTransfer(from, to, rnd.nextDouble(500.0));
      transferExecutor.execute(bt);
    }

  }

}

/**
 * Tilisiirtoa kuvaava luokka
 */
class BankTransfer implements Runnable {

  private Account from;
  private Account to;
  private double amount;

  /**
   * 
   * @param from Tili, jolta siirretään
   * @param to Tili, jolle siirretään
   * @param amount Rahamäärä, joka siirretään
   */
  public BankTransfer(Account from, Account to, double amount) {
    this.from = from;
    this.to = to;
    this.amount = amount;
  }

  /**
   * Tilisiirron suorittava metodi. Lukitsee lähde- ja kohdetilin säikeelle eksklusiivisesti.
   */
  @Override
  public void run() {
    // Otetaan säikeen nimi kätevämmän nimiseen muuttujaan
    var name = Thread.currentThread().getName();
    while (true) {
      if (from.lock.tryLock()) {
        try {
          if (to.lock.tryLock()) {
            try {
              if ((from.getBalance() - amount) > 0 && (to.getBalance() + amount) <= 1000) {
                // Jos tässä kohtaa toinen siirtotapahtuma tekisi siirron, siirto voisi mennä yli lain rajojen
                from.withdraw(amount);
                to.deposit(amount);
                break;
              } else {
                break;
              }
            } finally {
              to.lock.unlock();
            }
          }
        } finally {
          from.lock.unlock();
        }
        Random ran = new Random();
        int n = ran.nextInt(1000);
        int TIME = 1000 + n; // 1 sec
        try {
          Thread.sleep(TIME);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
    // Tilisiirto suoritettu ja lukot avattu
    System.out.printf("%s unlocked %d and %d%n", name, from.accountNumber, to.accountNumber);
  }

}

/**
 * Pankkitiliä kuvaava luokka
 */
class Account implements Comparable < Account > {

  private double balance = 0.0;
  public final int accountNumber;
  public final Lock lock = new ReentrantLock();
  /**
   * Pankkitilin konstruktori
   * @param accountNumber Pankkitilin tilinumero
   */
  public Account(int accountNumber) {
    this.accountNumber = Math.abs(accountNumber);
  }

  /**
   * Nosta rahaa tililtä
   * @param amount Nostettava rahamäärä
   */
  public synchronized void withdraw(double amount) {
    System.out.printf("Withdrawing %f from %d%n", amount, accountNumber);
    balance -= amount;
  }

  /**
   * Pane rahaa tilille
   * @param amount Pantava rahamäärä
   */
  public synchronized void deposit(double amount) {
    System.out.printf("Depositing %f to %d%n", amount, accountNumber);
    balance += amount;
  }

  /**
   * Saldotiedustelu
   * @return Pankkitilin tämänhetkinen saldo
   */
  public synchronized double getBalance() {
    return balance;
  }

  /**
   * Vertailee pankkitilejä toisiinsa. Vertailun tulos perustuu tilinumeroon, EI saldoon.
   * @param other Toinen tili, johon tätä tiliä verrataan
   * @return -1, 0 tai 1, jos tämän tilin tilinumero on toisen tilin tilinumeroa pienempi, yhtäsuuri tai suurempi
   */
  @Override
  public int compareTo(Account other) {
    if (this.accountNumber == other.accountNumber)
      return 0;
    else if (this.accountNumber > other.accountNumber)
      return 1;
    else
      return -1;
  }

}