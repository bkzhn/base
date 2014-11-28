package org.safehaus.subutai.core.peer.impl.dao;


import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.safehaus.subutai.common.protocol.api.DataService;
import org.safehaus.subutai.core.peer.api.ContainerHost;
import org.safehaus.subutai.core.peer.impl.model.ResourceHostEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;


public class ResourceHostDataService implements DataService<String, ResourceHostEntity>
{
    private static final Logger LOG = LoggerFactory.getLogger( ResourceHostDataService.class );
    EntityManagerFactory emf;


    public ResourceHostDataService( EntityManagerFactory entityManagerFactory )
    {
        this.emf = entityManagerFactory;
    }


    public void setEntityManagerFactory( final EntityManagerFactory emf )
    {
        this.emf = emf;
    }


    @Override
    public ResourceHostEntity find( final String id )
    {
        ResourceHostEntity result = null;
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            result = em.find( ResourceHostEntity.class, id );
            em.getTransaction().commit();
        }
        catch ( Exception e )
        {
            LOG.error( e.toString(), e );
            if ( em.getTransaction().isActive() )
            {
                em.getTransaction().rollback();
            }
        }
        finally
        {
            em.close();
        }
        return result;
    }


    @Override
    public Collection<ResourceHostEntity> getAll()
    {
        Collection<ResourceHostEntity> result = Lists.newArrayList();
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            result = em.createQuery( "select h from ResourceHostEntity h", ResourceHostEntity.class ).getResultList();
            em.getTransaction().commit();
        }
        catch ( Exception e )
        {
            LOG.error( e.toString(), e );
            if ( em.getTransaction().isActive() )
            {
                em.getTransaction().rollback();
            }
        }
        finally
        {
            em.close();
        }
        return result;
    }


    @Override
    public void persist( final ResourceHostEntity item )
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            em.persist( item );
            em.flush();
            em.getTransaction().commit();
        }
        catch ( Exception e )
        {
            LOG.error( e.toString(), e );
            if ( em.getTransaction().isActive() )
            {
                em.getTransaction().rollback();
            }
        }
        finally
        {
            em.close();
        }
    }


    @Override
    public void remove( final ResourceHostEntity item )
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            em.remove( item );
            em.getTransaction().commit();
        }
        catch ( Exception e )
        {
            LOG.error( e.toString(), e );
            if ( em.getTransaction().isActive() )
            {
                em.getTransaction().rollback();
            }
        }
        finally
        {
            em.close();
        }
    }


    @Override
    public void update( final ResourceHostEntity item )
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            em.merge( item );
            em.getTransaction().commit();
        }
        catch ( Exception e )
        {
            LOG.error( e.toString(), e );
            if ( em.getTransaction().isActive() )
            {
                em.getTransaction().rollback();
            }
        }
        finally
        {
            em.close();
        }
    }


    public void removeContainer( final ResourceHostEntity item, final ContainerHost containerHost )
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            em.merge( item );
            item.removeContainerHost( containerHost );
            em.getTransaction().commit();
        }
        catch ( Exception e )
        {
            LOG.error( e.toString(), e );
            if ( em.getTransaction().isActive() )
            {
                em.getTransaction().rollback();
            }
        }
        finally
        {
            em.close();
        }
    }
}
