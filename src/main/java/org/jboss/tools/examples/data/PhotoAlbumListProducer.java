package org.jboss.tools.examples.data;

import org.jboss.tools.examples.model.PhotoAlbum;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@RequestScoped
public class PhotoAlbumListProducer {
   @Inject
   private EntityManager em;

   private List<PhotoAlbum> photoAlbums;

   // @Named provides access the return value via the EL variable name "members" in the UI (e.g.,
   // Facelets or JSP view)
   @Produces
   @Named
   public List<PhotoAlbum> getPhotoAlbums() {
      return photoAlbums;
   }

   public void onPhotoAlbumListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final PhotoAlbum photoAlbum) {
      retrieveAllPhotoAlbumsOrderedByName();
   }

   @PostConstruct
   public void retrieveAllPhotoAlbumsOrderedByName() {
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<PhotoAlbum> criteria = cb.createQuery(PhotoAlbum.class);
      Root<PhotoAlbum> photoAlbum = criteria.from(PhotoAlbum.class);
      // Swap criteria statements if you would like to try out type-safe criteria queries, a new
      // feature in JPA 2.0
      // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
      criteria.select(photoAlbum).orderBy(cb.asc(photoAlbum.get("name")));
      photoAlbums = em.createQuery(criteria).getResultList();
   }
}
