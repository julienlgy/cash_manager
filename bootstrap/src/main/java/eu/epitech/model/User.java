package eu.epitech.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
})
public class User {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    public Integer getId() {
        return this.id;
    }
    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return this.name;
    }
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }
}
