package com.bank.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import com.bank.model.Account;
import com.bank.model.Student;

import java.util.List;
import java.util.Map;

import com.bank.dao.BankDAO;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/accounts")
public class AccountResource {
	@Inject
	private BankDAO bankDAO;
	
	//GET /api/accounts
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAccounts() {
        List<Account> accounts = bankDAO.getAllAccounts();
        return Response.ok(accounts).build();
    }

	@GET
    @Path("/{accountID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccount(@PathParam("accountID") int accountID) {
        Account account = bankDAO.getAccountByID(accountID);
        return Response.ok(account).build();
    }
	@GET
    @Path("/studentID/{accountID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountsByStudentID(@PathParam("accountID") int accountID, @PathParam("studentID") int studentID) {
        List<Account> accounts = bankDAO.getAccountsByStudentID(studentID);
        return Response.ok(accounts).build();
    }

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccount(Account account, @QueryParam("studentID") int studentID) {
        Student student = bankDAO.getStudentByID(studentID);
        if (student == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Student not found").build();
        }
        account.setStudent(student);
        bankDAO.createAccount(account);
        return Response.status(Response.Status.CREATED).entity(account).build();
    }
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAccount(@PathParam("id") int id, Account updatedAccount) {
    	if(bankDAO.updateAccount(updatedAccount)) {
    		return Response.status(Response.Status.CREATED).entity(updatedAccount).build();
    	}
    	
    	return Response.status(Response.Status.BAD_REQUEST).entity("Account ID" + id + " not found.").build();

    }
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAccount(@PathParam("id") int id) {
        boolean removed = bankDAO.deleteStudent(id);
        if (!removed) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response modifyAccountBalance(@PathParam("id") int accountId, Map<String, Object> payload) {
        try {
            String operation = payload.get("operation").toString();
            float amount = Float.parseFloat(payload.get("amount").toString());

            boolean success = false;
            if ("withdraw".equalsIgnoreCase(operation)) {
                success = bankDAO.withdraw(accountId, amount);
            } else if ("deposit".equalsIgnoreCase(operation)) {
                success = bankDAO.deposit(accountId, amount);
            }

            if (success) {
                return Response.ok(Map.of("message", operation + " successful")).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity(Map.of("error", operation + " failed"))
                               .build();
            }

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(Map.of("error", "Invalid input or internal error"))
                           .build();
        }
    }
    
    @PUT
    @Path("/transfer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response transferFunds(Map<String, Object> payload) {
        try {

        	//get the data first
            int fromAccountID = Integer.parseInt(payload.get("fromAccountID").toString());
            int toAccountID = Integer.parseInt(payload.get("toAccountID").toString());
            float amount = Float.parseFloat(payload.get("amount").toString());

            //do the transfer
            if (bankDAO.transfer(fromAccountID, toAccountID, amount)) {
                return Response.ok(Map.of("message", "Transfer successful")).build();
            } else {
               
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity(Map.of("error", "Transfer failed..."))
                               .build();
            }
        } catch (Exception e) {
            System.out.println("REST transfer error: " + e.getMessage()); // Debug
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(Map.of("error", "Invalid input or internal error"))
                           .build();
        }
    }
    
    
 // testing delete student account by student id
    @DELETE
    @Path("/student/{studentID}")
    public Response deleteAllStudentAccounts(@PathParam("studentID") int studentID) {
        List<Account> accounts = bankDAO.getAccountsByStudentID(studentID);
        if (accounts.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        for (Account account : accounts) {
            bankDAO.deleteAccount(account.getAccountID());
        }
        return Response.noContent().build();
    }

    // testing delete all accounts
    @DELETE
    public Response deleteAllAccounts() {
        List<Account> accounts = bankDAO.getAllAccounts();
        if (accounts.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        for (Account account : accounts) {
            bankDAO.deleteAccount(account.getAccountID());
        }
        return Response.noContent().build();
    }

    // testing get account by alias
    @GET
    @Path("/alias/{alias}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountByAlias(@PathParam("alias") String alias) {
        Account account = bankDAO.getAccountByAlias(alias);
        if (account == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(account).build();
    }

}
