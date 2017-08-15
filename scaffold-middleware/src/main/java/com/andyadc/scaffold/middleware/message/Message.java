package com.andyadc.scaffold.middleware.message;

import java.util.Date;

/**
 * @author andy.an
 * @since 2017/8/15
 */
public abstract class Message<T> {

    private String messageId;
    private Date sendTime;
    private String appId;
    private String eventType;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public abstract T getBody();

    public abstract void setBody(T body);

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Message {");
        sb.append("messageId='").append(messageId).append('\'');
        sb.append(", sendTime=").append(sendTime);
        sb.append(", appId='").append(appId).append('\'');
        sb.append(", eventType='").append(eventType).append('\'');
        sb.append(", body='").append(getBody()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
