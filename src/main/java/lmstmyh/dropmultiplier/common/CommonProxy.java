package lmstmyh.dropmultiplier.common;

public class CommonProxy {

    public void init() {
        // Gameplay events are registered from the mod constructor so they are available
        // as early as possible on both dedicated and integrated servers.
    }

    public void clientInit() {
        // No client setup on server
    }
}
