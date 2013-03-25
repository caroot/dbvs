package org.jboss.tools.examples.service;

import org.jboss.tools.examples.model.PhotoAlbum;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class PhotoAlbumRegistration {

   @Inject
   private Logger log;

   @Inject
   private EntityManager em;

   @Inject
   private Event<PhotoAlbum> photoAlbumEventSrc;

   public void register(PhotoAlbum photoAlbum) throws Exception {
      log.info("Registering " + photoAlbum.getName());
      log.info("Registering " + photoAlbum.getBeschreibung());
      log.info("Registering " + photoAlbum.getId());
      em.persist(photoAlbum);
      photoAlbumEventSrc.fire(photoAlbum);
   }
}
