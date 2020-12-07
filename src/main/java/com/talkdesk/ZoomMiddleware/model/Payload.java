
package com.talkdesk.ZoomMiddleware.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "account_id",
    "object"
})
public class Payload {

    @JsonProperty("account_id")
    private String accountId;
    @JsonProperty("object")
    private com.talkdesk.ZoomMiddleware.model.Object object;
    @JsonIgnore
    private Map<String, java.lang.Object> additionalProperties = new HashMap<String, java.lang.Object>();

    @JsonProperty("account_id")
    public String getAccountId() {
        return accountId;
    }

    @JsonProperty("account_id")
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @JsonProperty("object")
    public com.talkdesk.ZoomMiddleware.model.Object getObject() {
        return object;
    }

    @JsonProperty("object")
    public void setObject(com.talkdesk.ZoomMiddleware.model.Object object) {
        this.object = object;
    }

    @JsonAnyGetter
    public Map<String, java.lang.Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, java.lang.Object value) {
        this.additionalProperties.put(name, value);
    }

}
