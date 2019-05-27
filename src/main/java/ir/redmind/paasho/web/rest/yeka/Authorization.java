package ir.redmind.paasho.web.rest.yeka;

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
"_status",
"_datetime",
"data"
})
public class Authorization {

@JsonProperty("_status")
private String status;
@JsonProperty("_datetime")
private String datetime;
@JsonProperty("data")
private Data data;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("_status")
public String getStatus() {
return status;
}

@JsonProperty("_status")
public void setStatus(String status) {
this.status = status;
}

@JsonProperty("_datetime")
public String getDatetime() {
return datetime;
}

@JsonProperty("_datetime")
public void setDatetime(String datetime) {
this.datetime = datetime;
}

@JsonProperty("data")
public Data getData() {
return data;
}

@JsonProperty("data")
public void setData(Data data) {
this.data = data;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
