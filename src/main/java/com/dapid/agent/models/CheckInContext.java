package com.dapid.agent.models;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Payload for this agent/client registering itself with the server
 *
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class CheckInContext {
    @SerializedName("host_name")
    private String hostName;
    @SerializedName("self")
    private String fullUrlToSelf;

    public CheckInContext(String hostName, String fullUrlToSelf) {
        this.hostName = hostName;
        this.fullUrlToSelf = fullUrlToSelf;
    }

    public String getHostName() {
        return hostName;
    }

    public String getFullUrlToSelf() {
        return fullUrlToSelf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CheckInContext that = (CheckInContext) o;

        return new EqualsBuilder()
                .append(hostName, that.hostName)
                .append(fullUrlToSelf, that.fullUrlToSelf)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(hostName)
                .append(fullUrlToSelf)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("hostName", hostName)
                .append("fullUrlToSelf", fullUrlToSelf)
                .toString();
    }
}
