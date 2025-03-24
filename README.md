new chnages

- updated ListStudent.jsp and added the delete button and the script
- updated ListAccount.jsp and added the delete button and the script
- Added DeleteConfirmation.jsp file
- Modified AccountResource.java and added getAccountByAlias, deleteAllAccounts and deleteAllStudentAccounts
- Modified BankDAO.java and added deleteAllUserAccounts, getAccountByAlias
- Modified Controller.java and added the case deleteStudent, deleteAccountConfirmation, deleteAllStudentAccounts

Function implementation
This commit contains following changes

- implementation of Deposit (changes were made into BankDAO, Controller.java, AccountResource.java, DespositOrWithdraw.jsp)
- Implementation of Withdraw (changes were made into BankDAO, Controller.java, AccountResource.java, DespositOrWithdraw.jsp)
- Implementation of Transfer (changes were made into BankDAO, Controller.java, AccountResource.java, transfer.jsp)
