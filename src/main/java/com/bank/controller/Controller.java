package com.bank.controller;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.bank.dao.BankDAO;
import com.bank.model.*;
import java.util.List;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
    private BankDAO bankDAO;  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processGETRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processPOSTRequest(request, response);
	}

	private void processPOSTRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		float amount;
		switch(action)	{
		/*
		 * Handle POST Requests here:
		 */
		case "addStudent":
		    // Handle form data and save
		    Student student = new Student();
		    System.out.println("Student being created...");
		    student.setStudentName(request.getParameter("name"));
		    student.setStudentEmail(request.getParameter("email"));
		    student.setStudentPhone(request.getParameter("phone"));
		    student.setStudentAddress(request.getParameter("address"));
		    if(bankDAO.createStudent(student)) {
		    	response.sendRedirect("/Bank/");
		    }
		    else {
		    	request.setAttribute("error", "Could not add student.");
		    	request.getRequestDispatcher("Controller?action=listStudents").forward(request, response);
		    }
		    break;
		case "addAccount":
			Account account = new Account();
			account.setAccountAlias(request.getParameter("accountAlias"));
			account.setAccountBalance(0.0f);
			int studentID = getStudentIdFromCookie(request);
			if(studentID != 0) {
				account.setStudent(bankDAO.getStudentByID(studentID));
				System.out.println("Account being created...");
				bankDAO.createAccount(account);
				response.sendRedirect("/Bank/");
			} else {response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Student not logged in or recognized");}
			
			break;
		case "deleteAccount":
			String accountIDParam = request.getParameter("accountID");
			if (accountIDParam != null) {
				int accountID = Integer.parseInt(accountIDParam);
				Account accToDelete = bankDAO.getAccountByID(accountID);
				if (accToDelete != null && bankDAO.deleteAccount(accountID)) {
					response.setStatus(HttpServletResponse.SC_OK);
					request.getRequestDispatcher("/Bank/");
					response.sendRedirect("/Bank/");
					} else { 
		            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Account not found or deleted.");
				}
			} else {
		        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing account ID.");
		    }
			break;
			

		// testing delete all student accounts
		case "deleteAllStudentAccounts":
		    studentID = getStudentIdFromCookie(request);
		    if (studentID != 0) {
		        List<Account> accounts = bankDAO.getAccountsByStudentID(studentID);
		        for (Account acc : accounts) {
		            bankDAO.deleteAccount(acc.getAccountID());
		        }
		        response.sendRedirect("Controller?action=listAccounts");
		    } else {
		        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Student not logged in.");
		    }
		    break;
		case "transfer":
	        int fromAccountID = Integer.parseInt(request.getParameter("fromAccountID"));
	        int toAccountID = Integer.parseInt(request.getParameter("toAccountID"));
	        amount = Float.parseFloat(request.getParameter("amount"));
	        if((amount <= bankDAO.getAccountByID(fromAccountID).getAccountBalance()) 
	        		&& bankDAO.transfer(fromAccountID, toAccountID, amount)) {
					request.setAttribute("fromAccount", bankDAO.getAccountByID(fromAccountID));
					request.setAttribute("toAccount", bankDAO.getAccountByID(toAccountID));
					request.setAttribute("amount", amount);
					request.setAttribute("transaction", "transfer");
					request.getRequestDispatcher("jsp/transactions/TransactionResult.jsp").forward(request, response);
	        } else {
					request.setAttribute("error", "Transfer failed...");
					request.setAttribute("transaction", "transfer");
					request.getRequestDispatcher("jsp/transactions/TransactionResult.jsp").forward(request, response);
	        }

			break;
		case "depositOrWithdraw":
			//get form parameters
			String transaction = request.getParameter("transaction");
			int accountID = Integer.parseInt(request.getParameter("accountID"));
			amount = Float.parseFloat(request.getParameter("amount"));
			if (transaction.equals("withdraw")) {
				if((amount <= bankDAO.getAccountByID(accountID).getAccountBalance()) 
						&& bankDAO.withdraw(accountID, amount) 
						) {
					//return Account Object if successful to Transaction Result
					request.setAttribute("account", bankDAO.getAccountByID(accountID));
					request.setAttribute("transaction", "withdraw");
					request.getRequestDispatcher("jsp/transactions/TransactionResult.jsp").forward(request, response);
					
				} else {
					request.setAttribute("error", "Withdrawal failed...");
					request.setAttribute("transaction", "withdraw");
					request.getRequestDispatcher("jsp/transactions/TransactionResult.jsp").forward(request, response);
				}
				
			} else if (transaction.equals("deposit")) {
				if(bankDAO.deposit(accountID, amount)) {
					//return Account Object if successful to Transaction Result
					request.setAttribute("account", bankDAO.getAccountByID(accountID));
					request.setAttribute("transaction", "deposit");
					request.getRequestDispatcher("jsp/transactions/TransactionResult.jsp").forward(request, response);
					
				} else {
					request.setAttribute("error", "Deposit failed...");
					request.setAttribute("transaction", "deposit");
					request.getRequestDispatcher("jsp/transactions/TransactionResult.jsp").forward(request, response);
				}
			} else {
				response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "Unknown parameter in transaction");
			}
			break;
		
		default:
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown POST action: " + action);
		}

	}

	private void processGETRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		List<Student> students;
		List<Account> accounts = bankDAO.getAllAccounts();
		Student student;
		Account account;
		int studentID = 0;
		//Handle Actions here:
		switch(action) {
		/*
		 * Handle GET Requests Here
		 */
	
// --------------- Home Page ---------------------
		case "home":
            request.getRequestDispatcher("jsp/home/home.jsp").forward(request, response);
			break;

		case "login":
			students = bankDAO.getAllStudents();
		    request.setAttribute("students", students);
		    request.getRequestDispatcher("jsp/home/login.jsp").forward(request, response);			
		    break;

// --------------- Students ---------------------
		case "listStudents":
			//do the listing of the students
			students = bankDAO.getAllStudents();
            request.setAttribute("Students", students);
            request.getRequestDispatcher("jsp/students/ListStudents.jsp").forward(request, response);
            break;

		case "addStudent":
			request.getRequestDispatcher("jsp/students/AddStudent.jsp").forward(request, response);
			break;
// --------------- Accounts ---------------------
		case "listAccounts": // Lists all accounts if not logged in, otherwise lists student accounts
			studentID = getStudentIdFromCookie(request);

			if (studentID != 0) {
			    // Load only accounts for this student
			    accounts = bankDAO.getAccountsByStudentID(studentID);
			    request.setAttribute("Accounts", accounts);
			} else {
			    // Load all accounts
			    accounts = bankDAO.getAllAccounts();
			    request.setAttribute("Accounts", accounts);
			}			
			request.getRequestDispatcher("jsp/accounts/ListAccounts.jsp").forward(request, response);
			break;

		case "addAccount": // Web Page for creating a new Account
			request.getRequestDispatcher("jsp/accounts/AddAccount.jsp").forward(request, response);
			break;

		case "deleteAccount": // Delete
			break;
		case "deleteStudent":
		    int studentId = Integer.parseInt(request.getParameter("id"));
		    if (bankDAO.deleteStudent(studentId)) {
		        response.sendRedirect("Controller?action=listStudents");
		    } else {
		        request.setAttribute("error", "Failed to delete student");
		        request.getRequestDispatcher("Controller?action=listStudents").forward(request, response);
		    }
		    break;	
		case "viewAccount":
		    int id = Integer.parseInt(request.getParameter("id"));
		    account = bankDAO.getAccountByID(id);
		    request.setAttribute("account", account);
		    request.getRequestDispatcher("jsp/accounts/ViewAccount.jsp").forward(request, response);
		    break;
		 // testing get account delete confirmation
		case "deleteAccountConfirmation":
			int accountId = Integer.parseInt(request.getParameter("id"));
			account = bankDAO.getAccountByID(accountId);
			request.setAttribute("account", account);
			request.getRequestDispatcher("jsp/accounts/DeleteAccountConfirmation.jsp").forward(request, response);
		 	break;

// --------------- Business Logic ---------------------

		case "transfer":
			studentID = getStudentIdFromCookie(request);
			accounts = bankDAO.getAccountsByStudentID(studentID);
			request.setAttribute("Accounts", accounts);
			request.getRequestDispatcher("jsp/transactions/Transfer.jsp").forward(request, response);
			break;

		case "depositOrWithdraw":
			studentID = getStudentIdFromCookie(request);
		    if (studentID != 0) {
		        accounts = bankDAO.getAccountsByStudentID(studentID);
		        request.setAttribute("Accounts", accounts);
		    } else {
		        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Student not logged in.");
		    }
			request.getRequestDispatcher("jsp/transactions/DepositOrWithdraw.jsp").forward(request, response);
			break;
		//etc...
		}
	}
	
	private int getStudentIdFromCookie(HttpServletRequest request) {
		int studentID = 0;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
		    for (Cookie cookie : cookies) {
		        if ("studentID".equals(cookie.getName())) {
		            studentID = Integer.valueOf(cookie.getValue());
		        }
		    }
		}	
		return studentID;
	}
	
}
