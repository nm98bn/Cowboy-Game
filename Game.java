import java.util.Random;
import java.util.Scanner;

public class Game {

    public static class Enemy {
        private String type;
        public int maxHealth;
        public int attackDamage;

        public Enemy(String type, int maxHealth, int attackDamage) {
            this.type = type;
            this.maxHealth = maxHealth;
            this.attackDamage = attackDamage;
        }

        public String getType() {
            return type;
        }

        public int getMaxHealth() {
            return new Random().nextInt(maxHealth - maxHealth / 2 + 1) + maxHealth / 2;
        }

        public int getAttackDamage() {
            return attackDamage;
        }
    }

    public static void main(String[] args) {

        // System variables
        Scanner scan = new Scanner(System.in); // user input object
        Random rand = new Random(); // randomize object

        // Enemy variables
        Enemy[] enemies = {
                new Enemy("Lawman", 110, 30),
                new Enemy("Bounty Hunter", 110, 35),
                new Enemy("Raider", 120, 40),
                new Enemy("Rival Gang Member", 100, 40),
                new Enemy("Comanche", 100, 50),
                new Enemy("Stranger", 100, 40),
                new Enemy("Thief", 80, 20),
        };

        // Player variables
        int playerHealth = 100; // player health
        int attackDamage = 50; // max player attack damage
        int numBandages = 3; // player healing
        int bandageHealAmount = 25; // healing amount
        int bandageDropChance = 30; // Percentage of heal drop chance from killing enemy

        boolean traveling = true; // the player is traveling

        System.out.println("\nWelcome to The Wild West!");

        // ask for player name
        System.out.println("\nWhat's yer name?\n");
        String playerName = scan.nextLine();

        GAME:
        while (traveling) { // while the player is traveling

            System.out.println("--------------------------------------------------------");

            Enemy currentEnemy = enemies[rand.nextInt(enemies.length)]; // randomize enemy that appears
            int enemyHealth = currentEnemy.getMaxHealth(); // get the enemy's max health
            String enemyType = currentEnemy.getType(); // get the enemy's type

            System.out.println("\t#  " + enemyType + " has appeared! #\n");

            while (enemyHealth > 0 && playerHealth > 0) { // while enemy is still alive
                System.out.println("\n\t" + playerName + "'s HP: " + playerHealth); // player health
                System.out.println("\t" + enemyType + "'s HP: " + enemyHealth); // enemy health
                System.out.println("\n\tWhat would you like to do?");
                System.out.println("\t1. Attack");
                System.out.println("\t2. Bandage up");
                System.out.println("\t3. Run");

                String input = scan.nextLine(); // take user input

                if (input.equals("1")) { // if player chooses to attack
                    int damageDealt = rand.nextInt(attackDamage);
                    int damageTaken = rand.nextInt(currentEnemy.getAttackDamage());

                    enemyHealth -= damageDealt; // subtract damage dealt from enemy health
                    playerHealth -= damageTaken; // subtract damage taken from player health

                    System.out.println("\t>The " + enemyType + " shot at you for " + damageTaken + " damage.");
                    System.out.println("\t>You shot at the " + enemyType + " for " + damageDealt + " damage.");

                    // player health is getting too low
                    if (playerHealth < 10 && playerHealth > 1)
                        System.out.println("\t>Yer badly injured. Ya might want to heal up!");

                } else if (input.equals("2")) { // if player chooses to heal
                    if (numBandages > 0) { // if player has bandages
                        playerHealth += bandageHealAmount; // add healing amount to player health
                        numBandages--; // subtract number of bandages used
                        System.out.println("\t>You took cover and started bandaging yourself for " + bandageHealAmount + " HP."
                                + "\n\t>You have " + numBandages + " bandages left." // tell player how many bandages are left
                                + "\n\t>You now have " + playerHealth + " HP."); // tell player how much hp they have
                    } else { // player has no bandages
                        System.out.println("\t>Yer outta bandages. Kill 'em or start runnin'!");
                    }

                } else if (input.equals("3")) { // if player chooses to run
                    System.out.println("\t>Ya ran away from the " + enemyType + "!");
                    continue GAME; // starts from the top of the loop

                } else { // player didn't choose an available option
                    System.out.print("\n\t>What are ya doing, " + playerName + "? That ain't an option! Do something!\n");
                }
            }

            if (playerHealth <= 0) { // player death message
                System.out.println("\n\t>Looks like ya weren't cut out for this life. Yer DEAD, " + playerName + ".");

                System.out.println("\n\t>You've got two options."); // player choices if they die
                System.out.println("\t>1. Try again");
                System.out.println("\t>2. Stay dead");
                String input = scan.nextLine();

                if (input.equals("1")) {
                    System.out.println("Welcome back. Try not to die this time.");
                    playerHealth = 100;
                    continue GAME;
                } else if (input.equals("2")) {
                    System.out.println("RIP " + playerName);
                    break;
                }
            }

            if (enemyHealth <= 0) { // enemy death message
                System.out.println("\n\t>The " + enemyType + " is dead!");
                System.out.println("\t>You have " + playerHealth + " HP left and " + numBandages + " bandage(s) left.");

                if (rand.nextInt(100) < bandageDropChance) { // chance of enemy dropping a bandage for the player
                    numBandages++;
                    System.out.println("\n\t>The " + enemyType + " dropped a bandage!");
                    System.out.println("\t>Ya got " + numBandages + " now.");
                }

                // prompt player for what they want to do next after beating the enemy
                System.out.println("\n\tWhat do ya want to do now?");
                System.out.println("\t1. Continue your travels");
                System.out.println("\t2. Leave the area");

                String input = scan.nextLine();

                while (!input.equals("1") && !input.equals("2")) {
                    System.out.print("\t>Something ain't right. Pick one of the options!");
                    input = scan.nextLine();
                }

                if (input.equals("1")) {
                    System.out.println("\t>Traveling...");
                    continue GAME;

                } else if (input.equals("2")) {
                    System.out.println("\t>Looks like yer done here. Off into the sunset ya go!");
                    break;
                }
            }
        }
    }
}