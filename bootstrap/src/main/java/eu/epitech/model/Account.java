package eu.epitech.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "accountOwner",
        "cash"
})
public class Account {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("accountOwner")
    private Integer accountOwner;

    @JsonProperty("cash")
    private Float cash;

    @JsonProperty("accountOwner")
    public void setAccountOwner(Integer accountOwner) {
        this.accountOwner = accountOwner;
    }

    @JsonProperty("accountOwner")
    public Integer getAccountOwner() {
        return this.accountOwner;
    }

    @JsonProperty("cash")
    public Float getCash() {
        return this.cash;
    }

    @JsonProperty("cash")
    public void setCash(Float cash) {
        this.cash = cash;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("id")
    public Integer getId() {
        return this.id;
    }

    public void show() {
        System.out.println("His name is "+this.accountOwner+" and he as "+this.cash+"$ in his account");
    }

}
