package com.techelevator;

import com.techelevator.model.Vendable;
import com.techelevator.model.VendingMachine;
import com.techelevator.view.VendingMenu;



public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String MAIN_MENU_SECRET_OPTION = "*Sales Report";

	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";

	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT, MAIN_MENU_SECRET_OPTION };
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION};

	private VendingMenu menu;

	public VendingMachineCLI(VendingMenu menu) {
		this.menu = menu;
	}

	public void run() {
		boolean running = true;
		VendingMachine vendingMachine = new VendingMachine();

		vendingMachine.setProductsList(vendingMachine.createMap());


		//System.out.println(vendingMachine.getProductsList().get("A1").getItemCost());

		while (running) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

//			 A switch statement could also be used here.  Your choice.
			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				vendingMachine.printMenu();

//				 display vending machine items
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				String choice2 = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

				switch (choice2) {
					case "Feed Money":
						vendingMachine.feedMoney();
						break;
					case "Select Product":
						vendingMachine.makePurchase();
						break;
					case "Finish Transaction":
						vendingMachine.makeChange();
						System.out.println("Thank you!");
						break;
				}
			} else if(choice.equals(MAIN_MENU_SECRET_OPTION)) {
				System.out.printf("Congrats! We made $%4.2f in sales.", vendingMachine.getSales());

			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)){
				vendingMachine.makeChange();
				break;
		}
		}
	}

	public static void main(String[] args) {
		VendingMenu menu = new VendingMenu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
