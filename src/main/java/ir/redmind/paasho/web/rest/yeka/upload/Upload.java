package ir.redmind.paasho.web.rest.yeka.upload;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"response",
"data",
"_status",
"_datetime"
})
public class Upload {

@JsonProperty("response")
private String response;
@JsonProperty("data")
private List<Datum> data = null;
@JsonProperty("_status")
private String status;
@JsonProperty("_datetime")
private String datetime;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("response")
public String getResponse() {
return response;
}

@JsonProperty("response")
public void setResponse(String response) {
this.response = response;
}

@JsonProperty("data")
public List<Datum> getData() {
return data;
}

@JsonProperty("data")
public void setData(List<Datum> data) {
this.data = data;
}

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

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
