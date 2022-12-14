package com.enderbook.forge.data;

import java.io.IOException;

import com.enderbook.forge.util.LoginManager;
import com.enderbook.forge.util.TCNFlattener;
import com.enderbook.forge.util.TCNMerger;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tudbut.obj.TLMap;
import tudbut.parsing.TCN;

public class APISender {

    String archiveCreateURL;
    LoginManager loginManager;

    public APISender(String archiveCreateURL, LoginManager loginManager) {
        if (archiveCreateURL == null) {
            archiveCreateURL = "https://enderbook.com/api/v1/archives/create?username=";
        }
        this.archiveCreateURL = archiveCreateURL + loginManager.user;
        this.loginManager = loginManager;
    }

    TCNMerger archiveBeingBuilt = null;

    public APISender startArchive(Archive archive) {
        try {
            while (archiveBeingBuilt != null)
                Thread.sleep(50);
        } catch (InterruptedException e) {
            // ignore
        }
        archiveBeingBuilt = archive.serialize();
        return this;
    }

    public APISender append(TCN tcn) {
        archiveBeingBuilt.append(tcn, true);
        return this;
    }

    public Response finishArchive() throws IOException {
        TCN tcn = getDataToSend(archiveBeingBuilt);

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        RequestBody body = buildRequest(new MultipartBody.Builder().setType(MultipartBody.FORM), tcn).build();
        Request request = loginManager.insertToken(new Request.Builder()
                .url(archiveCreateURL)
                .method("POST", body))
                .build();
        Response response = client.newCall(request).execute();
        archiveBeingBuilt = null;
        return response;
    }

    public static TCN getDataToSend(TCNMerger merger) {
        return TCNFlattener.flatten("archive", merger.unwrap());
    }

    public static MultipartBody.Builder buildRequest(MultipartBody.Builder builder, TCN data) {
        builder.setType(MultipartBody.FORM);
        for (TLMap.Entry<String, Object> entry : data.map.entries()) {
            if (!entry.key.contains("$") && !entry.key.contains("*")) // $ and * items are generated by ConfigSaverTCN2
                                                                      // and should not be sent to the API.
                builder.addFormDataPart(entry.key, entry.val.toString());
        }
        return builder;
    }
}