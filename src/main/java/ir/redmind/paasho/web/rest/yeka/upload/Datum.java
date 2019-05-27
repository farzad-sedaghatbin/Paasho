package ir.redmind.paasho.web.rest.yeka.upload;

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
"name",
"size",
"type",
"error",
"url",
"delete_url",
"info_url",
"delete_type",
"delete_hash",
"hash",
"stats_url",
"short_url",
"file_id",
"unique_hash",
"url_html",
"url_bbcode"
})
public class Datum {

@JsonProperty("name")
private String name;
@JsonProperty("size")
private String size;
@JsonProperty("type")
private String type;
@JsonProperty("error")
private Object error;
@JsonProperty("url")
private String url;
@JsonProperty("delete_url")
private String deleteUrl;
@JsonProperty("info_url")
private String infoUrl;
@JsonProperty("delete_type")
private String deleteType;
@JsonProperty("delete_hash")
private String deleteHash;
@JsonProperty("hash")
private String hash;
@JsonProperty("stats_url")
private String statsUrl;
@JsonProperty("short_url")
private String shortUrl;
@JsonProperty("file_id")
private String fileId;
@JsonProperty("unique_hash")
private String uniqueHash;
@JsonProperty("url_html")
private String urlHtml;
@JsonProperty("url_bbcode")
private String urlBbcode;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("name")
public String getName() {
return name;
}

@JsonProperty("name")
public void setName(String name) {
this.name = name;
}

@JsonProperty("size")
public String getSize() {
return size;
}

@JsonProperty("size")
public void setSize(String size) {
this.size = size;
}

@JsonProperty("type")
public String getType() {
return type;
}

@JsonProperty("type")
public void setType(String type) {
this.type = type;
}

@JsonProperty("error")
public Object getError() {
return error;
}

@JsonProperty("error")
public void setError(Object error) {
this.error = error;
}

@JsonProperty("url")
public String getUrl() {
return url;
}

@JsonProperty("url")
public void setUrl(String url) {
this.url = url;
}

@JsonProperty("delete_url")
public String getDeleteUrl() {
return deleteUrl;
}

@JsonProperty("delete_url")
public void setDeleteUrl(String deleteUrl) {
this.deleteUrl = deleteUrl;
}

@JsonProperty("info_url")
public String getInfoUrl() {
return infoUrl;
}

@JsonProperty("info_url")
public void setInfoUrl(String infoUrl) {
this.infoUrl = infoUrl;
}

@JsonProperty("delete_type")
public String getDeleteType() {
return deleteType;
}

@JsonProperty("delete_type")
public void setDeleteType(String deleteType) {
this.deleteType = deleteType;
}

@JsonProperty("delete_hash")
public String getDeleteHash() {
return deleteHash;
}

@JsonProperty("delete_hash")
public void setDeleteHash(String deleteHash) {
this.deleteHash = deleteHash;
}

@JsonProperty("hash")
public String getHash() {
return hash;
}

@JsonProperty("hash")
public void setHash(String hash) {
this.hash = hash;
}

@JsonProperty("stats_url")
public String getStatsUrl() {
return statsUrl;
}

@JsonProperty("stats_url")
public void setStatsUrl(String statsUrl) {
this.statsUrl = statsUrl;
}

@JsonProperty("short_url")
public String getShortUrl() {
return shortUrl;
}

@JsonProperty("short_url")
public void setShortUrl(String shortUrl) {
this.shortUrl = shortUrl;
}

@JsonProperty("file_id")
public String getFileId() {
return fileId;
}

@JsonProperty("file_id")
public void setFileId(String fileId) {
this.fileId = fileId;
}

@JsonProperty("unique_hash")
public String getUniqueHash() {
return uniqueHash;
}

@JsonProperty("unique_hash")
public void setUniqueHash(String uniqueHash) {
this.uniqueHash = uniqueHash;
}

@JsonProperty("url_html")
public String getUrlHtml() {
return urlHtml;
}

@JsonProperty("url_html")
public void setUrlHtml(String urlHtml) {
this.urlHtml = urlHtml;
}

@JsonProperty("url_bbcode")
public String getUrlBbcode() {
return urlBbcode;
}

@JsonProperty("url_bbcode")
public void setUrlBbcode(String urlBbcode) {
this.urlBbcode = urlBbcode;
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
