package com.vuebackend.communication.registry;

import java.util.List;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

public class Registry {

    public static InstanceInfo getInstance(EurekaClient client, String appName) {
        Application app = client.getApplication(appName);
        if(app != null) {

            List<InstanceInfo> services = app.getInstances();

            if(services.size() == 0) {
                return services.get(0);
            } 
            
            int index = (int) Math.round(Math.random() * (services.size() - 1));
            return services.get(index);
        }
        return null;
    }
}