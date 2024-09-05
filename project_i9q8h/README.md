# My Personal Project

## Expense Tracker

**My application will track a person's purchases and their expenses.**
Anyone who wants extra assistance in managing their finances or
spending habits can use this application. This project is of interest
to me because many of my peers, as well as myself, struggle to manually
keep track of and calculate expenses. Therefore, having an application 
to do so would be more convenient.

## User Stories
- As a user, I want to be able to add the name of a purchase and 
its price to my list of expenses
- As a user, I want to be able to view my list of expenses and the
total cost
- As a user, I want to be able to remove a purchase from my list of
expenses
- As a user, I want to be able to view my total money accumulation, 
and how much my current balance is after deducting expenses
- As a user, I want to be able to add my name to my personal expenses list and wallet
####
- As a user, I want to be able to save my expenses list to a file (if I so choose)
- As a user, I want to be able to load my expenses list from file, with the current expenses 
and wallet balance saved previously (if I so choose)

## Instructions For Grader
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by
clicking "Manage List". In the list menu, enter the name of the expense in the top left text field, then 
enter its price in the top right text field, before clicking "Add Expense" to add the expense to your list.
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by 
clicking "Manage List". In the list menu, enter the name of an existing expense in your list in the bottom 
left text field, then enter its price in the bottom right text field, before clicking "Remove Expense" to 
remove the expense from your list.
- You can locate my visual component by clicking "Save", and a picture will be displayed in the dialogue message.
- You can save the state of my application by clicking "Save" at the top from the main menu.
- You can reload the state of my application by clicking "Load" at the top from the main menu.

 ## Phase 4: Task 2
Sun Nov 26 17:17:47 PST 2023
5.0$ removed from wallet balance.
Sun Nov 26 17:17:47 PST 2023
'apples' added to list.
Sun Nov 26 17:17:57 PST 2023
25.5$ removed from wallet balance.
Sun Nov 26 17:17:57 PST 2023
'shirt' added to list.
Sun Nov 26 17:18:07 PST 2023
100.0$ added to wallet balance.

## Phase 4: Task 3
Some refactoring I might consider to improve my design is to implement the Observer pattern to reduce coupling. 
For instance, since there are three association arrows drawn towards the Wallet class in the UML diagram, there 
appears to be high coupling between the Wallet class and other classes. To reduce this coupling, I might want to 
make the WalletUI class the observable, and the Wallet and WalletText classes my observers, which update the status
of the text or balance based on what's changed in the WalletUI by the user. To do this in the code, I would consider 
making Wallet and WalletText implement an Observer interface, and abstract class Menu extend an Observable class, since
WalletUI already extends Menu. From this change, I believe the association arrow pointing from WalletText to Wallet 
would be removed, which would reduce coupling. The same can also be done and said for the ExpensesList class, which also
has three association arrows pointing towards it.