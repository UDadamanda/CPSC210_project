# Spending tracker

## What will the application do?

**Record money flow as user enter, include:**
- Amount of each spending
- Category and subcategory of the spending
- Date of spending
- Select money account
- Comment about the spending

*Some features:*
- Calculator when entering amount
- Multiple money account 
- Summary of spending for day/month/year: Pie chart showing proportion of each category, plot of spending trend...
- Set budget for each day/month/year, show money spent vs budget.


**Who will use it?**
- Everyone with need of tracking spending.


**Why is this project of interest to you?**
- I am a user of another money tracking application, but there are some features that I think is not necessary, and 
some that I want to add on. For example, the one I am using right now have very nice UI design, but too many other feature 
beside spending tracking. There are some summary feature that I would like to add on, but not currently available on the 
one I am using.

## User Stories
- As a user, I want to be able to add spending to my spending list.
- As a user, I want to be able to remove spending from my spending list
- As a user, I want to see a summary of my spending.
- As a user, when I select the quit option from the application menu, I want to be reminded to save my to-do list to file and have the option to do so or not. 
- As a user, when I start the application, I want to be given the option to load my to-do list from file.

## Instructions for Grader
- You can generate the add spending to a spending list by clicking "Add Spending" button, and input information about spending in the popup window.
- You can generate the remove spending from spending list by clicking "Remove Spending" button and input the index in the popup window.
- The visual component will appear in the upper main window
- You can save the state of my application by clicking "Save File" button, there will also be a saving reminder before quiting the program.
- You can reload the state of my application by clicking "Load File" button, there will also be a loading option when staring the program.

## Phase 4: Task 2
(Loading file with 2 Spending will result in 2 Added events, and clicking the remove button will automatically display a summary of all spending)

Tue Apr 11 00:50:34 PDT 2023

Spending Added

Tue Apr 11 00:50:34 PDT 2023

Spending Added

Tue Apr 11 00:50:53 PDT 2023

Summary Displayed

Tue Apr 11 00:51:05 PDT 2023

Spending Removed

Tue Apr 11 00:51:12 PDT 2023

Spending Added

Tue Apr 11 00:51:14 PDT 2023

Summary Displayed

## Phase 4: Task 3

There are too many coupling
- The association between Tool and SpendingList, QuitTool/SaveTool and JsonWriter is redundant.
- Instead of leading each tool to process load process, we can let each tool to call a method in GUI class and let GUI class to handle the process, and each tool will handle only the click. 
And the GUI will have a field JsonWriter.

I will also want to have a category class, and have a built-in category list or the user can build up their own category list. In each category, there will be a list of spending having that category, and the spending will have a field category. 
So that instead of typing a category each time, the user can choose from the categories. This decrease typo. Also, having a list of spending in category will make summary according to category faster and easier, since we don't need to loop every spending in spending list and look up for the same category.