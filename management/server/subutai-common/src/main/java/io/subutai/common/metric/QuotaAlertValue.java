package io.subutai.common.metric;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;


/**
 * Quota alert class
 */
public class QuotaAlertValue implements AlertValue<ExceededQuota>
{
    protected static final Logger LOG = LoggerFactory.getLogger( QuotaAlertValue.class );

    private final ExceededQuota value;


    public QuotaAlertValue( @JsonProperty( "value" ) final ExceededQuota value )
    {
        this.value = value;
    }


    @Override
    @SuppressWarnings( "unchecked" )
    public ExceededQuota getValue()
    {
        return value;
    }


    @Override
    public boolean validate()
    {
        try
        {
            Preconditions.checkNotNull( value, "Value is null" );
            Preconditions.checkNotNull( value.getHostId(), "Host id is null" );
            Preconditions.checkNotNull( value.getHostId().getId(), "Host id is null" );
            Preconditions.checkNotNull( value.getContainerResourceType(), "Resource type is null" );
            Preconditions.checkNotNull( value.getCurrentValue(), "Current value is null" );
            Preconditions.checkNotNull( value.getQuotaValue(), "Quota value is null" );
        }
        catch ( Exception e )
        {
            LOG.warn( e.getMessage() );

            return false;
        }
        return true;
    }


    @Override
    public String toString()
    {
        return "QuotaAlertValue{" + "value=" + value + '}';
    }
}
