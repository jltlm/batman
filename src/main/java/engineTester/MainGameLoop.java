package engineTester;

import models.TexturedModel;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

import java.io.File;

public class MainGameLoop {

    public static void main(String[] args) {
        initLWJGL();

        DisplayManager.createDisplay();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();

        // OpenGL wants vertices to be defined counterclockwise
        float[] vertices = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
        };

        int[] indices = {
                0,1,3,  // Top left triangle
                3,1,2   // Bottom right triangle
        };

        float[] textureCoords = {
                0,0,
                0,1,
                1,1,
                1,0
        };

        RawModel model = loader.loadToVao(vertices, textureCoords, indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("howl"));
        TexturedModel texturedModel = new TexturedModel(model, texture);

        Display.setTitle("Heyooo mayoo");

        while (!Display.isCloseRequested()) {
            renderer.prepare();
            shader.start();
            renderer.render(texturedModel);
            shader.stop();
            DisplayManager.updateDisplay();

        }

        shader.cleanUp();
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
