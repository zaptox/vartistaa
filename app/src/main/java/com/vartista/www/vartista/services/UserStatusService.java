package com.vartista.www.vartista.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class UserStatusService extends Service {
    public UserStatusService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
