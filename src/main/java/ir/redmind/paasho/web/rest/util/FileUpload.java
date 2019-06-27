package ir.redmind.paasho.web.rest.util;

import ir.redmind.paasho.web.rest.yeka.Authorization;
import ir.redmind.paasho.web.rest.yeka.upload.Upload;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class FileUpload {

    private static final String key1 = "KlrXQr3ir2kbKksH7h8wQo6g2UFeW6xnX8h7yr1QhSqrc6hzS4KPmisUU4CQn56T";
    private static final String key2 = "F16BD99x7ruzKTWT7WwPJXxoMCzEFeCvL9p9upUMfpiEDLxAzE1Iy4fuyQeGQ4Hf";
    static Authorization auth;

    static {
        auth = getToken();
    }

    public static Authorization getToken() {


        RestTemplate restTemplate = new RestTemplateBuilder().setConnectTimeout(30000).setReadTimeout(30000).build();
        String fooResourceUrl
            = "https://yekupload.ir/api/v2/authorize?key1=" + key1 + "&key2=" + key2;
        ResponseEntity<Authorization> response
            = restTemplate.getForEntity(fooResourceUrl, Authorization.class);

        return response.getBody();
    }


    public static String uploadFile(Resource resource) throws IOException {

//
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body
            = new LinkedMultiValueMap<>();
        body.add("access_token", auth.getData().getAccessToken());
        body.add("account_id", auth.getData().getAccountId());
        body.add("upload_file", resource);
        body.add("folder_id", "142499");
        RestTemplate restTemplate = new RestTemplateBuilder().setConnectTimeout(30000).setReadTimeout(600000).build();
        HttpEntity<MultiValueMap<String, Object>> requestEntity
            = new HttpEntity<>(body, headers);

        String serverUrl = "https://yekupload.ir/api/v2/file/upload";

        ResponseEntity<Upload> response = restTemplate
            .postForEntity(serverUrl, requestEntity, Upload.class);


        return response.getBody().getData().get(0).getUrl();
    }

}
