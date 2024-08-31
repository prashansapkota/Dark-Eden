import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

public class Play {
	
	//declaring private variables for object Player
	private String pClass;
	private int maxHp;
	private int hp;
	private int level;
	private String weapon;
	private int wpnAtk;
	private int maxAtk;
	private String spMove;
	private int spAtkValue;
	private String gear;
	//private String item;
	private int xp;
	private boolean isDead;
	
	//declaring private variables for object Enemy
	private String eClass;
	private int eHp;
	private int eMax;
	private int eAtk;
	private boolean eisDead;
	
	//constructor to create object Player
	public Play(String _pClass, int _maxHp, int _hp, int _level, String _weapon, int _wpnAtk, int _maxAtk, String _spMove, int _spAtkValue, String _gear, int _xp, boolean _isDead){
		pClass = _pClass;
		maxHp = _maxHp;
		hp = _hp;
		level = _level;
		weapon = _weapon;
		wpnAtk = _wpnAtk;
		maxAtk = _maxAtk;
		spMove = _spMove;
		spAtkValue = _spAtkValue;
		gear = _gear;
		xp = _xp;
		isDead = _isDead;
		
	}	
	
	//no argument constructor to initialize values to default
	Play (){
	maxHp = 10;
	hp = maxHp;
	level = 1;
	wpnAtk = 3;
	maxAtk = wpnAtk;
	xp = 0;
	}
	
	//constructor to create object Enemy
	public Play (String _eClass, int _eHp, int _eMax, int _eAtk, boolean _eisDead) {
		eClass = _eClass;
		eHp = _eHp;
		eMax = _eMax;
		eAtk = _eAtk;
		eisDead = _eisDead;
	}
	
	// method levelUp to increase level by 1 when xp exceeds 1000
	public void levelUp(int level) {
		int tempHp = 0;
		 if (xp>1000) {
			 //for every 1000 that the xp exceeds 1000 player level increases by 1 and maximum HP increases by 2
			for (int i= xp/1000; i>0; i--) {
				xp = xp%1000;
				level++;
				tempHp = maxHp;
				maxHp = maxHp + 2;
				}
			hp = maxHp;
			}
		 //prints out player level, max hp and remaining xp
			System.out.print("\n You are now level: " + level);
			if (maxHp > tempHp) {
			System.out.print("\n Health increased to: " + maxHp);
			}
			System.out.print("\n Remaining xp: " + xp);
		 }
	
	//toString method to give a description of player character
	public String toString() {
		return "Player Character: " + "\n Class: " +  pClass + "\n Level: " + level + "\n Weapon: " + weapon + "\n Attack stat: " + wpnAtk + "\n Special move: " + spMove + "\n SP damage:" + spAtkValue + "\n Armor: " + gear + "\n XP: " + xp;
	}
	
	//gives a random weapon to the player at the start of battle
	public void getWpn() throws FileNotFoundException {
		File inputFile = new File("wpn.txt");
		Scanner in = new Scanner(inputFile);
		Random rng = new Random();
		int i = 0;
		int rand = rng.nextInt(4);
		//getting a random weapon from file
		while (i<=rand) {
		String wpnName = in.next();
		int wpnAttack = in.nextInt();
		if (i == rand) {
			//prints what weapon the player 
		System.out.print(" \n \n You got a ");
		System.out.print(" Weapon: " + wpnName);
		System.out.println(" \n Weapon Atk stat:" + wpnAttack);
		weapon = wpnName;
		wpnAtk = wpnAttack;
		maxAtk = wpnAtk;
		}
		i++;
		}
		
		
	}
	
