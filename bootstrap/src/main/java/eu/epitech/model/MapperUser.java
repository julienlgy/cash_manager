package eu.epitech.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class MapperUser {
    @JsonProperty("users")
    List<User> list = new ArrayList<User>();

    public List<User> getList() {
        return this.list;
    }
}
