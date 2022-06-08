package engineTester;

import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;

import java.io.File;

public class MainGameLoop {

    public static void initLWJGL() {
        File JGLLib = null;

        switch (LWJGLUtil.getPlatform()) {
            case LWJGLUtil.PLATFORM_WINDOWS:
                JGLLib = new File("./native/windows/");
                break;

            case LWJGLUtil.PLATFORM_LINUX:
                JGLLib = new File("./native/linux/");
                break;

            case LWJGLUtil.PLATFORM_MACOSX:
                JGLLib = new File("./native/macosx/");
                break;

            default:
                System.err.println("no platform.?");
                System.exit(-1);
        }

        System.setProperty("org.lwjgl.librarypath", JGLLib.getAbsolutePath());
        // https://stackoverflow.com/questions/30346632/java-error-no-lwjgl64-in-path/30347873#30347873
    }

    public static void main(String[] args) {

        initLWJGL();

        DisplayManager.createDisplay();

        while (!Display.isCloseRequested()) {
            // game logic
            // rendering
            // every frame

            DisplayManager.updateDisplay();
            Display.setTitle("Heyooo mayoo");
        }

        DisplayManager.closeDisplay();

    }

}
