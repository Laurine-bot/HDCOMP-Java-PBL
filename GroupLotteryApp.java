import java.util.Scanner;

public class GroupLotteryApp{

	public static void main(String[] args){

		Scanner input = new Scanner(System.in); //get the user input

		int[][] userNumbers; //create an array to store the user numbers

		int[] lotteryNumbers; //to get the lottery numbers from the inst class

		int[] matches; // create an array of matches (coming from the computation in the inst class)

		int gameCounter; //var to count the number of games the user played

		int totalWinnings = 0; //initialize the total winnings to 0 so we can add to this sum the winnings of each game

		double aveWinnings; //create a var to get the average winnings of all games back from the inst class

		int winnings; //create a var to get the winnings per one game back from the inst class

		int linesWon; //create a var to get the number of lines won per one game back from the inst class

		final int MAX_GAMES = 100; //we decided to have a sufficiently big array to store each game history on one row, there is one hundred rows in this array, so the user can play 100 times
		int[][] gameHistory = new int[MAX_GAMES][3]; //initialize the history array

		int noLines; //create a var for the number of lines the user will input

		int controlCounter = 0; //control counter used if the user wants to exit the game or lottery is won

		System.out.println("You are playing a lottery game.\nFor each iteration of the game, six random numbers between 1 and 40 (inclusive) are generated. \nTo win the lottery, you must guess those numbers. \nIn each interation of the game, you may play up to three lines of six numbers. \nYou may continue playing the lottery game until you win the lottery(match six numbers) or until you choose to stop. \nPrize money is attached to matching: \n3 numbers: 100 euro\n4 numbers: 300 euro\n5 numbers: 1500 euro\n6 numbers: you win the entire lottery of 1 000 000 euro.\nAt the end of all game iterations, the average prize money won per game will be calculated and displayed.");


		GroupLottery lottery = new GroupLottery(); //initialize the new GroupLottery Object and initialize the lottery numbers from the constructor
		gameCounter = 1;

		do{ // main game loop
			System.out.println("\nFor game number " + (gameCounter));
			System.out.print("Please enter the number of lines you want to play (between 1 and 3): ");

			noLines = input.nextInt(); // take the input for the number of lines
			while(noLines<1 || noLines>3){ // data validation for number of lines
				System.out.print("That is not a valid number! Please enter number of lines between 1 and 3: ");
				noLines = input.nextInt();
			}

			userNumbers = new int[noLines][6]; // initialize userNumbers array with the noLines being the number of rows in the array

			System.out.println("\nThank you! Now, please enter your lucky numbers - between 1 and 40:");

			for(int i =0; i<userNumbers.length; i++){
				System.out.println("For line " + (i+1));
				for(int j = 0; j < userNumbers[i].length; j++){
					System.out.print("For number " + (j+1)+ ": ");
					int number = input.nextInt();

					if(number>0 && number<41){
						if( (number != userNumbers[i][0]) && (number != userNumbers[i][1]) && (number != userNumbers[i][2]) && (number != userNumbers[i][3]) && (number != userNumbers[i][4]) && (number != userNumbers[i][5]) ){
							userNumbers[i][j] = number; //this if statement validates the integer input and stores the user number in the array
						} else {
							System.out.println("You have already entered this number! Please only enter unique numbers.");
							j=j-1;
						}
					} else {
						System.out.println("That is not a valid number! Please enter a number between 1 and 40.");
						j=j-1;
					}
				}
				System.out.println();
			}

			lottery.setUserNumbers(userNumbers);
			lottery.calculateMatches();

			matches = lottery.getMatches();

			int lotteryWon = 0;
			for(int i = 0; i < matches.length; i++){
				System.out.println();
				System.out.println("You guessed correctly " + matches[i] + " number(s) on line " + (i+1) + ".");
				if(matches[i] == 6){
					lotteryWon++;
					if(lotteryWon == 1){
						System.out.println("You won the whole lottery!!! Congratulations! :-D \nYou can play again soon, but we're going to stop you being greedy. ;-)");
					} else if(lotteryWon > 1){
						System.out.println("You guessed all numbers again! But you already won the lottery :) ");
						}
					controlCounter = 1; //after this do-while loop completes, exit the game.
				}

			}

			lottery.calculateWinnings();
			winnings = lottery.getWinnings();
			linesWon = lottery.getLinesWon();
			totalWinnings = totalWinnings + winnings;

			System.out.println("\nYou won on " + linesWon + " line(s) and you won " + winnings + " euro in this game.");

			gameHistory[gameCounter-1][0] = noLines; //number of lines played per game
			gameHistory[gameCounter-1][1] = linesWon; //number of lines won per game(number of lines where 3 or more numbers matched) when we do the winnings calculation
			gameHistory[gameCounter-1][2] = winnings; //winnings per game

			if(controlCounter == 0){ //if user didn't win the lottery

				System.out.print("\nDo you want to play another game?\nPress 1 to play again or press 2 to exit: ");

				int commit = input.nextInt(); //we check what the user wants to do

				while (commit<1 || commit>2){ // data validation for the commit input
					System.out.print("That is not a valid number! Please press 1 to play again or press 2 to exit: "); //1 or 2 only
					commit = input.nextInt();
				}

				if( commit == 1){ //user wants to play again
					gameCounter++; //increments gamecounter variable
				} else { // user doesn't want to play again
					controlCounter = 1; //increment the control counter and closes the game loop
				} //this selection statement allows the user to exit from the whole-game while loop.
			} //this selection statement checks whether the user has already reached a game-ending state.


			lottery.calculateAverage(totalWinnings, gameCounter);
			aveWinnings = lottery.getAveWinnings();

		}while(controlCounter != 1); //whole game do-while loop ends

		//fetch history here

		System.out.println("\nThanks for playing! This is the end of the lottery game.");

		lotteryNumbers = lottery.getLottery(); //print the lottery numbers
		System.out.print("The lottery numbers were: ");
		for(int i=0; i< lotteryNumbers.length;i++){
			System.out.print(lotteryNumbers[i] + " ");
		}

		System.out.println("\nIn total, you played " + gameCounter + " game(s).");

		for(int i= 0; i<gameHistory.length;i++){ //display the number of lines played per game, the number of lines won per game, the total winnings per game
			if(gameHistory[i][0] != 0){
			System.out.println("\nFor game number " + (i+1) + ", you played " + gameHistory[i][0] + " line(s).\nYou won on " + gameHistory[i][1] + " line(s). \nYou won " + gameHistory[i][2] + " euro.");
			}
		}


		if(aveWinnings>0){ // if user won nothing, display meaningful output
			System.out.println("\nYour average winnings per game are " + aveWinnings + " euro.");
			System.out.println("Your total winnings across all games are " + totalWinnings + " euro.\n");
		}
		else{
			System.out.println("You won nothing in any game.\n");
		}

	}
}
