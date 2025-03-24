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
        // TODO Auto-generated constructor stub
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
		    	response.sendRedirect("");
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
			int studentID = 0;
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
			    for (Cookie cookie : cookies) {
			        if ("studentID".equals(cookie.getName())) {
			            studentID = Integer.valueOf(cookie.getValue());
			        }
			    }
			}
			if(studentID != 0) {
				account.setStudent(bankDAO.getStudentByID(studentID));
				System.out.println("Account being created...");
				bankDAO.createAccount(account);
				response.sendRedirect("");
			} else {response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Student not logged in or recognized");}
			
			break;
		case "deleteAccount":
			String accountIDParam = request.getParameter("accountID");
			if (accountIDParam != null) {
				int accountID = Integer.parseInt(accountIDParam);
				Account accToDelete = bankDAO.getAccountByID(accountID);
				if (accToDelete != null && bankDAO.deleteAccount(accountID)) {
					response.setStatus(HttpServletResponse.SC_OK);
				} else { 
		            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Account not found or deleted.");
				}
			} else {
		        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing account ID.");
		    }
			break;
		case "transfer":
//			testing transfers
			 handleTransfer(request, response);
			break;
		case "withdraw":
			//testing withdraw
			 handleWithdraw(request, response);
			break;
			
			// testing deposit
		case "deposit":
            handleDeposit(request, response);
            break;
		default:
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown POST action: " + action);
		}

	}

	private void processGETRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		List<Student> students;
		List<Account> accounts = bankDAO.getAllAccounts();
		Cookie[] cookies;
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
			studentID = 0;
			cookies = request.getCookies();
			if (cookies != null) {
			    for (Cookie cookie : cookies) {
			        if ("studentID".equals(cookie.getName())) {
			            studentID = Integer.valueOf(cookie.getValue());
			            System.out.println("Student ID from cookie: " + studentID);
			        }
			    }
			}

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
			
		case "viewAccount":
		    int id = Integer.parseInt(request.getParameter("id"));
		    account = bankDAO.getAccountByID(id);
		    request.setAttribute("account", account);
		    request.getRequestDispatcher("jsp/accounts/ViewAccount.jsp").forward(request, response);
		    break;

// --------------- Business Logic ---------------------

		case "transfer":
			request.getRequestDispatcher("jsp/transactions/Transfer.jsp").forward(request, response);
			break;

		case "depositOrWithdraw":
			cookies = request.getCookies();
		    if (cookies != null) {
		        for (Cookie cookie : cookies) {
		            if ("studentID".equals(cookie.getName())) {
		                studentID = Integer.parseInt(cookie.getValue());
		                break;
		            }
		        }
		    }
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
	
	// function for deposit
	private void handleDeposit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
	    	
	    	// for debuggingg g
	        System.out.println("deposit request...");
	        System.out.println("account ID: " + request.getParameter("accountID"));
	        System.out.println("Amount: " + request.getParameter("amount"));
	        
	        
	        // Get account ID and amount from the request
	        int accountID = Integer.parseInt(request.getParameter("accountID"));
	        float amount = Float.parseFloat(request.getParameter("amount"));

	        if (bankDAO.deposit(accountID, amount)) {
	            response.sendRedirect("Controller?action=listAccounts");
	        } else {
	            request.setAttribute("error", "Deposit failed....");
	            request.getRequestDispatcher("jsp/transactions/DepositOrWithdraw.jsp").forward(request, response);
	        }
	    } catch (NumberFormatException e) {
	        request.setAttribute("error", "Invalid input...");
	        request.getRequestDispatcher("jsp/transactions/DepositOrWithdraw.jsp").forward(request, response);
	    }
	}
	
	private void handleWithdraw(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
	        // for debugging
	        System.out.println("withdraw request...");
	        System.out.println("Account ID: " + request.getParameter("accountID"));
	        System.out.println("Amount: " + request.getParameter("amount"));

	        int accountID = Integer.parseInt(request.getParameter("accountID"));
	        float amount = Float.parseFloat(request.getParameter("amount"));

	        if (bankDAO.withdraw(accountID, amount)) {
	            response.sendRedirect("Controller?action=listAccounts");
	        } else {
	            request.setAttribute("error", "Withdrawal failed...");
	            request.getRequestDispatcher("jsp/transactions/DepositOrWithdraw.jsp").forward(request, response);
	        }
	    } catch (NumberFormatException e) {
	        System.out.println("Invalid input: " + e.getMessage()); // Debug
	        request.setAttribute("error", "Invalid input.");
	        request.getRequestDispatcher("jsp/transactions/DepositOrWithdraw.jsp").forward(request, response);
	    }
	}
	
	private void handleTransfer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
	        // for debugging
	        System.out.println("Handling transfer request...");
	        System.out.println("From Account ID: " + request.getParameter("fromAccountID"));
	        System.out.println("To Account ID: " + request.getParameter("toAccountID"));
	        System.out.println("Amount: " + request.getParameter("amount"));

	        // get the data from reqqq
	        int fromAccountID = Integer.parseInt(request.getParameter("fromAccountID"));
	        int toAccountID = Integer.parseInt(request.getParameter("toAccountID"));
	        float amount = Float.parseFloat(request.getParameter("amount"));

	        if (bankDAO.transfer(fromAccountID, toAccountID, amount)) {
	            response.sendRedirect("Controller?action=listAccounts");
	        } else {
	            request.setAttribute("error", "Transfer failed...");
	            request.getRequestDispatcher("jsp/transactions/transfer.jsp").forward(request, response);
	        }
	    } catch (NumberFormatException e) {
	        request.setAttribute("error", "Invalid input. Please enter valid account IDs and amount.");
	        request.getRequestDispatcher("jsp/transactions/transfer.jsp").forward(request, response);
	    }
	}
}
