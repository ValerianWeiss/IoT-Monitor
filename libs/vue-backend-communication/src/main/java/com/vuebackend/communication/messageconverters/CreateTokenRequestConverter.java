package com.vuebackend.communication.messageconverters;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.vuebackend.communication.CreateTokenRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

public class CreateTokenRequestConverter implements HttpMessageConverter<CreateTokenRequest> {

    public CreateTokenRequestConverter(){}

    private enum Type {
        INTERGER,
        STRING,
    }
    

	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return clazz.equals(CreateTokenRequest.class);
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		return clazz.equals(CreateTokenRequest.class);
	}

	@Override
	public List<MediaType> getSupportedMediaTypes() {
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        return mediaTypes;
	}

	@Override
	public CreateTokenRequest read(Class<? extends CreateTokenRequest> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
        
        InputStream inStream = inputMessage.getBody();
        String content = this.getBodyContent(inStream);
       
        JSONObject json = new JSONObject(content);
        
        boolean expires = json.getBoolean("expires");
        CreateTokenRequest tokenRequest = new CreateTokenRequest(expires);
        JSONArray claimList = json.getJSONArray("claimList");
        tokenRequest = addClaims(claimList, tokenRequest);

        return tokenRequest;
    }

	@Override
	public void write(CreateTokenRequest tokenRequest, MediaType contentType, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {

        StringBuilder json = new StringBuilder();
        json.append("{ \"expires\":\"");
        json.append(tokenRequest.canExpire());
        json.append("\", \"claimList\":[");
        
        boolean addedList = intMapToJson(json, tokenRequest.getClaims(Integer.class), false);
        strMapToJson(json, tokenRequest.getClaims(String.class), addedList);
        
        json.append("]}");

        byte[] body = json.toString().getBytes();
        outputMessage.getBody().write(body);
    }

    private String getBodyContent(InputStream inStream) throws IOException {
        StringBuilder strBuilder = new StringBuilder();
        byte[] buffer = new byte[1024];
        while(true) {
            int count = inStream.available();
            
            if(count == 0) {
                break;
            } else {
                inStream.read(buffer);
                String appendString = new String(buffer);
                strBuilder.append(appendString);
            }
        }
        return strBuilder.toString();
    }
    
    private CreateTokenRequest addClaims(JSONArray claimList, CreateTokenRequest tokenRequest) throws IOException {
        for(int i = 0; i < claimList.length(); i++) {
            JSONObject map = claimList.getJSONObject(i);
            Type valueType = Type.values()[map.getInt("valueType")];
            JSONArray entries = map.getJSONArray("pairs");
            
            switch(valueType) {
                case INTERGER:
                    Map<String, String> intEntries = getEntries(entries);
                    Map<String, Integer> intClaimsToAdd = convertValuesToInt(intEntries);
                    tokenRequest.addIntegerClaims(intClaimsToAdd);
                    break;

                case STRING:
                    Map<String, String> strClaimsToAdd = getEntries(entries);
                    tokenRequest.addStringClaims(strClaimsToAdd);
                    break;

                default: throw new IOException("Type of List values not supported yet");
            }
        }
        return tokenRequest;
    }

    private Map<String, String> getEntries(JSONArray pairs) {
        Map<String, String> entries = new HashMap<>();

        for(int index = 0; index < pairs.length(); index++) {
            JSONObject entry = pairs.getJSONObject(index);
            String key = entry.getString("key");
            String value = entry.getString("value");
            entries.put(key, value);
        }
        return entries;
    }
    
    private Map<String, Integer> convertValuesToInt(Map<String, String> entries) {
        Map<String, Integer> convertedMap = new HashMap<>();
        for(Entry<String, String> entry : entries.entrySet()) {
            int value = Integer.parseInt(entry.getValue());
            convertedMap.put(entry.getKey(), value);
        }
        return convertedMap;
    }

    private boolean intMapToJson(StringBuilder json, Map<String, Integer> map, boolean commaSeperated) {
        if(map != null) {
            this.writeMapToJson(json, Type.INTERGER, map, commaSeperated);
            return true;
        }
        return false;
    }

    private boolean strMapToJson(StringBuilder json, Map<String, String> map, boolean commaSeperated) {
        if(map != null) {
            this.writeMapToJson(json, Type.STRING, map, commaSeperated);
            return true;
        }
        return false;
    }

    private <T> void writeMapToJson(StringBuilder json, Type typeValue, Map<String, T> map, boolean commaSeperated) {
        
        if(commaSeperated) {
            json.append(",");
        }

        json.append("{ \"valueType\":")
            .append(typeValue.ordinal())
            .append(", \"pairs\":[");
        keyValToJson(json, map);
        
        json.append("]}");
    }

    private <T> void keyValToJson(StringBuilder json, Map<String, T> map) {

        Set<Entry<String, T>> entrySet = map.entrySet();
        Iterator<Entry<String, T>> setIterator = entrySet.iterator();

        for(int i = 0; i < entrySet.size(); i++) {
            if(setIterator.hasNext()) {
                
                Entry<String, T> entry = setIterator.next();
                String key = entry.getKey();
                T value = entry.getValue();
                
                json.append("{\"key\":\"")
                    .append(key)
                    .append("\",\"value\":\"")
                    .append(value)
                    .append("\"}");
                
                if(i < entrySet.size() - 1) {
                    json.append(",");
                }
            }
        }
    }
}