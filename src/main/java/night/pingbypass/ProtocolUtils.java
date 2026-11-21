package night.pingbypass;

import net.minecraft.network.Connection;
import net.minecraft.network.PacketListener;
import net.minecraft.network.ProtocolInfo;

/**
 * Raw-typed bridge for {@link Connection#setupInboundProtocol}. The proxy installs
 * its own listener implementations, which do not share the exact generic listener
 * type of the vanilla protocol template, so the call has to be made through raw types.
 */
public final class ProtocolUtils {
    private ProtocolUtils() {
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void setupInbound(Connection connection, ProtocolInfo info, PacketListener listener) {
        connection.setupInboundProtocol((ProtocolInfo)info, (PacketListener)listener);
    }
}
