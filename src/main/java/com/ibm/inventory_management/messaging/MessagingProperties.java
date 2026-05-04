package com.ibm.inventory_management.messaging;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "inventory.messaging")
public class MessagingProperties {

    private String exchange;
    private String routingKey;
    private String queue;
    private String dlx;
    private String dlq;
    private String dlqRoutingKey;

    public String getExchange() { return exchange; }
    public void setExchange(String exchange) { this.exchange = exchange; }

    public String getRoutingKey() { return routingKey; }
    public void setRoutingKey(String routingKey) { this.routingKey = routingKey; }

    public String getQueue() { return queue; }
    public void setQueue(String queue) { this.queue = queue; }

    public String getDlx() { return dlx; }
    public void setDlx(String dlx) { this.dlx = dlx; }

    public String getDlq() { return dlq; }
    public void setDlq(String dlq) { this.dlq = dlq; }

    public String getDlqRoutingKey() { return dlqRoutingKey; }
    public void setDlqRoutingKey(String dlqRoutingKey) { this.dlqRoutingKey = dlqRoutingKey; }
}
