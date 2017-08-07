package br.com.spread.controller;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.spread.model.AbstractModel;
import br.com.spread.service.BaseServiceImpl;

@Component
public class BaseController<T extends AbstractModel<Long>, Long extends Serializable> {

	private BaseServiceImpl<T, Long> service;

	public BaseServiceImpl<T, Long> getService() {
		return service;
	}

	/*
	 * Get object by Id
	 * */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public T get(@PathParam("id") Long id) {
		return getService().get((java.lang.Long) id);
	}

	/*
	 * Get all object
	 * */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<T> findAll() {
		return getService().findAll();
	}

	/*
	 * Method to save the entities
	 */
	/*
	 * @POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response save(T entity , @Context HttpServletRequest request) {

		if (entity == null) {
			return Response.noContent().build();
		} else {

			entity = getService().save(entity);

			if (entity.getId() != null) {
				URI uri = URI.create(request.getRequestURI() + "/" + entity.getId());
				return Response.created(uri).build();
			} else {
				return Response.noContent().build();
			}
		}
	}
	*/
	
	/*
	 * Method to update the entities
	 */
	@Path("{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(T entity, @PathParam("id") Long id) {
		
		if (entity == null) {
			return Response.noContent().build();
		} else {

			try{
				getService().update(entity);
				return Response.ok().build();
			}catch (Exception e) {
				return Response.notModified().build();
			}
			
		}
    }
	
	/*
	 * Method to delete all entities
	 */
	@DELETE
	public Response deleteAll() {
		
		if(getService().deleteAll().equals(HttpStatus.OK)){
			return Response.ok().build();
		}else{
			return Response.serverError().build();
		}
	}
	
	/*
	 * Method to delete specific entity
	 */
	@Path("{id}")
	@DELETE
	public Response delete(@PathParam("id") Long id) {
		
		if(getService().delete((java.lang.Long) id).equals(HttpStatus.OK)){
			return Response.ok().build();
		}else{
			return Response.serverError().build();
		}
	}
	
}
