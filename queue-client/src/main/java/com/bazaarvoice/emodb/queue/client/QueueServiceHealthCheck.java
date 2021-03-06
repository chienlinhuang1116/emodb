package com.bazaarvoice.emodb.queue.client;

import com.bazaarvoice.emodb.queue.api.AuthQueueService;
import com.bazaarvoice.emodb.queue.api.QueueService;
import com.bazaarvoice.ostrich.dropwizard.healthcheck.ContainsHealthyEndPointCheck;
import com.bazaarvoice.ostrich.pool.ServicePoolProxies;

/**
 * Dropwizard health check.
 */
public class QueueServiceHealthCheck {
    public static ContainsHealthyEndPointCheck create(QueueService queueService) {
        return ContainsHealthyEndPointCheck.forPool(ServicePoolProxies.getPool(toServicePoolProxy(queueService)));
    }

    public static ContainsHealthyEndPointCheck create(AuthQueueService authQueueService) {
        return ContainsHealthyEndPointCheck.forPool(ServicePoolProxies.getPool(authQueueService));
    }

    private static Object toServicePoolProxy(QueueService queueService) {
        if (queueService instanceof QueueServiceAuthenticatorProxy) {
            return ((QueueServiceAuthenticatorProxy) queueService).getProxiedInstance();
        }
        return queueService;
    }
}
