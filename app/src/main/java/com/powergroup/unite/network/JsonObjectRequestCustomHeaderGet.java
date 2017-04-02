package com.powergroup.unite.network;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JsonObjectRequestCustomHeaderGet extends JsonObjectRequest {
	Context context;
	public JsonObjectRequestCustomHeaderGet(Context context, int method, String url,
                                            JSONObject jsonRequest, Listener<JSONObject> listener,
                                            ErrorListener errorListener) {
		super(method, url, jsonRequest, listener, errorListener);
		this.context = context;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> params = new HashMap<String, String>();
//		String creds = String.format("%s:%s", "playboy", "hughhefner");
//		String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
//		params.put("Authorization", auth);
//		params.put("X-HTTP-Method-Override", "GET");
//		params.put("X-Auth-Token", ProfileManager.INSTANCE.getAccessTokenDeviceID());
		return params;
	}
}