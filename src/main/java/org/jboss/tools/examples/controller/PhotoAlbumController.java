package org.jboss.tools.examples.controller;

import java.util.logging.Logger;


import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.tools.examples.model.Member;
import org.jboss.tools.examples.model.PhotoAlbum;
import org.jboss.tools.examples.service.PhotoAlbumRegistration;

// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://sfwk.org/Documentation/WhatIsThePurposeOfTheModelAnnotation
@Model
public class PhotoAlbumController{

   @Inject
   private FacesContext facesContext;

   @Inject
   private PhotoAlbumRegistration photoAlbumRegistration;

   private PhotoAlbum newPhotoAlbum;

   @Produces
   @Named
   public PhotoAlbum getNewPhotoAlbum() {
      return newPhotoAlbum;
   }

   public void register() throws Exception {
       try {
           photoAlbumRegistration.register(newPhotoAlbum);
           FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
           facesContext.addMessage(null, m);
           initNewPhotoAlbum();
       } catch (Exception e) {
           String errorMessage = getRootErrorMessage(e);
           FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
           facesContext.addMessage(null, m);
       }
   }

   @PostConstruct
   public void initNewPhotoAlbum() {
      newPhotoAlbum = new PhotoAlbum();
   }
   
   private String getRootErrorMessage(Exception e) {
       // Default to general error message that registration failed.
       String errorMessage = "Registration failed. See server log for more information";
       if (e == null) {
           // This shouldn't happen, but return the default messages
           return errorMessage;
       }

       // Start with the exception and recurse to find the root cause
       Throwable t = e;
       while (t != null) {
           // Get the message from the Throwable class instance
           errorMessage = t.getLocalizedMessage();
           t = t.getCause();
       }
       // This is the root cause message
       return errorMessage;
   }
}
