package com.lotus.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.lotus.common.exception.SysException;
import com.lotus.common.exception.UtilException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @program: commmon
 * @description:
 * @author: changjiz
 * @create: 2019-09-24 16:32
 **/
@Slf4j
public class RestTemplateUtils {

    private static RestTemplate restTemplate() {
        return new RestTemplate();
    }


    public static JSONObject get(String url, Object... params) {
        JSONObject resultJson = restTemplate().getForObject(url, JSONObject.class, params);

        StringJoiner paramsStringJ = new StringJoiner(",");
        Arrays.stream(params).forEach(e -> {
            paramsStringJ.add(e.toString());
        });
        log.info("request url -> : " + url + "  params：" + paramsStringJ.toString() + "  result：" + resultJson.toString());

        resultJson = checkErr(resultJson);
        return resultJson;
    }

    public static JSONObject post(String url, String contentType, Map<String, Object> body) {

        HttpHeaders headers = new HttpHeaders();
//        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        if (StringUtils.isNotEmpty(contentType)) {
            MediaType type = MediaType.parseMediaType(contentType);
            headers.setContentType(type);
//            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        }
        HttpEntity<String> formEntity = new HttpEntity<>(new JSONObject(body).toString(), headers);
        JSONObject resultJson = restTemplate().postForObject(url, formEntity, JSONObject.class);

        StringJoiner paramsStringJ = new StringJoiner("&");
        for (String k : body.keySet()) {
            paramsStringJ.add(k + "=" + body.get(k).toString());
        }
        log.info("request url -> : " + url + "  params：" + paramsStringJ.toString() + "  result：" + resultJson.toString());

        checkErr(resultJson);
        return resultJson;
    }

    public static JSONObject checkErr(JSONObject result) {
        // 状态大于0 视为异常
        if (0 < result.getIntValue("code")) {
            throw new UtilException(result.getString("msg"));
        }
        return result;
    }
}
