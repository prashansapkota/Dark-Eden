import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {



	public static void main(String[] args) throws FileNotFoundException
	{
		Play player1 = new Play("Warrior",15, 15,1, "Sword", 3, 3,"Slash", 5, "Warrior Armor", 0, false);
		//this line checks if the user has started the game
		boolean start = Play.gameStart();
		if (start) {
		System.out.println(player1.toString());
		player1.getWpn();
		player1.levelUp(player1.getLevel());
		String pturn = " ";
		int Atk = player1.getwpnAtk();
		Play enemy1 = new Play ("Goblin", 6, 6, 2, false);
		Play enemy2 = new Play ("Bandit",5, 5, 1, false);
		int pHealth = player1.maxHp();
		int i = 1;
		boolean run = false;
		//loops if the player isn't dead or hasn't run
		while (!player1.PdeathCheck() && !run) {
			if (!(enemy1.EdeathCheck() && enemy2.EdeathCheck())) {
			System.out.println("\n \n Turn " + i);
			if (!(enemy1.EdeathCheck())) {
			System.out.println("1 " + enemy1.eToString());}
			if (!enemy2.EdeathCheck()) {
			System.out.println("2 " + enemy2.eToString());}
			int choice = player1.rndPrgsn(pturn, Atk, Atk);
			//player turn if player chooses to attack
			if (choice == 1 && !(player1.PdeathCheck())) {
				enemy1.subHp(player1.getwpnAtk());
				player1.setAtkdefault();
				if (enemy1.EdeathCheck()) {
					System.out.println(enemy1.eClassInfo() + " died");
				}
			}
			else if (choice == 2 && !(player1.PdeathCheck())) {
				enemy2.subHp(player1.getwpnAtk());
				player1.setAtkdefault();
				if (enemy2.EdeathCheck()) {
					System.out.println(enemy2.eClassInfo() + " died");
				}
			}
			else if(choice == -1) {
				run = player1.run();
			}
			
			if (!(enemy1.EdeathCheck()) && !(player1.PdeathCheck()) && !run ) {
			enemy1.enemyturn();
			pHealth = player1.dPHp(enemy1.eAtkInfo());
			}
			if (!(enemy2.EdeathCheck()) && !(player1.PdeathCheck()) && !run) {
			enemy2.enemyturn();
			pHealth = player1.dPHp(enemy2.eAtkInfo());
			}
			boolean dead = player1.PdeathCheck();
			if (enemy1.EdeathCheck() && enemy2.EdeathCheck()) {
				System.out.println("You win");
				player1.genXP();
				player1.chngGear(player1.probCheck());
				
			}
			if (dead != true && !(enemy1.EdeathCheck()) && !(enemy1.EdeathCheck())) {
				if (!run) {
					System.out.println("\n Your health is now: " + pHealth + "/" + player1.getmax());}
			}
			else if (dead == true) {
				System.out.println("\n Your health is now: 0 /" + player1.getmax());
				System.out.println(" \n \n GAME OVER ");
			}
			i++;
			}	
		}
		{
			 try {
		         Thread.sleep(1000); // Pause for 1 second (1000 milliseconds)
		     } catch (InterruptedException e) {
		         e.printStackTrace(); // Handle interruption
		     }
	}
	}	
}
}