package com.tbsd.crawler.util;

import com.tbsd.crawler.model.FakeProfile;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FakeUtil {
    public static FakeProfile[] generate(int n) throws IOException {
        String command = "node ./crawler/random_profile.js "+n;
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        String str = IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8);
        return JsonUtil.GSON.fromJson(str, FakeProfile[].class);
    }
}