	//method to let the player use available items
	double itemU = 0;
	public void useItem() throws FileNotFoundException {
		File inputFile = new File("item.txt");
		Scanner in = new Scanner(inputFile);
		int i = 0;
		int choice = 0;
		String newString = "";
        System.out.println("What item would you like to use?");
        //prints a description of available items for the player to use
        while (i<=5) {
        	String itemName = in.next();
        	double itemCost = in.nextDouble();
        	System.out.print( (i+1)+ " " + itemName);
        	System.out.print(" | Cost: " + itemCost );
        	String itemUse = in.next();
        	if(itemUse.substring(0,1).equals("%")) {
        		newString = itemUse.substring(1);
        	System.out.println(" | Restores " + newString + " % of your current health");
        	}
            else if(itemUse.substring(0,1).equals("+")) {
        		newString = itemUse.substring(1);
        	System.out.println(" | Adds " + newString + " to your Weapon's attack for this turn");
            }
        	i++;
        }
        Scanner in1 = new Scanner(inputFile);
		Scanner input = new Scanner (System.in);
		//input validation to check that the player entered a valid number
		choice = input.nextInt();
		while (!(choice>=1 && choice<=6)) {
			System.out.println("What item would you like to use?");
			choice = input.nextInt();
        }
		choice--;
        int j = 0;
        double remCost = 3;
        while (j<=5) {
        	String itemName = in1.next();
        	 double itemCost = in1.nextDouble();
        	String itemUse = in1.next();
        	String newstr = "";
			int modifier = 0;
			//checks what item the player used, prints the effects of the used item, calculates cost of said item
        	if (itemCost<=remCost){
               		if (j==choice) {
        			if(itemUse.substring(0,1).equals("+")) {
        				newstr = itemUse.substring(1);
        				modifier = Integer.parseInt(newstr);
        				wpnAtk = wpnAtk + modifier;
        				System.out.println(itemName + " was used. " + "Attack increased by " + itemUse);
        				System.out.println("Weapon attack is now: " + wpnAtk + " for this turn");
        				itemU += itemCost;
        				System.out.println(itemU);
        				remCost = remCost - itemCost;
        			}
        			else if (itemUse.substring(0,1).equals("%")){
        				newstr = itemUse.substring(1);
        				modifier = Integer.parseInt(newstr);
        				hp = hp + (int)((double)modifier/100 * hp);
        				if (hp>=maxHp) {
        					hp = maxHp;
        					System.out.println("Health is " + hp + "/" + maxHp);
        					itemU = itemU + itemCost;
            				remCost = remCost-itemCost;
        				}
        				else {
        					System.out.println("Health is now: " + hp + "/" +maxHp);
        					System.out.println(itemName + " was used. Healed for: " + (int)((double)modifier/100 * hp));	
        					itemU = itemU + itemCost;
            				remCost = remCost-itemCost;
        				}
        				
        			}
        			System.out.println("Remaining use of items: " + remCost);
        		}
        	}
        	else if (itemU>3) {
        		chooseAction();
        	}
        	j++;
        	
        } 	
        
        }
	
	//accessor to return attack value of weapon
	public int getwpnAtk() {
		return wpnAtk;
	} 
	
	//lets the player choose between a few actions every turn
	public String chooseAction() throws FileNotFoundException{
		File inputFile = new File("Action.txt");
		Scanner in = new Scanner(inputFile);
		Scanner input = new Scanner(System.in);
		String returnout = "";
		if (!PdeathCheck()) {
		System.out.println("\n What would you like to do this turn?");
		while (in.hasNextLine()) {
			String prntln = in.nextLine();
			if (prntln.equals("2. Use Item") && itemU>=3) {
    			System.out.println("Already used maximum number of items");}
			else if (prntln.equals("2. Use Item") && itemU<3) {
				System.out.println(prntln + " | Limited to 3 items per battle");
			}
			else {
			System.out.println(prntln);}
		}
		in.close();
		String act = "";
		Scanner in1 = new Scanner(inputFile);
		int choice = input.nextInt();
		while (!(choice>=1 && choice<=4)) {
			System.out.println(" \n What would you like to do this turn?");
			choice = input.nextInt();
			if (!(choice>=1 && choice<=4)) {
			System.out.println("\n What would you like to do this turn?");
			choice = input.nextInt();
		}
		}
		while (in1.hasNext()) {
			act = in1.nextLine();
			if (choice == Integer.parseInt(act.substring(0,1))) {
			System.out.println("You chose to " + act.substring(3));
			returnout = act.substring(3);
			}
			
		}
		}
		return returnout;
	}
	
	//method to check if the player has died
	public boolean PdeathCheck() {
		if (hp>0) {
			isDead = false;
			}
		else {
			isDead = true;
			}
		return isDead;
	}
	
	//method to check if the enemy has died
	public boolean EdeathCheck() {
		if(eHp>0) {
			eisDead = false;
		}
		else {
			eisDead = true;
		}
		return eisDead;
	}
	
	//gives the Player any random armor at the end of a battle
	public String probCheck() throws FileNotFoundException {
		Random rng = new Random();
		File inputFile = new File("Gear.txt");
		Scanner in = new Scanner(inputFile);
		int i = 0;
		int prob = rng.nextInt(5);
		String obtArm = " ";
		while (i<=prob) {
			String _obtArm = in.nextLine();
			if (i == prob) {
				obtArm = _obtArm;
				System.out.println("You obtained " + obtArm);
				}
			i++;
			}
		
		return obtArm;
		}
	
