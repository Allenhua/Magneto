package allen.com.rsstest.net;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class NetService extends Service {
    public NetService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
