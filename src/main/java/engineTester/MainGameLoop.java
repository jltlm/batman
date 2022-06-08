package engineTester;

import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;

import java.io.File;

public class MainGameLoop {

    public static void main(String[] args) {
        initLWJGL();

        DisplayManager.createDisplay();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();

        // OpenGL wants vertices to be defined counterclockwise
        float[] vertices = {
                // left bottom tri
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                // right top tri
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
                -0.5f, 0.5f, 0f
        };
        RawModel model = loader.loadToVao(vertices);

        while (!Display.isCloseRequested()) {
            renderer.prepare();
            // game logic
            // rendering
            // every frame

            renderer.render(model);

            DisplayManager.updateDisplay();
            Display.setTitle("Heyooo mayoo");
        }
        loader.cleanUp();
        DisplayManager.closeDisplay();

    }

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

}
