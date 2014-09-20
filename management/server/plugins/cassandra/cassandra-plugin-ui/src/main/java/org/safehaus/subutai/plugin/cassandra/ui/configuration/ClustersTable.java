package org.safehaus.subutai.plugin.cassandra.ui.configuration;


import com.vaadin.ui.Button;
import com.vaadin.ui.Table;


public class ClustersTable extends Table
{


    public ClustersTable()
    {
        setSizeFull();
        addContainerProperty( "Environment", String.class, null );
        addContainerProperty( "Configure", Button.class, null );
    }


    public void refreshData( Object[] o )
    {
        addItem( o );
    }
}
