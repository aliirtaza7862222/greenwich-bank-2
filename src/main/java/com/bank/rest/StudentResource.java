package com.bank.rest;

import com.bank.dao.BankDAO;
import com.bank.model.Student;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/students")
public class StudentResource {

	@Inject
	private BankDAO bankDAO;
	
	//GET /api/students
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Student> getAllStudents(){
		return bankDAO.getAllStudents();
	}
	
	//GET /api/students/{student_id}
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    public Response getStudentById(@PathParam("id") int id) {
        Student student = bankDAO.getStudentByID(id);
        if (student == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(student).build();
    }
	// POST /api/students
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createStudent(Student student) {
    	if(bankDAO.createStudent(student)) {
    		return Response.status(Response.Status.CREATED).entity(student).build();
    	} 
    	else { 
    		return Response.status(Response.Status.BAD_REQUEST).entity(student).build();
    	}
    }

 // PUT /api/students/{id}
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStudent(@PathParam("id") int id, Student updatedStudent) {
    	if(bankDAO.updateStudent(updatedStudent)) {
    		return Response.status(Response.Status.CREATED).entity(updatedStudent).build();
    	}
    	
    	return Response.status(Response.Status.BAD_REQUEST).entity("Student ID" + id + " not found.").build();
    }
    
 // DELETE /api/students/{id}
    @DELETE
    @Path("/{id}")
    public Response deleteStudent(@PathParam("id") int id) {
        boolean removed = bankDAO.deleteStudent(id);
        if (!removed) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }

/* TODO: implement CRUD Operations for Accounts
 * Create Account for Student
 * Update Account
 * Delete Account
 * Delete all user accounts
 * Delete all accounts
 * getAllAccounts
 * getAccountByID
 * getAccountByUserID
 * getAccountByAlias?
 * 
 * 
 */
}