	//lets the player change equipped armor
	public String chngGear(String ngear) throws FileNotFoundException{
		Scanner in = new Scanner(System.in);
		System.out.println("\n Do you want to change your armor? Y/N");
		String choice = in.next();
		if (choice.toUpperCase().equals("Y")) {
			System.out.println("You have now equipped " + ngear);}
		else if(choice.toUpperCase().equals("N")){
			System.out.println("You still have" + gear);
		}
		in.close();	
		return ngear;
	}
	
	//sets the value of weapon attack at the start of every turn
	public void setAtkdefault(){
		wpnAtk = maxAtk;
	}	
	
	//returns the current level of the player
	public int getLevel() {
		return level;
	}
	
	//returns maximum value of HP of player
	public int maxHp() {
		return maxHp;
	}
	
	//returns the current HP of an enemy
	public int getHp() {
		return eHp;
	}
	
	//method to decrease enemy's health when player attacks
	public int subHp(int wpnAtk) {
		getHp();
		eHp = eHp - wpnAtk;
		return eHp;
	}
	
	//accessor to return the class of an enemy
	public String eClassInfo() {
		return eClass;
	}
	
	//accessor to return the maximum health of an enemy
	public int eMaxInfo() {
		return eMax;
	}
	
	//accessor to return the attack value of an enemy
	public int eAtkInfo() {
		return eAtk;
	}
	
	//toString method to format and print enemy description
	public String eToString() {
		String reStat = "";
		if (eHp < 0) {
			reStat = "\n Enemy: " + eClass + " \t Enemy Health: 0/ " + eMax;
		}
		else {
			reStat ="\n Enemy: " + eClass + " \t Enemy Health: " + eHp + "/ " + eMax; 
		}
		return reStat;
	}
	
	//method to decrease player health when enemy attack
	public int dPHp(int eAtk) {
		hp = hp - eAtk;
		return hp;
	}
	//method to progress turns in battle returns a target value when the player chooses an action
	public int rndPrgsn(String pturn, int wpnAtk, int spAtkValue ) throws FileNotFoundException{
		Scanner scanner = new Scanner(System.in);
		int target = 0;
		System.out.println("\n Player's turn");
		pturn = chooseAction();
		wpnAtk = getwpnAtk();
		if(pturn.equals("Use Item") && itemU<3) {
			useItem();
		}
		else if (pturn.equals("Use Item") && itemU>=3){
		while (pturn.equals("Use Item")) {
			System.out.println("\nPick something else");
			pturn = chooseAction();}
		}
		if (pturn.equals("Attack ")) {
			System.out.println("What enemy would you like to target?");
			target = scanner.nextInt();
		}
		else if(pturn.equals("Use Special")) {
			wpnAtk = useSpecial();
			System.out.println("What enemy would you like to target?");
			target = scanner.nextInt();
		}
		else if (pturn.equals("Run Away")) {
			System.out.println("You ran away.");
			target = -1;
		}
        EdeathCheck();
        return target;
		}
	
	//method to change weapon attack value 
	public int useSpecial() {
		wpnAtk = spAtkValue;
		return wpnAtk;
	}
	
	//method to return the maximum value of Player HP
	public int getmax() {
		return maxHp;
	}
	//method to let the enemy attack after the player's turn
	public int enemyturn() {
		System.out.println(eClass +  " attacked you. ");
		int pHealth = dPHp(eAtk);
		PdeathCheck();
		return pHealth;
	}
	//method to generate xp after combat ends
	public int genXP() {
		Random rng = new Random();
		int gXp = 100 + rng.nextInt(300);
		xp = xp + gXp;
		System.out.println("You gained " + gXp + " XP " + "\n You now have " + xp + " XP ");
		return xp;
	}
	
	//chooseAction accesses this method when player chooses to run away
	public boolean run() {
		boolean run = true;
		return run;
	}
	
	//method to prompt the user to start the game
	public static boolean gameStart() {
	    Scanner in = new Scanner(System.in);
	    boolean start = false; // Initialize start to false
	    System.out.println("Press 'A' to start game");
	    String resp = in.next();
	    String resp1 = resp.toLowerCase();
	    
	    while (!(resp1.equals("a"))) {
	        resp = in.next();
	        resp1 = resp.toLowerCase();
	    }
	    
	    // If the loop is exited, it means 'a' has been entered
	    start = true;
	    return start;
	}
	{
		 try {
	         Thread.sleep(2000); // Pause for 1 second (1000 milliseconds)
	     } catch (InterruptedException e) {
	         e.printStackTrace(); // Handle interruption
	     }
}
}	

	




