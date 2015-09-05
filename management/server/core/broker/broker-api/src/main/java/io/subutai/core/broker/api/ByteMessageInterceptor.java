package io.subutai.core.broker.api;


/**
 * Allows clients to intercept messages before they are dispatched to message listeners or sent to destinations.
 *
 * Messages can be altered by interceptors.
 */
public interface ByteMessageInterceptor
{
    /**
     * @param message - the message
     *
     * @return - either the original message or an altered version of the original message
     */
    public byte[] process( String topic, byte[] message );
}