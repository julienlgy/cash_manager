package eu.epitech.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class MapperAccount {
    @JsonProperty("accounts")
    List<Account> list = new ArrayList<Account>();

    public List<Account> getList() {
        return this.list;
    }
}
